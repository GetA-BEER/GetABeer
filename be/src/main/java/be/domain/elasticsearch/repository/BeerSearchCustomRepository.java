package be.domain.elasticsearch.repository;

import java.awt.print.Pageable;
import java.util.List;

import be.domain.beer.entity.Beer;

public interface BeerSearchCustomRepository {
	List<Beer> searchByName(String name, Pageable pageable);
}
