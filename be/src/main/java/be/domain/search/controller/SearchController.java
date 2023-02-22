package be.domain.search.controller;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.mapper.BeerMapper;
import be.domain.search.service.SearchService;
import be.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

	private final SearchService searchService;
	private final BeerMapper beerMapper;

	@GetMapping
	public ResponseEntity<MultiResponseDto<BeerDto.SearchResponse>> getSearchResult(
		@RequestParam("query") String queryParam,
		@RequestParam(name = "page", defaultValue = "1") Integer page) {

		System.out.println(queryParam);

		Page<Beer> beerPage = searchService.findBeersPageByQueryParam(queryParam, page);

		PageImpl<BeerDto.SearchResponse> responsePage = beerMapper.beersPageToSearchResponse(beerPage);

		return ResponseEntity.ok(new MultiResponseDto<>(responsePage.getContent(), beerPage));
	}
}
