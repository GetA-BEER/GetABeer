package be.domain.beer.repository;

import be.domain.beer.entity.MonthlyBeer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyBeerRepository extends JpaRepository<MonthlyBeer, Long> {
}
