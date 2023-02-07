package be.domain.beer.entity;

import javax.persistence.Embeddable;

import org.hibernate.annotations.ColumnDefault;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsStars {

	@ColumnDefault("0.0")
	private Double totalAverageStars;
	@ColumnDefault("0.0")
	private Double femaleAverageStars;
	@ColumnDefault("0.0")
	private Double maleAverageStars;

	@Builder
	public BeerDetailsStars(Double totalAverageStars, Double femaleAverageStars, Double maleAverageStars) {
		this.totalAverageStars = totalAverageStars;
		this.femaleAverageStars = femaleAverageStars;
		this.maleAverageStars = maleAverageStars;
	}
}
