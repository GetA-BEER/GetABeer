package be.domain.beer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import be.domain.beer.entity.Beer;

public interface BeerRepository extends JpaRepository<Beer, Long> {

	@Query(nativeQuery = true, value = "select * " +
		"from beer " +
		"order by " + "rand() " +
		"limit 5")
	List<Beer> findRandomBeer();
}
