package be.domain.beerWishlist.repository;

import be.domain.beerWishlist.entity.BeerWishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerWishlistRepository extends JpaRepository<BeerWishlist, Long> {
}
