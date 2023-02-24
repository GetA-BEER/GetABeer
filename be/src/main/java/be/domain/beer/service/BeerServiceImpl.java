package be.domain.beer.service;

import static be.domain.user.entity.enums.Role.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.entity.BeerBeerCategory;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.entity.WeeklyBeer;
import be.domain.beer.repository.BeerBeerCategoryQueryRepository;
import be.domain.beer.repository.BeerBeerCategoryRepository;
import be.domain.beer.repository.BeerQueryRepository;
import be.domain.beer.repository.BeerRepository;
import be.domain.beer.repository.MonthlyBeerQueryRepository;
import be.domain.beer.repository.MonthlyBeerRepository;
import be.domain.beer.repository.WeeklyBeerQueryRepository;
import be.domain.beer.repository.WeeklyBeerRepository;
import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.service.BeerCategoryService;
import be.domain.beertag.entity.BeerTag;
import be.domain.rating.entity.Rating;
import be.domain.user.entity.User;
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
	private final WeeklyBeerRepository weeklyBeerRepository;
	private final WeeklyBeerQueryRepository weeklyBeerQueryRepository;
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

		// User loginUser = userService.getLoginUser();
		//
		// if (!loginUser.getRoles().contains(ROLE_ADMIN)) {
		// 	throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		// } else {
		beerRepository.deleteById(beerId);
		// }
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
	public void createWeeklyBeer() {

		List<WeeklyBeer> weeklyBeersTemp = new ArrayList<>();
		List<Beer> findBeers = beerQueryRepository.findWeeklyBeer();

		findBeers.forEach(beer -> {
			WeeklyBeer weeklyBeer = WeeklyBeer.builder().build();

			weeklyBeer.create(beer);

			weeklyBeersTemp.add(weeklyBeer);
		});

		weeklyBeerRepository.saveAll(weeklyBeersTemp);
	}

	@Override
	@Transactional
	public Beer getBeer(Long beerId) {

		return findVerifiedBeer(beerId);
	}

	@Override
	// @Cacheable(MONTHLY_BEER)
	@Transactional(readOnly = true)
	public List<MonthlyBeer> findMonthlyBeers() {
		return monthlyBeerQueryRepository.findMonthlyBeer();
	}

	@Override
	// @Cacheable(MONTHLY_BEER)
	@Transactional(readOnly = true)
	public List<WeeklyBeer> findWeeklyBeers() {
		return weeklyBeerQueryRepository.findWeeklyBeer();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Beer> findRecommendBeers() {

		try {
			userService.getLoginUser();
		} catch (BusinessLogicException e) {
			return null;
		}

		User findUser = userService.getLoginUser();

		if (findUser.getUserBeerCategories().size() == 0) {
			return beerRepository.findRandomBeer();
		} else {
			return beerQueryRepository.findRecommendBeer(findUser);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Beer> findCategoryBeers(String queryParam, Integer page) {

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return beerQueryRepository.findCategoryBeers(queryParam, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Rating findBestRating(Beer beer) {
		return beerQueryRepository.findBestRating(beer);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BeerTag> findTop4BeerTags(Beer beer) {
		return beerQueryRepository.findTop4BeerTag(beer);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Beer> findSimilarBeers(Long beerId) {

		Beer findBeer = findVerifiedBeer(beerId);
		return beerQueryRepository.findSimilarBeer(findBeer);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Beer> findWishlistBeers(Integer page) {

		User loginUser = userService.getLoginUser();

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return beerQueryRepository.findMyPageBeers(loginUser, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Rating> findMyRatingWithWishlist() {

		User loginUser = userService.getLoginUser();

		return beerQueryRepository.findMyRatingWithWishlist(loginUser);
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
