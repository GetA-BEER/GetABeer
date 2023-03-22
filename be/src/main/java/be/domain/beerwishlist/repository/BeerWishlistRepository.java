package be.domain.beerwishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.beer.entity.Beer;
import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.user.entity.User;

public interface BeerWishlistRepository extends JpaRepository<BeerWishlist, Long> {

	BeerWishlist findByBeer(Beer beer);

	BeerWishlist findByBeerAndUser(Beer beer, User user);
}
