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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeerTag implements Serializable {

	private static final long serialVersionUID = 6494678977089006639L;

	@Id
	@Column(name = "beer_tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private BeerTagType beerTagType;
	@ColumnDefault("0")
	private Integer statCount;

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
		this.statCount++;
	}

	public void subtractDailyCount() {
		if (this.statCount != 0) {
			this.statCount--;
		} else {
			this.statCount = 0;
		}
	}

}
