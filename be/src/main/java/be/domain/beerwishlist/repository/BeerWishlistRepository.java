package be.domain.beerwishlist.repository;

import be.domain.beer.entity.Beer;
import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerWishlistRepository extends JpaRepository<BeerWishlist, Long> {

	BeerWishlist findByBeerAndUser(Beer beer, User user);
}
