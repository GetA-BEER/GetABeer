package be.domain.beerwishlist.service.pattern;

import be.domain.beer.entity.Beer;
import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.beerwishlist.repository.BeerWishlistRepository;
import be.domain.user.entity.User;

public interface WishState {
	void clickWish(WishButton wishButton, BeerWishlistRepository beerWishlistRepository, User user, Beer beer);
}

class WishStateFalse implements WishState {

	@Override
	public void clickWish(WishButton wishButton, BeerWishlistRepository beerWishlistRepository, User user, Beer beer) {
		BeerWishlist beerWishlist = beerWishlistRepository.findByBeerAndUser(beer, user);
		BeerWishlist saved = BeerWishlist.builder()
			.id(beerWishlist.getId())
			.beer(beer)
			.user(user)
			.wished(true)
			.build();
		beerWishlistRepository.save(saved);
		wishButton.setWishState(new WishStateTrue());
	}
}

class WishStateTrue implements WishState {

	@Override
	public void clickWish(WishButton wishButton, BeerWishlistRepository beerWishlistRepository, User user, Beer beer) {
		BeerWishlist beerWishlist = beerWishlistRepository.findByBeerAndUser(beer, user);
		BeerWishlist saved = BeerWishlist.builder()
			.id(beerWishlist.getId())
			.beer(beer)
			.user(user)
			.wished(false)
			.build();
		beerWishlistRepository.save(saved);
		wishButton.setWishState(new WishStateFalse());
	}
}