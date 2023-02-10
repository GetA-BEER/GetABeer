package be.global.aop;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
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
import be.domain.user.entity.User;
import be.domain.user.entity.enums.Gender;
import be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Aspect
// @Component
@RequiredArgsConstructor
public class GetABeerAop {

	private final UserService userService;
	private final BeerService beerService;
	private final BeerTagService beerTagService;
	private final RatingService ratingService;
	private final BeerRepository beerRepository;

	/*
	 * 레이팅 새로 등록될 때마다 인기 태그, 베스트 레이팅, 평균 별점 계산 후 변경 사항 저장
	 */
	@AfterReturning(value = "Pointcuts.createRating() && args(rating, beerId, ratingTag, userId)")
	public void calculateBeerDetailsOnCreation(JoinPoint joinPoint, Rating rating, Long beerId,
		RatingTag ratingTag, Long userId) {

		Beer findBeer = beerService.findVerifiedBeer(beerId);

		findBeer.addStatRatingCount(); // 맥주 통계용 레이팅 숫자 늘려주기
		findBeer.addRatingCount(); // 맥주 전체 레이팅 숫자 늘려주기

		// ------------------------------------BEER TAG-------------------------------------------

		List<BeerTagType> postBeerTagTypes = ratingTag.createBeerTagTypeList(); // 입력받은 맥주 태그 타입

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
		// --------------------------------BEER DETAIL STARS--------------------------------------

		User loginUser = userService.getLoginUser();

		findBeer.calculateTotalAverageStars(rating.getStar());

		if (loginUser.getGender() == Gender.FEMALE) {
			findBeer.calculateFemaleAverageStars(rating.getStar());
			findBeer.addFemaleStarCount();
		} else if (loginUser.getGender() == Gender.MALE) {
			findBeer.calculateMaleAverageStars(rating.getStar());
			findBeer.addMaleStarCount();
		}
		// ---------------------------------------------------------------------------------------

		// beerRepository.save(findBeer);
	}

	/*
	 * 레이팅 수정시 인기 태그, 평균 별점 계산 후 변경 사항 저장
	 */
	@Before(value = "Pointcuts.updateRating() && args(rating, ratingId, ratingTag)")
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
		// --------------------------------BEER DETAIL STARS--------------------------------------

		User loginUser = userService.getLoginUser();
		Rating previousRating = ratingService.findRating(ratingId); // 메서드 실행 전 레이팅
		Double previousStar = previousRating.getStar();
		Double afterStar = rating.getStar();

		findBeer.updateTotalAverageStars(previousStar, afterStar);

		if (loginUser.getGender().equals(Gender.FEMALE)) {
			findBeer.updateFemaleAverageStars(previousStar, afterStar);
			findBeer.addFemaleStarCount();
		} else if (loginUser.getGender().equals(Gender.MALE)) {
			findBeer.updateMaleAverageStars(previousStar, afterStar);
			findBeer.addMaleStarCount();
		}

		// ---------------------------------------------------------------------------------------

		beerRepository.save(findBeer);
	}

	@Around(value = "Pointcuts.deleteRating() && args(ratingId)")
	public Object calculateBeerDetailsOnDeletion(ProceedingJoinPoint joinPoint, long ratingId) throws Throwable {

		Beer findBeer = beerService.findBeerByRatingId(ratingId);
		User loginUser = userService.getLoginUser();

		try {
			// @Before
			Rating findRating = ratingService.findRating(ratingId);
			Double deleteStar = findRating.getStar();

			findBeer.deleteTotalAverageStars(deleteStar);
			findBeer.minusRatingCount();

			if (loginUser.getGender().equals(Gender.FEMALE)) {
				findBeer.deleteFemaleAverageStars(deleteStar);
				findBeer.minusFemaleStarCount();
			} else if (loginUser.getGender().equals(Gender.MALE)) {
				findBeer.deleteMaleAverageStars(deleteStar);
				findBeer.minusMaleStarCount();
			}

			List<String> presentBeerTagTypes = findBeer.createTopTagList(); // 삭제 이전 베스트 태그

			log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
			Object result = joinPoint.proceed();

			// @AfterReturning
			List<BeerTag> beerTags = beerService.findTop4BeerTags(findBeer); // 상위 태그 새로 계산

			List<String> beerTagTypes = beerTags.stream() // 입력받은 맥주 태그 타입 문자열 리스트로 전환
				.map(beerTag -> beerTag.getBeerTagType().toString())
				.collect(Collectors.toList());

			if (presentBeerTagTypes != beerTagTypes) { // 둘이 다르면 교체
				findBeer.getBeerDetailsTopTags().changeTags(beerTagTypes);
			}

			// 삭제되는 레이팅이 베스트 레이팅일 경우
			if (findBeer.getBeerDetailsBestRating().getBestRatingId() == ratingId) {
				Rating bestRating = beerService.findBestRating(findBeer);
				findBeer.updateBestRating(bestRating);
			}

			log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());

			return result;
		} catch (Exception e) {
			// @AfterThrowing
			log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
			throw e;
		} finally {
			// @After
			log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
		}
	}

	@AfterReturning(value = "Pointcuts.createPairing() && args(pairing, image, category, beerId)")
	public void calculateBeerDetailsOnPairingCreation(JoinPoint joinPoint, Pairing pairing, List<String> image,
		String category, Long beerId) {
		// ---------------------------------------------------------------------------------------
		// TODO: 페어링 생성시 각종 계산
		// ---------------------------------------------------------------------------------------
	}

	@AfterReturning(value = "Pointcuts.updatePairing() && args(pairing, pairingId, category, image)")
	public void calculateBeerDetailsOnPairingUpdate(JoinPoint joinPoint, Pairing pairing, long pairingId,
		String category, List<String> image) {
		// ---------------------------------------------------------------------------------------
		// TODO: 페어링 수정시 각종 계산
		// ---------------------------------------------------------------------------------------
	}

	@Before(value = "Pointcuts.deletePairing() && args(pairingId)")
	public void calculateBeerDetailsOnPairingDeletion(JoinPoint joinPoint, long pairingId) {
		// ---------------------------------------------------------------------------------------
		// TODO: 페어링 삭제시 각종 계산
		// ---------------------------------------------------------------------------------------
	}

	@Before(value = "Pointcuts.getBeer() && args(beerId)")
	public void test(JoinPoint joinPoint, Long beerId) {
		Beer beer = beerService.findVerifiedBeer(beerId);
		beer.addStatViewCount();
	}
}
