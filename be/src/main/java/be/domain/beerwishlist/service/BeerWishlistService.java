package be.domain.beerwishlist.service;

import be.domain.beer.entity.Beer;

public interface BeerWishlistService {
	void createWishlist(Long beerId);

	void deleteWishlist(Long beerId);

	Boolean getIsWishlist(Beer beer);
}
