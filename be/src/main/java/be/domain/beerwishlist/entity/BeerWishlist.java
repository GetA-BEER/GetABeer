package be.domain.beerwishlist.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.domain.beer.entity.Beer;
import be.domain.user.entity.User;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeerWishlist extends BaseTimeEntity {

	@Id
	@Column(name = "beer_wishlist_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "beer_id")
	private Beer beer;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	public void addBeer(Beer beer) {
		this.beer = beer;
		if (!this.beer.getBeerWishlists().contains(this)) {
			this.beer.getBeerWishlists().add(this);
		}
	}

	public void addUser(User user) {
		this.user = user;
		if (!this.user.getBeerWishlists().contains(this)) {
			this.user.getBeerWishlists().add(this);
		}
	}
}
