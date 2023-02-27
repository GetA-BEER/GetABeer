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

	private static final long serialVersionUID = 6494678977089006639L;

	@Id
	@Column(name = "monthly_beer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String korName;
	// private String country;
	private String thumbnail;
	// @Embedded
	// private MonthlyBeerCategory monthlyBeerCategory;
	@Embedded
	private BeerDetailsTopTags beerDetailsTopTags;
	// private Double abv;
	// private Integer ibu;
	private Double averageStar;
	private Integer ratingCount;
	@Embedded
	private BeerDetailsBestRating beerDetailsBestRating;

	public void create(Beer beer) {
		this.id = beer.getId();
		this.korName = beer.getBeerDetailsBasic().getKorName();
		this.thumbnail = beer.getBeerDetailsBasic().getThumbnail();
		this.beerDetailsTopTags = beer.getBeerDetailsTopTags();
		this.averageStar = beer.getBeerDetailsStars().getTotalAverageStars();
		this.ratingCount = beer.getBeerDetailsCounts().getRatingCount();
		this.beerDetailsBestRating = beer.getBeerDetailsBestRating();
	}

	public List<String> createTagList() {
		return List.of(this.beerDetailsTopTags.getTag1(), this.beerDetailsTopTags.getTag2(),
			this.beerDetailsTopTags.getTag3(), this.beerDetailsTopTags.getTag4());
	}

	// public void create(Beer beer) {
	// 	this.id = beer.getId();
	// 	this.korName = beer.getBeerDetailsBasic().getKorName();
	// 	this.country = beer.getBeerDetailsBasic().getCountry();
	// 	this.thumbnail = beer.getBeerDetailsBasic().getThumbnail();
	// 	List<String> categoryString = beer.getBeerBeerCategories().stream()
	// 		.map(beerBeerCategory -> beerBeerCategory.getBeerCategory().toString())
	// 		.collect(Collectors.toList());
	// 	if (categoryString.size() == 2) {
	// 		this.monthlyBeerCategory.addCategories(categoryString);
	// 	} else {
	// 		this.monthlyBeerCategory.addCategory(categoryString.get(0));
	// 	}
	// 	this.beerDetailsTopTags = beer.getBeerDetailsTopTags();
	// 	this.abv = beer.getBeerDetailsBasic().getAbv();
	// 	this.ibu = beer.getBeerDetailsBasic().getIbu();
	// 	this.averageStar = beer.getBeerDetailsStars().getTotalAverageStars();
	// 	this.ratingCount = beer.getBeerDetailsCounts().getRatingCount();
	// 	this.beerDetailsBestRating = beer.getBeerDetailsBestRating();
	// }
}
