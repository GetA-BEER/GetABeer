package be.domain.beer.service;

import static be.domain.user.entity.enums.Role.*;
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
import be.domain.user.entity.User;
import be.domain.user.entity.enums.Role;
import be.domain.user.service.UserService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

	private final UserService userService;
	private final BeerRepository beerRepository;
	private final BeerQueryRepository beerQueryRepository;
	private final MonthlyBeerRepository monthlyBeerRepository;
	private final MonthlyBeerQueryRepository monthlyBeerQueryRepository;
	private final BeerCategoryService beerCategoryService;
	private final BeerBeerCategoryRepository beerBeerCategoryRepository;
	private final BeerBeerCategoryQueryRepository beerBeerCategoryQueryRepository;

	@Override
	@Transactional
	public Beer createBeer(Beer beer) {

		// User loginUser = userService.getLoginUser();
		//
		// if (!loginUser.getRoles().contains(ROLE_ADMIN)) {
		// 	throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		// }

		Beer savedBeer = Beer.builder().build();

		savedBeer.create(beer);
		saveBeerBeerCategories(savedBeer, beer);

		return beerRepository.save(savedBeer);
	}

	@Override
	@Transactional
	public Beer updateBeer(Beer beer, Long beerId) {

		User loginUser = userService.getLoginUser();

		if (!loginUser.getRoles().contains(ROLE_ADMIN)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		}

		Beer findBeer = findVerifiedBeer(beerId);

		findBeer.update(beer);

		deleteBeerBeerCategories(findBeer);

		saveBeerBeerCategories(findBeer, beer);

		return beerRepository.save(findBeer);
	}

	@Override
	@Transactional
	public void deleteBeer(Long beerId) {

		User loginUser = userService.getLoginUser();

		if (!loginUser.getRoles().contains(ROLE_ADMIN)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		} else {
			beerRepository.deleteById(beerId);
		}
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

	@Override
	@Transactional
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

	@Override
	@Transactional
	public Beer getBeer(Long beerId) {

		Beer findBeer = findVerifiedBeer(beerId);

		return findBeer;
	}

	@Override
	@Cacheable(MONTHLY_BEER)
	@Transactional(readOnly = true)
	public List<MonthlyBeer> findMonthlyBeers() {
		return monthlyBeerQueryRepository.findMonthlyBeer();
	}

	@Override
	@Transactional(readOnly = true)
	public List<BeerTag> findTop4BeerTags(Beer beer) {
		return beerQueryRepository.findTop4BeerTag(beer);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Beer> findSimilarBeers(Beer beer) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Beer> findMyPageBeers(Integer page) {
		PageRequest pageRequest = PageRequest.of(page - 1, 10);
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Beer findBeerByRatingId(Long ratingId) {
		return beerQueryRepository.findBeerByRatingId(ratingId);
	}

	@Override
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
}
