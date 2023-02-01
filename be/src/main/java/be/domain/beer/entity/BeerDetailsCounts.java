package be.domain.beer.entity;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsCounts {

	private Integer totalStarCount;
	private Integer femaleStarCount;
	private Integer maleStarCount;
	private Integer ratingCount;
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
