package be.domain.rating.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.beertag.entity.BeerTagType;
import be.domain.rating.dto.RatingTagDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;
import be.domain.rating.repository.RatingRepository;
import be.domain.rating.repository.RatingTagRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RatingService {
	private final RatingRepository ratingRepository;
	private final BeerService beerService;
	private final RatingTagRepository tagRepository;

	public RatingService(RatingRepository ratingRepository, BeerService beerService,
		RatingTagRepository tagRepository) {
		this.ratingRepository = ratingRepository;
		this.beerService = beerService;
		this.tagRepository = tagRepository;
	}

	/* 맥주 코멘트 등록 */
	@Transactional
	public Rating create(Rating rating, Long beerId, RatingTag ratingTag) {
		/* 존재하는 맥주인지 확인 */
		Beer beer = beerService.findVerifiedBeer(beerId);

		/* 기본 설정 저장하기 */
		tagRepository.save(ratingTag);
		rating.saveDefault(beer, ratingTag, 0, 0, new ArrayList<>());

		ratingRepository.save(rating);

		return rating;
	}

	/* 맥주 코멘트 수정 */
	public Rating update(Rating rating, long ratingId) {

		/* 존재하는 맥주 코멘트인지 확인 및 해당 맥주 코멘트 정보 가져오기 */
		Rating findRating = findVerifiedRating(ratingId);

		/* 수정할 내용이 존재하면, 해당 정보 수정 후 저장*/
		Optional.ofNullable(rating.getContent()).ifPresent(findRating::updateContent);
		Optional.ofNullable(rating.getStar()).ifPresent(findRating::updateStar);
		ratingRepository.save(findRating);

		return findRating;
	}

	/* 특정 맥주 코멘트 상세 조회 */
	public Rating getRating(long ratingId) {

		return findVerifiedRating(ratingId);
	}

	/* 맥주 코멘트 페이지 조회 -> Query Dsl 사용 예정 */
	public List<Rating> getRatingPage(int page, int size) {

		return null;
	}

	/* 맥주 코멘트 삭제 */
	public String delete(long ratingId) {
		Rating rating = findVerifiedRating(ratingId);
		ratingRepository.delete(rating);

		return "해당 맥주 코멘트가 삭제되었습니다.";
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
}
