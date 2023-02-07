package be.domain.beer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;

import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.pairing.entity.Pairing;
import be.domain.rating.entity.Rating;
import be.global.BaseTimeEntity;
import be.global.statistics.entity.BeerStatistics;
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
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Beer extends BaseTimeEntity implements Serializable {

	private static final long serialVersionUID = 6494678977089006639L;

	@Id
	@Column(name = "beer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	private BeerDetailsBasic beerDetailsBasic;
	@Embedded
	private BeerDetailsStars beerDetailsStars;
	@Embedded
	private BeerDetailsCounts beerDetailsCounts;
	@Embedded
	private BeerDetailsTopTags beerDetailsTopTags;
	@Embedded
	@Nullable
	private BeerDetailsBestRating beerDetailsBestRating;
	@Embedded
	@Nullable
	private BeerDetailsStatistics beerDetailsStatistics;
	private Boolean isWishListed;

	@ManyToOne
	@JoinColumn(name = "similar_beer_id")
	private Beer similarBeer;
	@OneToMany(mappedBy = "similarBeer")
	private List<Beer> similarBeers = new ArrayList<>();
	@OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST)
	private List<BeerBeerCategory> beerBeerCategories = new ArrayList<>();
	@OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST)
	private List<BeerBeerTag> beerBeerTags = new ArrayList<>();
	@OneToMany(mappedBy = "beer", cascade = CascadeType.REMOVE)
	private List<BeerWishlist> beerWishlists = new ArrayList<>();
	@OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST)
	private List<Rating> ratingList = new ArrayList<>();
	@OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST)
	private List<Pairing> pairingList = new ArrayList<>();

	public void addBeerBeerCategories(List<BeerBeerCategory> beerBeerCategories) {
		this.beerBeerCategories = beerBeerCategories;
	}

	/*
	 * STUB DATA 생성을 위한 메서드
	 */
	public void addBeerDetailsCounts(BeerDetailsCounts beerDetailsCounts) {
		this.beerDetailsCounts = beerDetailsCounts;
	}

	public void addBeerDetailsStars(BeerDetailsStars beerDetailsStars) {
		this.beerDetailsStars = beerDetailsStars;
	}

	//    ------------------------------------------------------------------------

	public void addBeerBeerCategory(BeerBeerCategory beerBeerCategory) {
		this.beerBeerCategories.add(beerBeerCategory);
		if (beerBeerCategory.getBeer() != this) {
			beerBeerCategory.addBeer(this);
		}
	}

	public void addBeerBeerTag(BeerBeerTag beerBeerTag) {
		this.beerBeerTags.add(beerBeerTag);

		if (beerBeerTag.getBeer() != this) {
			beerBeerTag.addBeer(this);
		}
	}

	public void addBeerWishlists(BeerWishlist beerWishlist) {
		this.beerWishlists.add(beerWishlist);

		if (beerWishlist.getBeer() != this) {
			beerWishlist.addBeer(this);
		}
	}

	public void addPairingList(Pairing pairing) {
		pairingList.add(pairing);

		if (pairing.getBeer() != this) {
			pairing.belongToBeer(this);
		}
	}

	public void addRatingList(Rating rating) {
		ratingList.add(rating);

		if (rating.getBeer() != this) {
			rating.belongToBeer(this);
		}
	}

	public void create(Beer beer) {
		this.beerDetailsBasic = beer.getBeerDetailsBasic();
	}

	public void createTopTags(List<String> beerTagTypes) {

		this.beerDetailsTopTags =
			BeerDetailsTopTags.builder()
				.tag1(beerTagTypes.get(0))
				.tag2(beerTagTypes.get(1))
				.tag3(beerTagTypes.get(2))
				.tag4(beerTagTypes.get(3))
				.build();
	}

	public List<String> createTopTagList() {
		return List.of(this.beerDetailsTopTags.getTag1(),
			this.beerDetailsTopTags.getTag2(),
			this.beerDetailsTopTags.getTag3(),
			this.beerDetailsTopTags.getTag4()
		);
	}

	public void updateBestRating(Rating rating) {
		this.beerDetailsBestRating =
			BeerDetailsBestRating.builder()
				.bestRatingId(rating.getId())
				.bestStar(rating.getStar())
				.bestNickname(rating.getNickname())
				.bestContent(rating.getContent())
				.build();
	}

	public void update(Beer beer) {
		this.beerDetailsBasic = beer.getBeerDetailsBasic();
	}

	public void addDailyRatingCount() {
		this.getBeerDetailsStatistics().addDailyRatingCount();
	}

}
