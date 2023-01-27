package be.domain.beercategory.repository;

import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.entity.BeerCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeerCategoryRepository extends JpaRepository<BeerCategory, Long> {

    Optional<BeerCategory> findBeerCategoryByBeerCategoryType(BeerCategoryType beerCategoryType);
}
