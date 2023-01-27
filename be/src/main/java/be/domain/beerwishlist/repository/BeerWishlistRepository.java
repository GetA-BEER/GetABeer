package be.domain.beerwishlist.repository;

import be.domain.beerwishlist.entity.BeerWishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerWishlistRepository extends JpaRepository<BeerWishlist, Long> {
}
