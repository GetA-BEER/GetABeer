package be.domain.beer.entity;

import be.domain.beertag.entity.BeerTag;
import be.global.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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

    public void addMonthlyBeerBeerCategory(MonthlyBeerBeerCategory monthlyBeerBeerCategory) {
        this.monthlyBeerBeerCategories.add(monthlyBeerBeerCategory);
        if (monthlyBeerBeerCategory.getMonthlyBeer() != this) {
            monthlyBeerBeerCategory.addMonthlyBeer(this);
        }
    }

    public void addMonthlyBeerBeerTag(MonthlyBeerBeerTag monthlyBeerBeerTag) {
        this.monthlyBeerBeerTags.add(monthlyBeerBeerTag);
        if (monthlyBeerBeerTag.getMonthlyBeer() != this) {
            monthlyBeerBeerTag.addMonthlyBeer(this);
        }
    }

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
