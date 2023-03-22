package be.domain.beer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.beer.entity.BeerBeerCategory;

public interface BeerBeerCategoryRepository extends JpaRepository<BeerBeerCategory, Long> {
}
