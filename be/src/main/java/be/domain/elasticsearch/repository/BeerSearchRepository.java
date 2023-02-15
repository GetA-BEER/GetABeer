package be.domain.elasticsearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import be.domain.beer.entity.Beer;

public interface BeerSearchRepository extends ElasticsearchRepository<Beer, Long>, BeerSearchCustomRepository {

	List<Beer> findByBeerDetailsBasic_KorNameContains(String korName);
}
