package be.global.statistics.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

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
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotalStatistics extends BaseTimeEntity {

	private static final Long serialVersionUID = 6494678977089006639L;
	
	@Id
	@Column(name = "total_statistics_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CreatedDate
	private LocalDate date;
	@ColumnDefault("0")
	private Integer totalVisitorCount;
	@ColumnDefault("0")
	private Integer totalBeerViewCount;
	@ColumnDefault("0")
	private Integer totalRatingCount;
	@ColumnDefault("0")
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
