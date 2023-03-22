package be.domain.beer.entity;

import javax.persistence.Embeddable;

import org.hibernate.annotations.ColumnDefault;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsStatistics {

	@ColumnDefault("0")
	private Integer statViewCount;
	@ColumnDefault("0")
	private Integer statRatingCount;
	private String bestPairingCategory;

	@Builder
	public BeerDetailsStatistics(Integer statViewCount, Integer statRatingCount) {
		this.statViewCount = statViewCount;
		this.statRatingCount = statRatingCount;
	}

	public void addStatViewCount() {
		this.statViewCount++;
	}

	public void addStatRatingCount() {
		this.statRatingCount++;
	}

	public void updateBestPairingCategory(String bestPairingCategory) {
		this.bestPairingCategory = bestPairingCategory;
	}

	public void resetStatistic() {
		this.statViewCount = 0;
		this.statRatingCount = 0;
	}
}
