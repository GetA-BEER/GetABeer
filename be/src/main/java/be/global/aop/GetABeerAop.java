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
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class GetABeerAop {

	private final BeerService beerService;
	private final BeerRepository beerRepository;

	/*
	 * 레이팅 새로 등록될 때마다 인기 태그 계산 후 변경 사항 저장
	 */
	@AfterReturning(value = "execution(* be.domain.rating.service.RatingService.create(..)) "
		+ "&& args(rating, beerId, ratingTag)")
	public void calculateAndMakeTagRanking(JoinPoint joinPoint, Rating rating, Long beerId, RatingTag ratingTag) {

		Beer findBeer = beerService.findVerifiedBeer(beerId);

		List<BeerTag> beerTags = beerService.findTop4BeerTags(findBeer);

		List<String> beerTagTypes = beerTags.stream()
			.map(beerTag -> beerTag.getBeerTagType().toString())
			.collect(Collectors.toList());

		List<String> presentBeerTagTypes = findBeer.getBeerDetailsTopTags().createList();

		if (beerTagTypes == presentBeerTagTypes) {
			return;
		} else {
			findBeer.getBeerDetailsTopTags().changeTags(beerTagTypes);
			beerRepository.save(findBeer);
		}
	}
}
