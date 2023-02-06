package be.domain.rating.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import be.domain.beertag.entity.BeerTagType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class RatingTag {

	protected RatingTag() {
	}

	@Id
	@Column(name = "rating_tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private BeerTagType color;

	@Enumerated(EnumType.STRING)
	private BeerTagType taste;

	@Enumerated(EnumType.STRING)
	private BeerTagType flavor;

	@Enumerated(EnumType.STRING)
	private BeerTagType carbonation;

	@OneToOne(mappedBy = "ratingTag", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Rating rating;

	public void oneToOneByRating(Rating rating) {
		this.rating = rating;

		if (rating.getRatingTag() != this) {
			rating.oneToOneByRatingTag(this);
		}
	}

	@Builder
	public RatingTag(Long id, BeerTagType color, BeerTagType taste, BeerTagType flavor, BeerTagType carbonation,
		Rating rating) {
		this.id = id;
		this.color = color;
		this.taste = taste;
		this.flavor = flavor;
		this.carbonation = carbonation;
		this.rating = rating;
	}

	public void updateRatingTag(RatingTag ratingTag) {
		this.color = ratingTag.getColor();
		this.taste = ratingTag.getTaste();
		this.flavor = ratingTag.getFlavor();
		this.carbonation = ratingTag.getCarbonation();
	}

	public List<BeerTagType> createBeerTagTypeList() {
		return List.of(color, taste, flavor, carbonation);
	}
}
