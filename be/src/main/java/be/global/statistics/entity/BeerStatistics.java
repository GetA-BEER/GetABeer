package be.global.statistics.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import be.domain.beer.entity.Beer;
import be.domain.beer.entity.BeerBeerCategory;
import be.domain.beer.entity.BeerDetailsStars;
import be.domain.beer.entity.BeerDetailsTopTags;
import be.global.BaseTimeEntity;
import ch.qos.logback.core.joran.conditional.IfAction;
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
public class BeerStatistics {
	@Id
	@Column(name = "beer_statistics_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CreatedDate
	private LocalDateTime createdAt;
	private LocalDate date;
	private Long beerId;
	private String korName;
	private String category1;
	private String category2;
	private BeerDetailsStars beerDetailsStars;
	private BeerDetailsTopTags beerDetailsTopTags;
	private Long viewCount;
	private Long ratingCount;

	public void create(Beer beer) {
		this.createdAt = LocalDateTime.now();
		this.date = LocalDate.now().minusDays(1);
		this.beerId = beer.getId();
		this.korName = beer.getBeerDetailsBasic().getKorName();

		List<String> list = beer.getBeerBeerCategories().stream()
			.map(beerBeerCategory -> beerBeerCategory.getBeerCategory().toString())
			.collect(Collectors.toList());
		if (list.size() == 1) {
			this.category1 = list.get(0);
			this.category2 = "";
		} else {
			this.category1 = list.get(0);
			this.category2 = list.get(1);
		}
		this.beerDetailsStars = beer.getBeerDetailsStars();
		this.beerDetailsTopTags = beer.getBeerDetailsTopTags();
		this.viewCount = beer.getBeerDetailsStatistics().getDailyViewCount();
		this.ratingCount = beer.getBeerDetailsStatistics().getDailyRatingCount();
	}
}
