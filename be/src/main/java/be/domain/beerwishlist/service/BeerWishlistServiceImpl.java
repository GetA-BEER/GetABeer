package be.domain.beerwishlist.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.beerwishlist.repository.BeerWishListQRepository;
import be.domain.beerwishlist.repository.BeerWishlistRepository;
import be.domain.beerwishlist.service.pattern.WishButton;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerWishlistServiceImpl implements BeerWishlistService {
	private final UserService userService;
	private final BeerService beerService;
	private final BeerWishlistRepository beerWishlistRepository;
	private final BeerWishListQRepository beerWishListQRepository;
	private static WishButton wishButton = new WishButton();

	@Override
	public void createWishlist(Beer beer, User user) {

		BeerWishlist saved = BeerWishlist.builder()
			.beer(beer)
			.user(user)
			.wished(true)
			.build();

		beerWishlistRepository.save(saved);
	}

	@Override
	public void deleteWishlist(Long beerId) {
		beerWishListQRepository.deleteByBeer(beerId);
	}

	@Override
	public BeerWishlist getIsWishlist(Beer beer) {

		User user;
		try {
			user = userService.getLoginUser();
		} catch (Exception e) {
			user = null;
		}

		return user == null ? beerWishlistRepository.findByBeer(beer) :
			beerWishlistRepository.findByBeerAndUser(beer, user);
	}

	@Override
	public Page<BeerWishlist> getUserWishlist(Integer page, Integer size) {

		User loginUser = userService.getLoginUser();
		PageRequest pageRequest = PageRequest.of(page - 1, size);
		return beerWishListQRepository.findByUserAndTrue(loginUser.getId(), pageRequest);
	}

	@Override
	@Transactional
	public void wishStatePattern(Beer beer, User user) {
		wishButton.clickButton(wishButton, beerWishlistRepository, user, beer);
	}

	@Override
	@Transactional
	public void verifyWishState(Long beerId) {
		Beer beer = beerService.getBeer(beerId);
		User user = userService.getLoginUser();

		if (beerWishlistRepository.findByBeerAndUser(beer, user) == null) {
			createWishlist(beer, user);
		} else {
			wishStatePattern(beer, user);
		}
	}
}
