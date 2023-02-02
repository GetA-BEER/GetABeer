package be.domain.beer.service;

import static be.global.config.CacheConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.entity.BeerBeerCategory;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.repository.BeerBeerCategoryQueryRepository;
import be.domain.beer.repository.BeerBeerCategoryRepository;
import be.domain.beer.repository.BeerQueryRepository;
import be.domain.beer.repository.BeerRepository;
import be.domain.beer.repository.MonthlyBeerQueryRepository;
import be.domain.beer.repository.MonthlyBeerRepository;
import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.service.BeerCategoryService;
import be.domain.beertag.entity.BeerTag;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerService {

	private final BeerRepository beerRepository;
	private final BeerQueryRepository beerQueryRepository;
	private final MonthlyBeerRepository monthlyBeerRepository;
	private final MonthlyBeerQueryRepository monthlyBeerQueryRepository;
	private final BeerCategoryService beerCategoryService;
	private final BeerBeerCategoryRepository beerBeerCategoryRepository;
	private final BeerBeerCategoryQueryRepository beerBeerCategoryQueryRepository;

	@Transactional
	public Beer createBeer(Beer beer) {

		Beer savedBeer = Beer.builder().build();

		savedBeer.create(beer);
		saveBeerBeerCategories(savedBeer, beer);

		return beerRepository.save(savedBeer);
	}

	@Transactional
	public Beer updateBeer(Beer beer, Long beerId) {

		Beer findBeer = findVerifiedBeer(beerId);

		findBeer.update(beer);

		beerBeerCategoryQueryRepository.deleteAllByBeerId(beerId);

		saveBeerBeerCategories(findBeer, beer);

		return beerRepository.save(findBeer);
	}

	@Transactional
	public void deleteBeer(Long beerId) {

		beerRepository.deleteById(beerId);
	}

	//    public Beer isWishListedBeer(Beer beer, User user){
	//        return null;
	//    }

	//    public Comment isLikedComment(Comment comment, User user){
	//        return null;
	//    }

	//    public Beer isLikedComments(Long beerId){
	//        return null;
	//    }

	@Cacheable(MONTHLY_BEER)
	@Transactional(readOnly = true)
	public List<MonthlyBeer> findMonthlyBeers() {

		Optional<List<MonthlyBeer>> monthlyBeers = monthlyBeerQueryRepository.findMonthlyBeer();

		return null;
	}

	@Cacheable(TOP_FOUR_BEER_TAG)
	@Transactional(readOnly = true)
	public List<BeerTag> findTop4BeerTags(Beer beer) {
		return beerQueryRepository.findTop4BeerTag(beer);
	}

	@Transactional(readOnly = true)
	public List<Beer> findSimilarBeers(Beer beer) {
		return null;
	}

	@Transactional(readOnly = true)
	public Page<Beer> findMyPageBeers(Integer page) {

		PageRequest pageRequest = PageRequest.of(page - 1, 10);
		return null;
	}

	@Transactional(readOnly = true)
	public Beer findVerifiedBeer(Long beerId) {

		Optional<Beer> optionalBeer = beerRepository.findById(beerId);

		return optionalBeer.orElseThrow(() ->
			new BusinessLogicException(ExceptionCode.BEER_NOT_FOUND));
	}

	private void saveBeerBeerCategories(Beer savedBeer, Beer beer) {

		beer.getBeerBeerCategories()
			.forEach(beerBeerCategory -> {
				BeerCategory beerCategory =
					beerCategoryService.findVerifiedBeerCategory(
						beerBeerCategory.getBeerCategory().getBeerCategoryType());
				BeerBeerCategory savedBeerBeerCategory =
					BeerBeerCategory.builder()
						.beer(savedBeer)
						.beerCategory(beerCategory)
						.build();
				beerBeerCategoryRepository.save(savedBeerBeerCategory);
			});
	}

	private void deleteBeerBeerCategories(Beer findBeer) {
		findBeer.getBeerBeerCategories().forEach(beerBeerCategory -> {
			beerBeerCategory.remove(findBeer, beerBeerCategory.getBeerCategory());
			beerBeerCategoryQueryRepository.delete(beerBeerCategory);
		});
	}

	public void createMonthlyBeer() {

		List<MonthlyBeer> monthlyBeersTemp = new ArrayList<>();
		List<Beer> findBeers = beerQueryRepository.findMonthlyBeer();

		findBeers.forEach(beer -> {
			MonthlyBeer monthlyBeer = MonthlyBeer.builder().build();

			monthlyBeer.create(beer);

			monthlyBeersTemp.add(monthlyBeer);
		});

		monthlyBeerRepository.saveAll(monthlyBeersTemp);
	}
}
