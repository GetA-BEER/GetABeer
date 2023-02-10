package be.domain.beer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import be.domain.beer.entity.Beer;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beertag.entity.BeerTag;
import be.domain.rating.entity.Rating;

public interface BeerService {
	Beer createBeer(Beer beer);

	Beer updateBeer(Beer beer, Long beerId);

	void deleteBeer(Long beerId);

	void createMonthlyBeer();

	Beer getBeer(Long beerId);

	List<MonthlyBeer> findMonthlyBeers();

	Rating findBestRating(Beer beer);

	List<BeerTag> findTop4BeerTags(Beer beer);

	List<Beer> findSimilarBeers(Beer beer);

	Page<Beer> findMyPageBeers(Integer page);

	Beer findBeerByRatingId(Long ratingId);

	Beer findVerifiedBeer(Long beerId);
}
