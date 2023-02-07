package be.domain.beer.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.domain.beercategory.entity.BeerCategory;
import be.domain.beertag.entity.BeerTag;
import be.global.BaseTimeEntity;
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
public class BeerBeerTag extends BaseTimeEntity implements Serializable {

	private static final long serialVersionUID = 6494678977089006639L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "beer_beer_tag_id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "beer_id")
	private Beer beer;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "beer_tag_id")
	private BeerTag beerTag;

	public void addBeer(Beer beer) {
		this.beer = beer;
		if (!this.beer.getBeerBeerTags().contains(this)) {
			this.beer.getBeerBeerTags().add(this);
		}
	}

	public void addBeerTag(BeerTag beerTag) {
		this.beerTag = beerTag;
		if (!this.beerTag.getBeerBeerTags().contains(this)) {
			this.beerTag.addBeerBeerTag(this);
		}
	}

	public void remove(Beer beer, BeerTag beerTag) {
		this.beer = null;
		this.beerTag = null;
	}
}
