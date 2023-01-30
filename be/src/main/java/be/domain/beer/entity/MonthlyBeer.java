package be.domain.beer.entity;

import be.domain.beercategory.entity.BeerCategory;
import be.domain.beertag.entity.BeerTag;
import be.global.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyBeer extends BaseTimeEntity {

    @Id
    @Column(name = "monthly_beer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String korName;
    private String country;
    private String thumbnail;
    private Double abv;
    private Integer ibu;
    private Double averageStar;
    private Integer starCount;
    @OneToMany(mappedBy = "monthlyBeer", cascade = CascadeType.PERSIST)
    private List<MonthlyBeerBeerCategory> monthlyBeerBeerCategories = new ArrayList<>();
    @OneToMany(mappedBy = "monthlyBeer", cascade = CascadeType.PERSIST)
    private List<MonthlyBeerBeerTag> monthlyBeerBeerTags = new ArrayList<>();

    public void create(Beer beer, List<BeerTag> beerTags) {
        this.id = beer.getId();
        this.korName = beer.getBeerDetailsBasic().getKorName();
        this.country = beer.getBeerDetailsBasic().getCountry();
        this.thumbnail = beer.getBeerDetailsBasic().getThumbnail();
        this.abv = beer.getBeerDetailsBasic().getAbv();
        this.ibu = beer.getBeerDetailsBasic().getIbu();
        this.averageStar = beer.getBeerDetailsStars().getTotalAverageStars();
        this.starCount = beer.getBeerDetailsCounts().getTotalStarCount();
    }
}
