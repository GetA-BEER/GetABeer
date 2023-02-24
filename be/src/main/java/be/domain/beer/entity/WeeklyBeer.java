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
public class WeeklyBeer extends BaseTimeEntity implements Serializable {

	private static final long serialVersionUID = 6494678977089006639L;

	@Id
	@Column(name = "weekly_beer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String korName;
	private String country;
	private String thumbnail;
	@Embedded
	private WeeklyBeerCategory weeklyBeerCategory;
	private Double abv;
	private Integer ibu;
	private Double averageStar;

	public void create(Beer beer) {
		this.id = beer.getId();
		this.korName = beer.getBeerDetailsBasic().getKorName();
		this.country = beer.getBeerDetailsBasic().getCountry();
		this.thumbnail = beer.getBeerDetailsBasic().getThumbnail();
		List<String> categoryList = beer.getBeerBeerCategories().stream()
			.map(beerBeerCategory -> beerBeerCategory.getBeerCategory().getBeerCategoryType().toString())
			.collect(Collectors.toList());
		if (categoryList.size() == 2) {
			this.weeklyBeerCategory.addCategories(categoryList);
		} else {
			this.weeklyBeerCategory.addCategory(categoryList.get(0));
		}
		this.abv = beer.getBeerDetailsBasic().getAbv();
		this.ibu = beer.getBeerDetailsBasic().getIbu();
		this.averageStar = beer.getBeerDetailsStars().getTotalAverageStars();
	}
}
