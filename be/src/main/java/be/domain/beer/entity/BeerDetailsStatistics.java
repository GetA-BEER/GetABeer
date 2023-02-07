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
	private Long dailyViewCount;
	@ColumnDefault("0")
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

	public void resetStatistic() {
		this.dailyViewCount = 0L;
		this.dailyRatingCount = 0L;
	}
}
