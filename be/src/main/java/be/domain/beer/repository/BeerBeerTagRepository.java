package be.domain.beer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.beer.entity.BeerBeerTag;

public interface BeerBeerTagRepository extends JpaRepository<BeerBeerTag, Long> {
}
