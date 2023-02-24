package be.domain.search.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.repository.BeerQueryRepository;
import be.domain.search.repository.SearchQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
	private final BeerQueryRepository beerQueryRepository;
	private final SearchQueryRepository searchQueryRepository;

	@Transactional(readOnly = true)
	public Page<Beer> findBeersPageByQueryParam(String queryParam, Integer page) {

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		Page<Beer> beerPage;

		queryParam = queryParam.strip();

		if (queryParam.isEmpty()) {
			beerPage = new PageImpl<>(new ArrayList<>());
		} else if (queryParam.charAt(0) == '@') {
			beerPage = searchQueryRepository.findBeersPageByBeerCategoryQueryParam(queryParam, pageRequest);
		} else if (queryParam.charAt(0) == '#') {
			beerPage = searchQueryRepository.findBeersPageByBeerTagQueryParam(queryParam, pageRequest);
		} else {
			beerPage = searchQueryRepository.findBeersPageByQueryParam(queryParam, pageRequest);
		}

		return beerPage;
	}
}
