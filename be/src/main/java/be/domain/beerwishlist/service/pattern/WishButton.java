package be.domain.beerwishlist.service.pattern;

import be.domain.beer.entity.Beer;
import be.domain.beerwishlist.repository.BeerWishlistRepository;
import be.domain.user.entity.User;

public class WishButton {
	private WishState wishState = new WishStateTrue();

	public void setWishState(WishState wishState) {
		this.wishState = wishState;
	}

	public void clickButton(WishButton wishButton, BeerWishlistRepository beerWishlistRepository, User user, Beer beer) {
		wishState.clickWish(wishButton, beerWishlistRepository, user, beer);
	}
}
