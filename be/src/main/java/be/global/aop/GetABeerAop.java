package be.global.aop;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import be.domain.beer.entity.Beer;
import be.domain.beer.repository.BeerRepository;
import be.domain.beer.service.BeerService;
import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.entity.BeerTagType;
import be.domain.beertag.service.BeerTagService;
import be.domain.pairing.entity.Pairing;
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;
import be.domain.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class GetABeerAop {

	private final BeerService beerService;
	private final BeerTagService beerTagService;
	private final RatingService ratingService;
	private final BeerRepository beerRepository;

	/*
	 * 레이팅 새로 등록될 때마다 인기 태그 계산 후 변경 사항 저장
	 */
	@AfterReturning("execution(* be.domain.rating.service.RatingService.create(..)) "
		+ "&& args(rating, beerId, ratingTag)")
	public void calculateBeerDetailsOnCreation(JoinPoint joinPoint, Rating rating, Long beerId,
		RatingTag ratingTag) {

		Beer findBeer = beerService.findVerifiedBeer(beerId);

		// ------------------------------------BEER TAG-------------------------------------------

		List<BeerTagType> postBeerTagTypes = ratingTag.createBeerTagTypeList(); // 입력받은 맥주 태그 타입

		findBeer.getBeerDetailsStatistics().addDailyRatingCount(); // 맥주 레이팅 숫자 늘려주기

		postBeerTagTypes.forEach(beerTagType -> { // 태그 카운트 늘려주기
			BeerTag findBeerTag = beerTagService.findVerifiedBeerTagByBeerTagType(beerTagType);
			findBeerTag.addDailyCount();
		});

		List<BeerTag> beerTags = beerService.findTop4BeerTags(findBeer); // 상위 태그 새로 계산하기

		List<String> beerTagTypes = beerTags.stream() // 입력받은 맥주 태그 타입 문자열 리스트로 전환
			.map(beerTag -> beerTag.getBeerTagType().toString())
			.collect(Collectors.toList());

		if (findBeer.getBeerDetailsTopTags() == null) {
			findBeer.createTopTags(beerTagTypes);
		} else {
			List<String> presentBeerTagTypes = findBeer.createTopTagList(); // 기존 상위 태그
			if (presentBeerTagTypes != beerTagTypes) { // 둘이 다르면 교체
				findBeer.getBeerDetailsTopTags().changeTags(beerTagTypes);
			}
		}

		// ---------------------------------------------------------------------------------------
		// ----------------------------------BEST RATING------------------------------------------

		if (findBeer.getBeerDetailsBestRating() == null ||
			rating.getStar() >= findBeer.getBeerDetailsBestRating().getBestStar()) {
			findBeer.updateBestRating(rating);
		}
		// ---------------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------------
		// TODO: 전체 별점과 회원 성별에 따른 별점 평균 계산
		// ---------------------------------------------------------------------------------------

		beerRepository.save(findBeer);
	}

	/*
	 * 레이팅 수정시 인기 태그 계산 후 변경 사항 저장
	 */
	@AfterReturning(value = "execution(* be.domain.rating.service.RatingService.update(..)) "
		+ "&& args(rating, ratingId, ratingTag)")
	public void calculateBeerDetailsOnUpdate(JoinPoint joinPoint, Rating rating, Long ratingId,
		RatingTag ratingTag) {

		Beer findBeer = beerService.findBeerByRatingId(ratingId);

		// ------------------------------------BEER TAG-------------------------------------------

		List<BeerTagType> postBeerTagTypes = ratingTag.createBeerTagTypeList(); // 입력받은 맥주 태그 타입

		postBeerTagTypes.forEach(beerTagType -> { // 태그 카운트 늘려주기
			BeerTag findBeerTag = beerTagService.findVerifiedBeerTagByBeerTagType(beerTagType);
			findBeerTag.addDailyCount();
		});

		List<BeerTag> beerTags = beerService.findTop4BeerTags(findBeer); // 상위 태그 새로 계산

		List<String> beerTagTypes = beerTags.stream() // 입력받은 맥주 태그 타입 문자열 리스트로 전환
			.map(beerTag -> beerTag.getBeerTagType().toString())
			.collect(Collectors.toList());

		if (findBeer.getBeerDetailsTopTags() == null) {
			findBeer.createTopTags(beerTagTypes);
		} else {
			List<String> presentBeerTagTypes = findBeer.createTopTagList(); // 기존 상위 태그
			if (presentBeerTagTypes != beerTagTypes) { // 둘이 다르면 교체
				findBeer.getBeerDetailsTopTags().changeTags(beerTagTypes);
			}
		}

		// ---------------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------------
		// TODO: 전체 별점과 회원 성별에 따른 별점 평균 계산
		// ---------------------------------------------------------------------------------------

		beerRepository.save(findBeer);
	}

	@Before(value = "execution(* be.domain.rating.service.RatingService.create(..)) && args(ratingId)")
	public void calculateBeerDetailsOnDeletion(JoinPoint joinPoint, long ratingId) {
		// ---------------------------------------------------------------------------------------
		// TODO: 레이팅 삭제시 각종 계산
		// ---------------------------------------------------------------------------------------
	}

	@AfterReturning(value = "execution(* be.domain.pairing.service.PairingService.create(..)) && args(pairing, image, category, beerId)")
	public void calculateBeerDetailsOnPairingCreation(JoinPoint joinPoint, Pairing pairing, List<String> image,
		String category, Long beerId) {
		// ---------------------------------------------------------------------------------------
		// TODO: 페어링 생성시 각종 계산
		// ---------------------------------------------------------------------------------------
	}

	@AfterReturning(value = "execution(* be.domain.pairing.service.PairingService.update(..)) && args(pairing, pairingId, category, image)")
	public void calculateBeerDetailsOnPairingUpdate(JoinPoint joinPoint, Pairing pairing, long pairingId,
		String category, List<String> image) {
		// ---------------------------------------------------------------------------------------
		// TODO: 페어링 생성시 각종 계산
		// ---------------------------------------------------------------------------------------
	}

	@Before(value = "execution(* be.domain.pairing.service.PairingService.delete(..)) && args(pairingId)")
	public void calculateBeerDetailsOnPairingDeletion(JoinPoint joinPoint, long pairingId) {
		// ---------------------------------------------------------------------------------------
		// TODO: 페어링 삭제시 각종 계산
		// ---------------------------------------------------------------------------------------
	}
}
