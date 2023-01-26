package be.domain.beer.entity;

import be.domain.beerWishlist.entity.BeerWishlist;
import be.global.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
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

//    @OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();
//    @OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<Pairing> pairings = new ArrayList<>();
//    @OneToMany(mappedBy = "beer", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<Image> images = new ArrayList<>();

}
