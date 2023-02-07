package be.domain.beer.entity;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsStatistics {

	private Long dailyViewCount;
	private Long dailyRatingCount;

	@Builder
	public BeerDetailsStatistics(Long dailyViewCount, Long dailyRatingCount) {
		this.dailyViewCount = dailyViewCount;
		this.dailyRatingCount = dailyRatingCount;
	}

	public void addDailyViewCount() {
		this.dailyViewCount++;
	}

	public void addDailyRatingCount() {
		this.dailyRatingCount++;
	}
}
