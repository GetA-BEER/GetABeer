package be.global.statistics.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

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
public class TotalStatistics {
	@Id
	@Column(name = "total_statistics_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CreatedDate
	private LocalDate createdAt;
	private LocalDate date;
	private Integer totalVisitorCount;
	private Integer totalBeerViewCount;
	private Integer totalRatingCount;
	private Integer totalPairingCount;

	public void addTotalVisitorCount() {
		this.totalVisitorCount++;
	}

	public void addTotalBeerViewCount() {
		this.totalBeerViewCount++;
	}

	public void addTotalRatingCount() {
		this.totalRatingCount++;
	}

	public void addTotalPairingCount() {
		this.totalPairingCount++;
	}
}
