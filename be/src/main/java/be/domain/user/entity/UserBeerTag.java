package be.domain.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.domain.beertag.entity.BeerTag;
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
public class UserBeerTag {

	@Id
	@Column(name = "user_beer_tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "beer_tag_id")
	private BeerTag beerTag;

	public void addUser(User user) {
		this.user = user;
		if (!this.user.getUserBeerTags().contains(this)) {
			this.user.getUserBeerTags().add(this);
		}
	}

	public void addBeerTag(BeerTag beerTag) {
		this.beerTag = beerTag;
		if (!this.user.getUserBeerTags().contains(this)) {
			this.user.getUserBeerTags().add(this);
		}
	}
}
