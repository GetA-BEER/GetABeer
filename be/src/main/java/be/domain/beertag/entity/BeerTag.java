package be.domain.beertag.entity;

import be.domain.beer.entity.BeerBeerTag;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.entity.MonthlyBeerBeerCategory;
import be.domain.beer.entity.MonthlyBeerBeerTag;
import be.domain.user.entity.UserBeerTag;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeerTag {

    @Id
    @Column(name = "beer_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private BeerTagType beerTagType;
    private Long count;

    @OneToMany(mappedBy = "beerTag")
    private List<BeerBeerTag> beerBeerTags = new ArrayList<>();

    @OneToMany(mappedBy = "beerTag")
    private List<UserBeerTag> userBeerTags = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "beerTag")
    private List<MonthlyBeerBeerTag> monthlyBeerBeerTags = new ArrayList<>();

    public void addBeerBeerTag(BeerBeerTag beerBeerTag) {
        this.beerBeerTags.add(beerBeerTag);
        if (beerBeerTag.getBeerTag() != this) {
            beerBeerTag.addBeerTag(this);
        }
    }

    public void addMonthlyBeerBeerTag(MonthlyBeerBeerTag monthlyBeerBeerTag) {
        this.monthlyBeerBeerTags.add(monthlyBeerBeerTag);
        if (monthlyBeerBeerTag.getBeerTag() != this) {
            monthlyBeerBeerTag.addBeerTag(this);
        }
    }
}
