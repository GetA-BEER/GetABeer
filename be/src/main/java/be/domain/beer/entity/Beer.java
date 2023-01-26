package be.domain.beer.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import be.domain.beerWishlist.entity.BeerWishlist;
import be.domain.comment.entity.BeerComment;
import be.domain.pairing.entity.Pairing;
import be.global.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Beer extends BaseTimeEntity {

	@Id
	@Column(name = "beer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String korName;
	private String engName;
	private String country;
	private Long thumbnail;
	private Double abv;
	private Integer ibu;
	private Double totalAverageRating;
	private Double femaleAverageRating;
	private Double maleAverageRating;
	private Integer ratingCount;
	private Integer commentCount;
	private Integer pairingCount;
	private Boolean isWishListed;

	@ManyToOne
	@JoinColumn(name = "similar_beer_id")
	private Beer similarBeer;
	@OneToMany(mappedBy = "similarBeer")
	private List<Beer> similarBeers = new ArrayList<>();
	@Singular
	@OneToMany(mappedBy = "beer", cascade = CascadeType.REMOVE)
	private List<BeerBeerCategory> beerBeerCategories = new ArrayList<>();
	@Singular
	@OneToMany(mappedBy = "beer", cascade = CascadeType.REMOVE)
	private List<BeerBeerTag> beerBeerTags = new ArrayList<>();
	@OneToMany(mappedBy = "beer", cascade = CascadeType.REMOVE)
	private List<BeerWishlist> beerWishLists = new ArrayList<>();

	/* 💛 맥주 - 맥주 코멘트 일대다 연관관계 */
	@OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<BeerComment> beerCommentList = new ArrayList<>();

	/* 💛 맥주 - 맥주 코멘트 일대다 연관관계 편의 메서드 */
	public void addBeerCommentList(BeerComment beerComment) {
		beerCommentList.add(beerComment);

		if (beerComment.getBeer() != this) {
			beerComment.belongToBeer(this);
		}
	}

	/* 💙 맥주 - 페어링 일대다 연관관계 */
	@OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Pairing> pairingList = new ArrayList<>();

	/* 💙 맥주 - 페어링 일대다 연관관계 편의 메서드 */
	public void addPairingList(Pairing pairing) {
		pairingList.add(pairing);

		if (pairing.getBeer() != this) {
			pairing.belongToBeer(this);
		}
	}

	//    @OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST, orphanRemoval = true)
	//    private List<Image> images = new ArrayList<>();

}
