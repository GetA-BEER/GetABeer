package be.domain.beer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.beer.entity.WeeklyBeer;

public interface WeeklyBeerRepository extends JpaRepository<WeeklyBeer, Long> {
}
