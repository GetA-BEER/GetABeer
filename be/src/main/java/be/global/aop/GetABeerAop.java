package be.global.aop;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import be.domain.beer.entity.Beer;
import be.domain.beer.repository.BeerRepository;
import be.domain.beer.service.BeerService;
import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.entity.BeerTagType;
import be.domain.beertag.service.BeerTagService;
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;
import be.domain.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
// @Component
@RequiredArgsConstructor
public class GetABeerAop {

	private final BeerService beerService;
	private final BeerTagService beerTagService;
	private final RatingService ratingService;
	private final BeerRepository beerRepository;

	/*
	 * 레이팅 새로 등록될 때마다 인기 태그 계산 후 변경 사항 저장
	 */
	@AfterReturning(value = "execution(* be.domain.rating.service.RatingService.create(..)) "
		+ "&& args(rating, beerId, ratingTag)")
	public void calculateBeerTagRankingOnCreation(JoinPoint joinPoint, Rating rating, Long beerId,
		RatingTag ratingTag) {

		Beer findBeer = beerService.findVerifiedBeer(beerId);
		List<BeerTagType> postBeerTagTypes = ratingTag.createBeerTagTypeList(); // 입력받은 맥주 태그 타입

		findBeer.getBeerDetailsStatistics().addDailyRatingCount(); // 맥주 레이팅 숫자 늘려주기

		postBeerTagTypes.forEach(beerTagType -> { // 태그 카운트 늘려주기
			BeerTag findBeerTag = beerTagService.findVerifiedBeerTagByBeerTagType(beerTagType);
			findBeerTag.addDailyCount();
		});

		List<BeerTag> beerTags = beerService.findTop4BeerTags(findBeer); // 새로 계산하기

		List<String> beerTagTypes = beerTags.stream()
			.map(beerTag -> beerTag.getBeerTagType().toString())
			.collect(Collectors.toList());

		try {
			findBeer.getBeerDetailsTopTags();
		} catch (NullPointerException e) {
			findBeer.getBeerDetailsTopTags().changeTags(beerTagTypes);
			beerRepository.save(findBeer);
		}

		List<String> presentBeerTagTypes = findBeer.getBeerDetailsTopTags().createList(); // 기존 리스트 가져오기

		if (beerTagTypes != presentBeerTagTypes) { // 둘이 다르면 교체
			findBeer.getBeerDetailsTopTags().changeTags(beerTagTypes);
			beerRepository.save(findBeer);
		}
	}

	@AfterReturning(value = "execution(* be.domain.rating.service.RatingService.update(..)) "
		+ "&& args(rating, ratingId, ratingTag)")
	public void calculateBeerTagRankingOnUpdate(JoinPoint joinPoint, Rating rating, Long ratingId,
		RatingTag ratingTag) {

		Beer findBeer = beerService.findBeerByRatingId(ratingId);

		List<BeerTagType> postBeerTagTypes = ratingTag.createBeerTagTypeList(); // 입력받은 맥주 태그 타입

		postBeerTagTypes.forEach(beerTagType -> { // 태그 카운트 늘려주기
			BeerTag findBeerTag = beerTagService.findVerifiedBeerTagByBeerTagType(beerTagType);
			findBeerTag.addDailyCount();
		});

		List<BeerTag> beerTags = beerService.findTop4BeerTags(findBeer);

		List<String> beerTagTypes = beerTags.stream()
			.map(beerTag -> beerTag.getBeerTagType().toString())
			.collect(Collectors.toList());

		List<String> presentBeerTagTypes = findBeer.getBeerDetailsTopTags().createList();

		if (beerTagTypes != presentBeerTagTypes) { // 둘이 다르면 교체
			findBeer.getBeerDetailsTopTags().changeTags(beerTagTypes);
			beerRepository.save(findBeer);
		}
	}
}
