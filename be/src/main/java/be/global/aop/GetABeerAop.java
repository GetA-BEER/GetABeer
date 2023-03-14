package be.global.aop;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import be.domain.beer.entity.Beer;
import be.domain.beer.repository.BeerQueryRepository;
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
import be.global.statistics.entity.TotalStatistics;
import be.global.statistics.repository.TotalStatisticsQueryRepository;
import be.global.statistics.repository.TotalStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class GetABeerAop {

	private final UserService userService;
	private final BeerService beerService;
	private final BeerTagService beerTagService;
	private final RatingService ratingService;
	private final BeerRepository beerRepository;
	private final BeerQueryRepository beerQueryRepository;
	private final TotalStatisticsRepository totalStatisticsRepository;
	private final TotalStatisticsQueryRepository totalStatisticsQueryRepository;

	@Before(value = "Pointcuts.getWeeklyBeer()")
	public void calculateVisitorByMainPageView(JoinPoint joinPoint) {

		TotalStatistics totalStatistics = totalStatisticsQueryRepository.findTotalStatistics();

		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();

		HttpServletRequest request = attr.getRequest(); // Http Request
		HttpServletResponse response = attr.getResponse(); // Http Response
		Cookie[] cookies = request.getCookies(); // Request Cookies
		String token = request.getHeader("Cookie"); // Cookie에서 뜯어온 토큰들

		if (cookies != null) { // 쿠키를 가진 경우

			for (Cookie cookie : cookies) {

				/*
				 * 통계 쿠키가 있는데 오늘 방문한 적이 없을 경우
				 * or
				 * 통계 쿠키가 발급된 적이 없는 경우
				 */
				if ((cookie.getValue().contains("statistic") && !cookie.getValue()
					.contains(request.getContextPath())) || !cookie.getValue()
					.contains("statistic")) { // 통계 쿠키가 있지만 방문한 적 없을 경우

					createStatCookie(request, response, cookie);

					totalStatistics.addTotalVisitorCount();
				}
			}
		} else if (cookies == null) { // 쿠키 자체가 없는 경우 새로 발급

			String key = "visit_cookie";
			String value =
				"statistic" + "_" + "[" + LocalDateTime.now() + "]" + "_" + "[" + request.getContextPath()
					+ "]";

			ResponseCookie newStatCookie = ResponseCookie.from(key, value)
				.maxAge(2 * 60 * 60) // 두 시간
				.path("/")
				.secure(true)
				.sameSite("None")
				.httpOnly(true)
				.build();

			response.setHeader("Set-Cookie", newStatCookie.toString());

			totalStatistics.addTotalVisitorCount();
		}

		totalStatisticsRepository.save(totalStatistics);

	}

	@Before(value = "Pointcuts.getBeer() && args(beerId)")
	public void calculateStaticticsOnGetBeer(JoinPoint joinPoint, Long beerId) {
		TotalStatistics totalStatistics = totalStatisticsQueryRepository.findTotalStatistics();
		totalStatistics.addTotalBeerViewCount();
		totalStatisticsRepository.save(totalStatistics);
	}

	/*
	 * 레이팅 새로 등록될 때마다 인기 태그, 베스트 레이팅, 평균 별점 계산 후 변경 사항 저장
	 */
	@AfterReturning(value = "Pointcuts.createRating() && args(rating, beerId, ratingTag)")
	public void calculateBeerDetailsOnCreation(JoinPoint joinPoint, Rating rating, Long beerId,
		RatingTag ratingTag) {

		TotalStatistics totalStatistics = totalStatisticsQueryRepository.findTotalStatistics();
		totalStatistics.addTotalRatingCount();

		Beer findBeer = beerService.findVerifiedBeer(beerId);

		findBeer.addStatRatingCount(); // 맥주 통계용 레이팅 숫자 늘려주기
		// findBeer.addRatingCount(); // 맥주 전체 레이팅 숫자 늘려주기

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

		if (findBeer.getBeerDetailsBestRating() == null
			|| findBeer.getBeerDetailsBestRating().getBestLikeCount() == 0 &&
			rating.getStar() >= findBeer.getBeerDetailsBestRating().getBestStar()) {
			findBeer.updateBestRating(rating);
		}
		// ---------------------------------------------------------------------------------------
		// --------------------------------BEER DETAIL STARS--------------------------------------

		User loginUser = userService.getLoginUser();

		findBeer.calculateTotalAverageStars(rating.getStar());

		if (loginUser.getGender() == Gender.FEMALE) {
			findBeer.calculateFemaleAverageStars(rating.getStar());
			// findBeer.addFemaleStarCount();
		} else if (loginUser.getGender() == Gender.MALE) {
			findBeer.calculateMaleAverageStars(rating.getStar());
			// findBeer.addMaleStarCount();
		}
		// ---------------------------------------------------------------------------------------
		totalStatisticsRepository.save(totalStatistics);
		beerRepository.save(findBeer);
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
		} else if (loginUser.getGender().equals(Gender.MALE)) {
			findBeer.updateMaleAverageStars(previousStar, afterStar);
		}

		// ---------------------------------------------------------------------------------------

		beerRepository.save(findBeer);
	}

	@Around(value = "Pointcuts.deleteRating() && args(ratingId)")
	public Object calculateBeerDetailsOnDeletion(ProceedingJoinPoint joinPoint, Long ratingId) throws Throwable {

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
			} else if (loginUser.getGender().equals(Gender.MALE)) {
				findBeer.deleteMaleAverageStars(deleteStar);
			}

			beerRepository.save(findBeer);

			List<String> presentBeerTagTypes = new ArrayList<>();

			if (findBeer.createTopTagList() != null) {
				presentBeerTagTypes = findBeer.createTopTagList(); // 삭제 이전 베스트 태그
			}

			log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
			Object result = joinPoint.proceed();

			// @AfterReturning
			List<BeerTag> beerTags = beerService.findTop4BeerTags(findBeer); // 상위 태그 새로 계산

			List<String> beerTagTypes = beerTags.stream() // 맥주 태그 타입 문자열 리스트로 전환
				.map(beerTag -> beerTag.getBeerTagType().toString())
				.collect(Collectors.toList());

			if (presentBeerTagTypes != beerTagTypes && beerTagTypes.size() != 0) { // 둘이 다르면 교체
				findBeer.getBeerDetailsTopTags().changeTags(beerTagTypes);
			} else if (presentBeerTagTypes != beerTagTypes && beerTagTypes.size() == 0) {
				findBeer.makeTopTagsNull();
			}

			// 삭제되는 레이팅이 베스트 레이팅일 경우
			if (findBeer.getBeerDetailsBestRating().getBestRatingId() == ratingId
				&& beerService.findBestRating(findBeer) != null) {
				Rating bestRating = beerService.findBestRating(findBeer);
				findBeer.updateBestRating(bestRating);
			} else if (findBeer.getBeerDetailsBestRating().getBestRatingId() == ratingId
				&& beerService.findBestRating(findBeer) == null) {
				findBeer.deleteBeerDetailsBestRating();
			}

			beerRepository.save(findBeer);

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

	@AfterReturning(value = "Pointcuts.createPairing() && args(pairing, files, beerId)")
	public void calculateBeerDetailsOnPairingCreation(JoinPoint joinPoint, Pairing pairing, List<MultipartFile> files,
		Long beerId) {

		TotalStatistics totalStatistics = totalStatisticsQueryRepository.findTotalStatistics();
		totalStatistics.addTotalPairingCount();

		// ---------------------------------------------------------------------------------------
		// TODO: 페어링 생성시 각종 계산
		// ---------------------------------------------------------------------------------------

		Beer findBeer = beerService.findVerifiedBeer(beerId);

		String bestPairingCategory = beerService.findBestPairingCategory(findBeer);

		if (findBeer.getBeerDetailsStatistics().getBestPairingCategory() != bestPairingCategory) {
			findBeer.getBeerDetailsStatistics().updateBestPairingCategory(bestPairingCategory);
		}

		totalStatisticsRepository.save(totalStatistics);
		beerRepository.save(findBeer);

	}

	@AfterReturning(value = "Pointcuts.updatePairing() && args(pairing, pairingId, category, image)")
	public void calculateBeerDetailsOnPairingUpdate(JoinPoint joinPoint, Pairing pairing, Long pairingId,
		String category, List<String> image) {

		// ---------------------------------------------------------------------------------------
		// TODO: 페어링 수정시 각종 계산
		// ---------------------------------------------------------------------------------------

		Beer findBeer = beerService.findBeerByPairingId(pairingId);

		String bestPairingCategory = beerService.findBestPairingCategory(findBeer);

		if (findBeer.getBeerDetailsStatistics().getBestPairingCategory() != bestPairingCategory) {
			findBeer.getBeerDetailsStatistics().updateBestPairingCategory(bestPairingCategory);
		}

		beerRepository.save(findBeer);
	}

	@Before(value = "Pointcuts.deletePairing() && args(pairingId)")
	public void calculateBeerDetailsOnPairingDeletion(JoinPoint joinPoint, Long pairingId) {

		// ---------------------------------------------------------------------------------------
		// TODO: 페어링 삭제시 각종 계산
		// ---------------------------------------------------------------------------------------

		Beer findBeer = beerService.findBeerByPairingId(pairingId);

		String bestPairingCategory;

		if (beerService.findBestPairingCategory(findBeer) != null) {
			bestPairingCategory = beerService.findBestPairingCategory(findBeer);
		} else {
			bestPairingCategory = null;
		}

		if (findBeer.getBeerDetailsStatistics().getBestPairingCategory() != bestPairingCategory) {
			findBeer.getBeerDetailsStatistics().updateBestPairingCategory(bestPairingCategory);
		}

		beerRepository.save(findBeer);

	}

	@Before(value = "Pointcuts.getBeer() && args(beerId)")
	public void test(JoinPoint joinPoint, Long beerId) {
		Beer findBeer = beerService.findVerifiedBeer(beerId);
		findBeer.addStatViewCount();
		beerRepository.save(findBeer);
	}

	@AfterReturning(value = "Pointcuts.clickRatingLike() && args(ratingId)")
	public void calculateBestPairingOnPairingLike(JoinPoint joinPoint, Long ratingId) {

		Rating findRating = ratingService.findRating(ratingId);
		Beer findBeer = beerService.findBeerByRatingId(ratingId);

		if (findBeer.getBeerDetailsBestRating() != null) {
			Long bestRatingId = findBeer.getBeerDetailsBestRating().getBestRatingId();
			Integer bestRatingStarCount = ratingService.findRating(bestRatingId).getLikeCount();

			if (findRating.getId().equals(bestRatingId)
				|| findRating.getLikeCount() > bestRatingStarCount) { // 좋아요 찍힌게 베스트 레이팅이거나 순위가 바뀌면 새로 계산
				Rating bestRating = beerService.findBestRating(findBeer);
				findBeer.updateBestRating(bestRating);
			}
		}
		beerRepository.save(findBeer);
	}

	@Before(value = "Pointcuts.updateUser() && args(edit)")
	public void calculateStarsOnUpdateUser(JoinPoint joinPoint, User edit) {

		User loginUser = userService.getLoginUser();
		Gender loginUserGender = loginUser.getGender();
		Gender updatedUserGender = edit.getGender();

		List<Beer> beerList = beerQueryRepository.findRatedBeersListByUserId(loginUser.getId());

		beerList.stream()
			.map(beer -> {

				Double star = beer.getRatingList().stream()
					.filter(rating -> rating.getUser().getId().equals(loginUser.getId()))
					.findFirst().get().getStar();

				if (loginUserGender == Gender.REFUSE) {
					if (updatedUserGender == Gender.FEMALE) {
						beer.calculateFemaleAverageStars(star);
					} else if (updatedUserGender == Gender.MALE) {
						beer.calculateMaleAverageStars(star);
					}
				} else if (loginUserGender == Gender.FEMALE) {
					if (updatedUserGender == Gender.REFUSE) {
						beer.deleteFemaleAverageStars(star);
					} else if (updatedUserGender == Gender.MALE) {
						beer.deleteFemaleAverageStars(star);
						beer.calculateMaleAverageStars(star);
					}
				} else if (loginUserGender == Gender.MALE) {
					if (updatedUserGender == Gender.REFUSE) {
						beer.deleteMaleAverageStars(star);
					} else if (updatedUserGender == Gender.FEMALE) {
						beer.deleteMaleAverageStars(star);
						beer.calculateFemaleAverageStars(star);
					}
				}
				return beerRepository.save(beer);
			}).collect(Collectors.toList());
	}

	private void createStatCookie(HttpServletRequest request, HttpServletResponse response, Cookie cookie) {

		String key = "visit_cookie";
		String value =
			"statistic" + "_" + "[" + LocalDateTime.now() + "]" + "_" + "[" + request.getContextPath()
				+ "]";

		ResponseCookie newStatCookie = ResponseCookie.from(key, value)
			.maxAge(2 * 60 * 60) // 두 시간
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();

		cookie.setMaxAge(0);

		response.setHeader("Set-Cookie", newStatCookie.toString());
	}

	@WebListener
	public static class sessionStatistics implements HttpSessionListener { // 세션 사용하게 되면 쓰기

		@Override
		public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		}

		@Override
		public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		}
	}
}
