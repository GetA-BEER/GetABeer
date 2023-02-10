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
public class BeerTagStatistics {
	@Id
	@Column(name = "rating_statistics_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CreatedDate
	private LocalDateTime createdAt;
	private Integer week;
	private Integer straw;
	private Integer gold;
	private Integer brown;
	private Integer black;
	private Integer sweet;
	private Integer sour;
	private Integer bitter;
	private Integer rough;
	private Integer fruity;
	private Integer flower;
	private Integer malty;
	private Integer noScent;
	private Integer weak;
	private Integer middle;
	private Integer strong;
	private Integer noCarbonation;
	private Integer male;
	private Integer female;
	private Integer refuse;
}
