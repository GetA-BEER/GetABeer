package be.domain.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import be.domain.beer.entity.Beer;
import be.domain.beer.repository.BeerQueryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	BeerQueryRepository beerQueryRepository;

	public Page<Beer> findBeersPageByQueryParam(String queryParam, Integer page) {

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return beerQueryRepository.findBeersPageByQueryParam(queryParam, pageRequest);
	}
}
