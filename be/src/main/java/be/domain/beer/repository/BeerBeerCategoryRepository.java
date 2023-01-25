package be.domain.beer.repository;

import be.domain.beer.entity.BeerBeerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerBeerCategoryRepository extends JpaRepository<BeerBeerCategory, Long> {
}
