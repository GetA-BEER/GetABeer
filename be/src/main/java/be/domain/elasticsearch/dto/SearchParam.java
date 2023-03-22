package be.domain.elasticsearch.dto;

import javax.persistence.Embedded;

import be.domain.beer.entity.BeerDetailsTopTags;
import lombok.Getter;

@Getter
public class SearchParam {

	private String korName;
	private String engName;
	private String country;
	private String category;
	@Embedded
	private BeerDetailsTopTags beerDetailsTopTags;
}
