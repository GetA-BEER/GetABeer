package be.domain.beer.entity;

import be.domain.beercategory.entity.BeerCategory;
import be.global.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
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
    private Long thumbnail;
    private Double abv;
    private Integer ibu;
    private Double totalAverageRating;
    private Integer starCount;
    @OneToMany(mappedBy = "monthlyBeer")
    private List<BeerCategory> beerCategories;

    public void create(Beer beer) {
        this.id = beer.getId();
        this.korName = beer.getBeerDetailsBasic().getKorName();
        this.country = beer.getBeerDetailsBasic().getCountry();
        this.thumbnail = beer.getBeerDetailsBasic().getThumbnail();
        this.abv = beer.getBeerDetailsBasic().getAbv();
        this.ibu = beer.getBeerDetailsBasic().getIbu();
        this.totalAverageRating = beer.getBeerDetailsRatings().getTotalAverageRating();
        this.starCount = beer.getBeerDetailsCounts().getStarCount();
        this.beerCategories = beer.getBeerBeerCategories().stream()
                .map(BeerBeerCategory::getBeerCategory)
                .collect(Collectors.toList());
    }
}
