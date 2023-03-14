package be.domain.elasticsearch.entity;

import javax.persistence.Embedded;
import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import be.domain.beer.entity.Beer;
import be.domain.beer.entity.BeerDetailsTopTags;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Document(indexName = "beer")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @Mapping(mappingPath = "elastic/beer-mapping.json")
// @Setting(settingPath = "elastic/beer-setting.json")
public class BeerDocument {

	@Id
	private Long id;
	private String korName;
	private String engName;
	private String thumbnail;
	private String country;
	private String category;
	private Double abv;
	private Integer ibu;
	private Double totalAverageStars;
	private Integer totalStarCount;
	@Embedded
	private BeerDetailsTopTags beerDetailsTopTags;

	public static BeerDocument toEntity(Beer beer) {
		return BeerDocument.builder()
			.id(beer.getId())
			.korName(beer.getBeerDetailsBasic().getKorName())
			.engName(beer.getBeerDetailsBasic().getEngName())
			.thumbnail(beer.getBeerDetailsBasic().getThumbnail())
			.country(beer.getBeerDetailsBasic().getCountry())
			.abv(beer.getBeerDetailsBasic().getAbv())
			.ibu(beer.getBeerDetailsBasic().getIbu())
			.totalAverageStars(beer.getBeerDetailsStars().getTotalAverageStars())
			.totalStarCount(beer.getBeerDetailsCounts().getRatingCount())
			.beerDetailsTopTags(beer.getBeerDetailsTopTags())
			.build();
	}
}
