package be.domain.beerwishlist.service;

import org.springframework.stereotype.Service;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.beerwishlist.repository.BeerWishlistRepository;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerWishlistServiceImpl implements BeerWishlistService {
	private final UserService userService;
	private final BeerService beerService;
	private final BeerWishlistRepository beerWishlistRepository;

	@Override
	public void createWishlist(Long beerId) {

		User loginUser = userService.getLoginUser();

		Beer findBeer = beerService.findVerifiedBeer(beerId);

		BeerWishlist findBeerWishlist = beerWishlistRepository.findByBeerAndUser(findBeer, loginUser);

		if (findBeerWishlist == null) {

			findBeerWishlist = BeerWishlist.builder()
				.beer(findBeer)
				.user(loginUser)
				.build();

			beerWishlistRepository.save(findBeerWishlist);

		} else {
			throw new BusinessLogicException(ExceptionCode.WISH_LISTED);
		}
	}

	@Override
	public void deleteWishlist(Long beerId) {

		User loginUser = userService.getLoginUser();

		Beer findBeer = beerService.findVerifiedBeer(beerId);

		BeerWishlist findBeerWishlist = beerWishlistRepository.findByBeerAndUser(findBeer, loginUser);

		if (findBeerWishlist == null) {
			throw new BusinessLogicException(ExceptionCode.UN_WISH_LISTED);
		} else {

			beerWishlistRepository.delete(findBeerWishlist);
		}
	}

	@Override
	public Boolean getIsWishlist(Beer beer) {

		try {
			userService.getLoginUser();
		} catch (BusinessLogicException e) {
			return null;
		}

		User loginUser = userService.getLoginUser();

		return beerWishlistRepository.findByBeerAndUser(beer, loginUser) != null;
	}
}
