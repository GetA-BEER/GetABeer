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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import be.domain.beercategory.entity.BeerCategory;
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
public class BeerBeerCategory extends BaseTimeEntity implements Serializable {

	private static final long serialVersionUID = 6494678977089006639L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "beer_beer_category_id")
	private Long id;
	@JsonBackReference
	@JoinColumn(name = "beer_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@NotFound(action = NotFoundAction.IGNORE)
	private Beer beer;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "beer_category_id")
	private BeerCategory beerCategory;

	public void addBeer(Beer beer) {
		this.beer = beer;
		if (!this.beer.getBeerBeerCategories().contains(this)) {
			this.beer.getBeerBeerCategories().add(this);
		}
	}

	public void addBeerCategory(BeerCategory beerCategory) {
		this.beerCategory = beerCategory;
		if (!this.beerCategory.getBeerBeerCategories().contains(this)) {
			this.beerCategory.addBeerBeerCategory(this);
		}
	}

	public void remove(Beer beer, BeerCategory beerCategory) {
		this.beer = null;
		this.beerCategory = null;
	}
}
