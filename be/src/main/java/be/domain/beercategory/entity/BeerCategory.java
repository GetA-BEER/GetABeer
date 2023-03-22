package be.domain.beercategory.entity;

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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.querydsl.core.annotations.QueryProjection;

import be.domain.beer.entity.BeerBeerCategory;
import be.domain.user.entity.UserBeerCategory;
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
public class BeerCategory implements Serializable {

	private static final long serialVersionUID = 6494678977089006639L;

	@Id
	@Column(name = "beer_category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private BeerCategoryType beerCategoryType;
	@ColumnDefault("0")
	private Integer statCount;

	@JsonManagedReference
	@OneToMany(mappedBy = "beerCategory")
	private List<BeerBeerCategory> beerBeerCategories = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "beerCategory")
	private List<UserBeerCategory> userBeerCategories = new ArrayList<>();

	public void addBeerBeerCategory(BeerBeerCategory beerBeerCategory) {
		this.beerBeerCategories.add(beerBeerCategory);
		if (beerBeerCategory.getBeerCategory() != this) {
			beerBeerCategory.addBeerCategory(this);
		}
	}

	public void addUserBeerCategory(UserBeerCategory userBeerCategory) {
		this.userBeerCategories.add(userBeerCategory);
		if (userBeerCategory.getBeerCategory() != this) {
			userBeerCategory.addBeerCategory(this);
		}
	}

	@QueryProjection
	public BeerCategory(Long id, BeerCategoryType beerCategoryType) {
		this.id = id;
		this.beerCategoryType = beerCategoryType;
	}

	public void addStatCount() {
		this.statCount++;
	}

	public void resetStatCount() {
		this.statCount = 0;
	}
}
