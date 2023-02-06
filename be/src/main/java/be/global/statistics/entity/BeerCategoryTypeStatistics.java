package be.global.statistics.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beertag.entity.BeerTagType;
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
public class BeerCategoryTypeStatistics {
	@Id
	@Column(name = "rating_statistics_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CreatedDate
	private LocalDateTime createdAt;
	private LocalDate date;
	private Long ale;
	private Long larger;
	private Long weizen;
	private Long dunkel;
	private Long pilsener;
	private Long fruitBeer;
	private Long nonAlcoholic;

}
