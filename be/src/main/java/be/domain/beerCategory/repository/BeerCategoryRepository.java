package be.domain.beerCategory.repository;

import be.domain.beerCategory.entity.BeerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerCategoryRepository extends JpaRepository<BeerCategory, Long> {
}
