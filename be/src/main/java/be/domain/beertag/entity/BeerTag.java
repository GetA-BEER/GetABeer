package be.domain.beertag.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import be.domain.beer.entity.BeerBeerTag;
import be.domain.user.entity.UserBeerTag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeerTag implements Serializable {

	@Id
	@Column(name = "beer_tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private BeerTagType beerTagType;
	private Long dailyCount;

	@OneToMany(mappedBy = "beerTag")
	private List<BeerBeerTag> beerBeerTags = new ArrayList<>();

	@OneToMany(mappedBy = "beerTag")
	private List<UserBeerTag> userBeerTags = new ArrayList<>();

	public void addBeerBeerTag(BeerBeerTag beerBeerTag) {
		this.beerBeerTags.add(beerBeerTag);
		if (beerBeerTag.getBeerTag() != this) {
			beerBeerTag.addBeerTag(this);
		}
	}

	public void addDailyCount() {
		this.dailyCount++;
	}

	// public void subtractDailyCount() {
	// 	if (this.dailyCount != 0L) {
	// 		this.dailyCount--;
	// 	} else {
	// 		this.dailyCount = 0L;
	// 	}
	// }

}
