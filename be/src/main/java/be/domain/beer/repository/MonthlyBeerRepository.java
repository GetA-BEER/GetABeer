package be.domain.beer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.beer.entity.MonthlyBeer;

public interface MonthlyBeerRepository extends JpaRepository<MonthlyBeer, Long> {
}
