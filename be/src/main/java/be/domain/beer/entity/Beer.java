package be.domain.beer.entity;

import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.comment.entity.BeerComment;
import be.domain.pairing.entity.Pairing;
import be.global.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
import java.util.ArrayList;
import java.util.List;

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
    @Embedded
    private BeerDetailsBasic beerDetailsBasic;
    @Embedded
    private BeerDetailsStars beerDetailsStars;
    @Embedded
    private BeerDetailsCounts beerDetailsCounts;
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
    private List<BeerComment> beerCommentList = new ArrayList<>();
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

    public void addBeerCommentList(BeerComment beerComment) {
        beerCommentList.add(beerComment);

        if (beerComment.getBeer() != this) {
            beerComment.belongToBeer(this);
        }
    }

    public void create(Beer beer) {
        this.beerDetailsBasic = beer.getBeerDetailsBasic();
    }

    public void update(Beer beer) {
        this.beerDetailsBasic = beer.getBeerDetailsBasic();
    }


}
