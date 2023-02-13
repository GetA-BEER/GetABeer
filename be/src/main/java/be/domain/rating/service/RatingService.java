package be.domain.rating.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.entity.BeerBeerTag;
import be.domain.beer.repository.BeerBeerTagQueryRepository;
import be.domain.beer.repository.BeerBeerTagRepository;
import be.domain.beer.service.BeerService;
import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.entity.BeerTagType;
import be.domain.beertag.service.BeerTagService;
import be.domain.like.repository.RatingLikeRepository;
import be.domain.like.service.RatingLikeService;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;
import be.domain.rating.repository.RatingRepository;
import be.domain.rating.repository.RatingTagRepository;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingService {
	private final RatingRepository ratingRepository;
	private final BeerService beerService;
	private final BeerTagService beerTagService;
	private final BeerBeerTagRepository beerBeerTagRepository;
	private final BeerBeerTagQueryRepository beerBeerTagQueryRepository;
	private final RatingTagRepository tagRepository;
	private final UserService userService;
	private final RatingLikeRepository ratingLikeRepository;

	/* 맥주 평가 등록 */
	@Transactional
	public String create(Rating rating, Long beerId, RatingTag ratingTag, Long userId) {

		/* 접근하려는 유저가 로그인 유저와 일치하는 지 확인*/
		// User loginUser = userService.getLoginUser();
		// userService.checkUser(userId, loginUser.getId());

		/* 회원 정보 가져오기 */
		User user = userService.getUser(userId);

		/* 존재하는 맥주인지 확인 */
		Beer beer = beerService.findVerifiedBeer(beerId);

		/* 기본 설정 저장하기 */
		rating.saveDefault(beer, user, ratingTag, user.getNickname(),0, 0, new ArrayList<>());
		ratingTag.saveRating(rating);

		/* 다대다 연관관계 생성 및 저장 */
		saveBeerBeerTags(beer, ratingTag.createBeerTagTypeList());

		ratingRepository.save(rating);

		return "맥주에 대한 평가가 성공적으로 등록되었습니다.";
	}

	/* 맥주 평가 수정 */
	@Transactional
	public String update(Rating rating, Long ratingId, RatingTag ratingTag) {

		/* 존재하는 맥주 코멘트인지 확인 및 해당 맥주 코멘트 정보 가져오기 */
		Rating findRating = findVerifiedRating(ratingId);

		/* 로그인 한 유저가 평가를 단 유저와 일치하는지 판별 */
		// User user = findRating.getUser();
		// User loginUser = userService.getLoginUser();
		// userService.checkUser(user.getId(), loginUser.getId());

		/* 레이팅 아이디로 맥주 조회 */
		Beer findBeer = beerService.findBeerByRatingId(ratingId);

		/* BeerBeerTag 찾아서 맥주/맥주 태그 연관 관계 끊고 삭제 */
		deleteBeerBeerTags(findBeer, findRating.getRatingTag().createBeerTagTypeList());

		/* 수정할 내용이 존재하면, 해당 정보 수정 후 저장*/
		Optional.ofNullable(rating.getContent()).ifPresent(findRating::updateContent);
		Optional.ofNullable(rating.getStar()).ifPresent(findRating::updateStar);

		RatingTag findTag = findRating.getRatingTag();
		findTag.updateRatingTag(ratingTag);
		tagRepository.save(findTag);

		findRating.updateTag(findTag);

		/* 새로운 BeerBeerTag 생성 및 저장 */
		saveBeerBeerTags(findBeer, ratingTag.createBeerTagTypeList());

		ratingRepository.save(findRating);

		return "맥주에 대한 평가가 성공적으로 수정되었습니다.";
	}

	/* 특정 맥주 평가 상세 조회 : 응답 */
	@Transactional
	public RatingResponseDto.Detail getRatingResponse(Long ratingId) {

		/* 존재하는 평가인지 확인 */
		Rating rating = findVerifiedRating(ratingId);

		RatingResponseDto.Detail response = ratingRepository.findDetailRatingResponse(ratingId);

		response.addTag(getRatingTagList(ratingId));
		response.addComment(ratingRepository.findRatingCommentResponse(ratingId));

		User user = userService.getLoginUser();
		response.addUserLike(getIsUserLikes(rating.getId(), user.getId()));

		return response;
	}

	/* 특정 맥주 평가 조회 : 맥주 평가 */
	@Transactional(readOnly = true)
	public Rating findRating(Long ratingId) {

		return findVerifiedRating(ratingId);
	}

	/* 맥주 평가 삭제 */
	@Transactional
	public String delete(long ratingId) {
		Rating rating = findVerifiedRating(ratingId);
		Beer findBeer = beerService.findBeerByRatingId(ratingId);

		/* 삭제하려는 평가의 유저와 로그인한 유저가 일치하는지 확인 */
		// User user = rating.getUser();
		// User loginUser = userService.getLoginUser();
		// userService.checkUser(user.getId(), loginUser.getId());

		deleteBeerBeerTags(findBeer, rating.getRatingTag().createBeerTagTypeList());

		ratingRepository.delete(rating);

		return "맥주에 대한 평가가 성공적으로 삭제되었습니다.";
	}

	// ------------------------------------------- 조회 세분화 --------------------------------------------------------

	/* 맥주 평가 페이지 조회 : 최신순 */
	public Page<RatingResponseDto.Total> getRatingPageOrderByRecent(Long beerId, Integer page, Integer size) {
		Page<RatingResponseDto.Total> responses = ratingRepository.findRatingTotalResponseOrderByRecent(beerId,
			PageRequest.of(page - 1, size));

		responses.forEach(rating -> rating.addTag(getRatingTagList(rating.getRatingId())));

		User user = userService.getLoginUser();
		responses.forEach(rating -> rating.addUserLike(getIsUserLikes(rating.getRatingId(), user.getId())));
		return responses;
	}

	/* 맥주 평가 페이지 조회 : 추천 순 */
	public Page<RatingResponseDto.Total> getRatingPageOrderByMoreLikes(Long beerId, Integer page, Integer size) {
		Page<RatingResponseDto.Total> responses = ratingRepository.findRatingTotalResponseOrderByLikes(beerId,
			PageRequest.of(page - 1, size));

		responses.forEach(rating -> rating.addTag(getRatingTagList(rating.getRatingId())));

		User user = userService.getLoginUser();
		responses.forEach(rating -> rating.addUserLike(getIsUserLikes(rating.getRatingId(), user.getId())));

		return responses;
	}

	/* 맥주 평가 페이지 조회 : 댓글 많은 순 */
	public Page<RatingResponseDto.Total> getRatingPageOrderByMoreComments(Long beerId, Integer page, Integer size) {
		Page<RatingResponseDto.Total> responses = ratingRepository.findRatingTotalResponseOrderByComments(beerId,
			PageRequest.of(page - 1, size));

		responses.forEach(rating -> rating.addTag(getRatingTagList(rating.getRatingId())));

		User user = userService.getLoginUser();
		responses.forEach(rating -> rating.addUserLike(getIsUserLikes(rating.getRatingId(), user.getId())));
		return responses;
	}

	//-------------------------------------------------------------------------------------------------------------

	/* 태그 리스트 가져오기 */
	private List<BeerTagType> getRatingTagList(Long ratingId) {

		RatingTag ratingTag = ratingRepository.findTagResponse(ratingId);
		List<BeerTagType> tag = new ArrayList<>();
		tag.add(ratingTag.getColor());
		tag.add(ratingTag.getTaste());
		tag.add(ratingTag.getFlavor());
		tag.add(ratingTag.getCarbonation());

		return tag;
	}

	/* 추천 여부 가져오기 */
	private Boolean getIsUserLikes(Long ratingId, Long userId) {
		int userLikes = ratingLikeRepository.findRatingLikeUser(ratingId, userId);

		if (userLikes != 0) {
			return true;
		}

		return false;
	}

	/* 존재하는 맥주 코멘트인지 확인 -> 존재하면 해당 맥주 코멘트 반환 */
	private Rating findVerifiedRating(long ratingId) {

		return ratingRepository.findById(ratingId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RATING_NOT_FOUND));
	}

	/* 평가 태그 입력 확인 */
	public void checkVerifiedTag(String color, String taste, String flavor, String carbonation) {

		if (!color.equalsIgnoreCase("STRAW") && !color.equalsIgnoreCase("GOLD")
			&& !color.equalsIgnoreCase("BROWN") && !color.equalsIgnoreCase("BLACK")) {
			log.error("색 태그가 잘못 요청 되었음.");
			throw new BusinessLogicException(ExceptionCode.TAG_IS_WRONG);
		}

		if (!taste.equalsIgnoreCase("SWEET") && !taste.equalsIgnoreCase("SOUR")
			&& !taste.equalsIgnoreCase("BITTER") && !taste.equalsIgnoreCase("ROUGH")) {
			log.error("맛 태그가 잘못 요청 되었음.");
			throw new BusinessLogicException(ExceptionCode.TAG_IS_WRONG);
		}

		if (!flavor.equalsIgnoreCase("FRUITY") && !flavor.equalsIgnoreCase("FLOWER")
			&& !flavor.equalsIgnoreCase("MALTY") && !flavor.equalsIgnoreCase("HOPPY")) {
			log.error("향 태그가 잘못 요청 되었음.");
			throw new BusinessLogicException(ExceptionCode.TAG_IS_WRONG);
		}

		if (!carbonation.equalsIgnoreCase("WEAK") && !carbonation.equalsIgnoreCase("MIDDLE")
			&& !carbonation.equalsIgnoreCase("STRONG") && !carbonation.equalsIgnoreCase("NO_CARBONATION")) {
			log.error("탄산 태그가 잘못 요청 되었음.");
			throw new BusinessLogicException(ExceptionCode.TAG_IS_WRONG);
		}
	}

	private void saveBeerBeerTags(Beer findBeer, List<BeerTagType> beerTagTypeList) {

		beerTagTypeList
			.forEach(beerTagType -> {

				BeerTag findBeerTag = beerTagService.findVerifiedBeerTagByBeerTagType(beerTagType);

				BeerBeerTag createdBeerBeerTag =
					BeerBeerTag.builder()
						.beer(findBeer)
						.beerTag(findBeerTag)
						.build();

				beerBeerTagRepository.save(createdBeerBeerTag);
			});
	}

	private void deleteBeerBeerTags(Beer findBeer, List<BeerTagType> beerTagTypeList) {

		beerTagTypeList
			.forEach(beerTagType -> {

				BeerTag findBeerTag = beerTagService.findVerifiedBeerTagByBeerTagType(beerTagType);

				// findBeerTag.subtractDailyCount(); // 카운트 빼주기

				BeerBeerTag beerBeerTag = beerBeerTagQueryRepository
					.findBeerBeerTagByBeerAndBeerTagType(findBeer, beerTagType);

				beerBeerTag.remove(findBeer, findBeerTag);

				beerBeerTagRepository.delete(beerBeerTag);
			});

	}
}
