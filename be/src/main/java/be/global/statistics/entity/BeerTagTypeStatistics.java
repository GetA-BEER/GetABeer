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
public class BeerTagTypeStatistics {
	@Id
	@Column(name = "rating_statistics_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CreatedDate
	private LocalDateTime createdAt;
	private LocalDate date;
	private Long straw;
	private Long gold;
	private Long brown;
	private Long black;
	private Long sweet;
	private Long sour;
	private Long bitter;
	private Long rough;
	private Long fruity;
	private Long flower;
	private Long malty;
	private Long hoppy;
	private Long weak;
	private Long middle;
	private Long strong;
	private Long noCarbonation;
}
