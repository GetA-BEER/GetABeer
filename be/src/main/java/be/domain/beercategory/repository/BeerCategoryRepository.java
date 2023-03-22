package be.domain.beercategory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.entity.BeerCategoryType;

public interface BeerCategoryRepository extends JpaRepository<BeerCategory, Long> {

	Optional<BeerCategory> findBeerCategoryByBeerCategoryType(BeerCategoryType beerCategoryType);
}
