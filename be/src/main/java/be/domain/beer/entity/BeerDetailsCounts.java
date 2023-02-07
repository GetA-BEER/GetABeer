package be.domain.beer.entity;

import javax.persistence.Embeddable;

import org.hibernate.annotations.ColumnDefault;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsCounts {

	@ColumnDefault("0")
	private Integer totalStarCount;
	@ColumnDefault("0")
	private Integer femaleStarCount;
	@ColumnDefault("0")
	private Integer maleStarCount;
	@ColumnDefault("0")
	private Integer ratingCount;
	@ColumnDefault("0")
	private Integer pairingCount;

	@Builder
	public BeerDetailsCounts(Integer totalStarCount, Integer femaleStarCount, Integer maleStarCount,
		Integer ratingCount, Integer pairingCount) {
		this.totalStarCount = totalStarCount;
		this.femaleStarCount = femaleStarCount;
		this.maleStarCount = maleStarCount;
		this.ratingCount = ratingCount;
		this.pairingCount = pairingCount;
	}
}
