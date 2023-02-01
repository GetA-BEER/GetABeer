package be.domain.rating.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.rating.entity.Rating;
import be.domain.rating.repository.RatingRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;

@Service
public class RatingService {
	private final RatingRepository ratingRepository;
	private final BeerService beerService;

	public RatingService(RatingRepository ratingRepository, BeerService beerService) {
		this.ratingRepository = ratingRepository;
		this.beerService = beerService;
	}

	/* 맥주 코멘트 등록 */
	public Rating create(Rating rating, Long beerId) {
		/* 존재하는 맥주인지 확인 */
		Beer beer = beerService.findVerifiedBeer(beerId);

		/* 기본 설정 저장하기 */
		rating.saveDefault(beer, 0, 0, new ArrayList<>());

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
}
