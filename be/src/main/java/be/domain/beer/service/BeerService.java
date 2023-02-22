package be.domain.beer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import be.domain.beer.entity.Beer;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.entity.WeeklyBeer;
import be.domain.beertag.entity.BeerTag;
import be.domain.rating.entity.Rating;

public interface BeerService {
	Beer createBeer(Beer beer);

	Beer updateBeer(Beer beer, Long beerId);

	void deleteBeer(Long beerId);

	void createMonthlyBeer();

	void createWeeklyBeer();

	Beer getBeer(Long beerId);

	List<MonthlyBeer> findMonthlyBeers();

	List<WeeklyBeer> findWeeklyBeers();

	List<Beer> findRecommendBeers();

	Rating findBestRating(Beer beer);

	Page<Beer> findCategoryBeers(String queryParam, Integer page);

	List<BeerTag> findTop4BeerTags(Beer beer);

	List<Beer> findSimilarBeers(Long beerId);

	Page<Beer> findWishlistBeers(Integer page);

	List<Rating> findMyRatingWithWishlist();

	Beer findBeerByRatingId(Long ratingId);
	
	Beer findVerifiedBeer(Long beerId);
}
