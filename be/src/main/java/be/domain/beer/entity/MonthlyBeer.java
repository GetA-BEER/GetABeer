package be.domain.beer.entity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import be.domain.beertag.entity.BeerTag;
import be.global.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyBeer extends BaseTimeEntity implements Serializable {

	@Id
	@Column(name = "monthly_beer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String korName;
	private String country;
	private String thumbnail;
	private String categoryString;
	@Embedded
	private BeerDetailsTopTags beerDetailsTopTags;
	private Double abv;
	private Integer ibu;
	private Double averageStar;
	private Integer starCount;

	public void create(Beer beer) {
		this.id = beer.getId();
		this.korName = beer.getBeerDetailsBasic().getKorName();
		this.country = beer.getBeerDetailsBasic().getCountry();
		this.thumbnail = beer.getBeerDetailsBasic().getThumbnail();
		this.categoryString = beer.getBeerBeerCategories().stream()
			.map(beerBeerCategory -> beerBeerCategory.getBeerCategory().toString())
			.collect(Collectors.joining(", "));
		this.beerDetailsTopTags = beer.getBeerDetailsTopTags();
		this.abv = beer.getBeerDetailsBasic().getAbv();
		this.ibu = beer.getBeerDetailsBasic().getIbu();
		this.averageStar = beer.getBeerDetailsStars().getTotalAverageStars();
		this.starCount = beer.getBeerDetailsCounts().getTotalStarCount();
	}
}
