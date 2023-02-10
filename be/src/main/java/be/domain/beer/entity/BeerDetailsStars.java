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

	public void calculateTotalAverageStars(Double star, Integer ratingCount) {
		if (this.totalAverageStars == 0.0) {
			this.totalAverageStars = star;
		} else {
			Double numerator = totalAverageStars + star;
			int denominator = ratingCount + 1;
			this.totalAverageStars = format(numerator, denominator);
		}
	}

	public void calculateFemaleAverageStars(Double star, Integer femaleStarCount) {
		if (this.femaleAverageStars == 0.0) {
			this.femaleAverageStars = star;
		} else {
			Double numerator = femaleAverageStars + star;
			Integer denominator = femaleStarCount + 1;
			this.femaleAverageStars = format(numerator, denominator);
		}
	}

	public void calculateMaleAverageStars(Double star, Integer maleStarCount) {
		if (this.maleAverageStars == 0.0) {
			this.maleAverageStars = star;
		} else {
			Double numerator = maleAverageStars + star;
			Integer denominator = maleStarCount + 1;
			this.femaleAverageStars = format(numerator, denominator);
		}
	}

	public void updateTotalAverageStars(Double previousStar, Double afterStar, Integer ratingCount) {
		Double numerator = this.totalAverageStars * ratingCount - previousStar + afterStar;
		Integer denominator = ratingCount;
		this.totalAverageStars = format(numerator, denominator);
	}

	public void updateFemaleAverageStars(Double previousStar, Double afterStar, Integer ratingCount) {
		Double numerator = this.femaleAverageStars * ratingCount - previousStar + afterStar;
		Integer denominator = ratingCount;
		this.femaleAverageStars = format(numerator, denominator);
	}

	public void updateMaleAverageStars(Double previousStar, Double afterStar, Integer ratingCount) {
		Double numerator = this.maleAverageStars * ratingCount - previousStar + afterStar;
		Integer denominator = ratingCount;
		this.maleAverageStars = format(numerator, denominator);
	}

	public void deleteTotalAverageStars(Double deleteStar, Integer ratingCount) {
		Double numerator = this.totalAverageStars * ratingCount - deleteStar;
		Integer denominator = ratingCount - 1;
		this.totalAverageStars = format(numerator, denominator);
	}

	public void deleteFemaleAverageStars(Double deleteStar, Integer ratingCount) {
		Double numerator = this.femaleAverageStars * ratingCount - deleteStar;
		Integer denominator = ratingCount - 1;
		this.femaleAverageStars = format(numerator, denominator);
	}

	public void deleteMaleAverageStars(Double deleteStar, Integer ratingCount) {
		Double numerator = this.maleAverageStars * ratingCount - deleteStar;
		Integer denominator = ratingCount - 1;
		this.maleAverageStars = format(numerator, denominator);
	}

	private Double format(Double num, Integer den) {
		return (double)(Math.round(num * 100 / den)) / 100;
	}
}
