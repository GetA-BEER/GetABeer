package be.domain.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.repository.BeerQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
	private final BeerQueryRepository beerQueryRepository;

	@Transactional(readOnly = true)
	public Page<Beer> findBeersPageByQueryParam(String queryParam, Integer page) {

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return beerQueryRepository.findBeersPageByQueryParam(queryParam, pageRequest);
	}
}
