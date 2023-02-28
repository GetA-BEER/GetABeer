package be.domain.beerwishlist.service;

import org.springframework.data.domain.Page;

import be.domain.beer.entity.Beer;
import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.user.entity.User;

public interface BeerWishlistService {
	void createWishlist(Beer beer, User user);

	void deleteWishlist(Long beerId);

	BeerWishlist getIsWishlist(Beer beer);

	Page<BeerWishlist> getUserWishlist(Integer page, Integer size);

	void wishStatePattern(Beer beer, User user);

	void verifyWishState(Long beerId);
}
