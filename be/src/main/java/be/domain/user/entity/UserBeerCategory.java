package be.domain.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.domain.beercategory.entity.BeerCategory;
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
public class UserBeerCategory {

	@Id
	@Column(name = "user_beer_category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "beer_category_id")
	private BeerCategory beerCategory;

	public void addUser(User user) {
		this.user = user;
		if (!this.user.getUserBeerCategories().contains(this)) {
			this.user.getUserBeerCategories().add(this);
		}
	}

	public void addBeerCategory(BeerCategory beerCategory) {
		this.beerCategory = beerCategory;
		if (!this.user.getUserBeerCategories().contains(this)) {
			this.beerCategory.addUserBeerCategory(this);
		}
	}
}
