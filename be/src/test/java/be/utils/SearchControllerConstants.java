package be.utils;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import be.domain.beer.dto.BeerDto;

public class SearchControllerConstants {

	public static final BeerDto.SearchResponse SEARCH_RESPONSE_DTO =
		BeerDto.SearchResponse.builder()
			.beerId(1L)
			.korName("한글 이름")
			.country("생산 국가")
			.category(List.of("ALE"))
			.abv(3.5)
			.ibu(17)
			.beerDetailsTopTags(List.of("GOLD"))
			.totalAverageStar(4.0)
			.totalStarcount(40)
			.thumbnail("썸네일 이미지 경로")
			.build();

	public static final PageImpl<BeerDto.SearchResponse> SEARCH_RESPONSE_PAGE =
		new PageImpl<>(List.of(SEARCH_RESPONSE_DTO, SEARCH_RESPONSE_DTO));

	public static final List<BeerDto.SearchResponse> SEARCH_RESPONSE_LIST =
		List.of(SEARCH_RESPONSE_DTO, SEARCH_RESPONSE_DTO);
}
