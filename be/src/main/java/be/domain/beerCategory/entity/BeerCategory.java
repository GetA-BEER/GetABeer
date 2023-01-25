package be.domain.beerCategory.entity;

import be.domain.beer.entity.BeerBeerCategory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeerCategory {

    @Id
    @Column(name = "beer_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BeerCategoryType beerCategoryType;

    @OneToMany(mappedBy = "beerCategory")
    private List<BeerBeerCategory> beerBeerCategories = new ArrayList<>();

    public void addBeerBeerCategory(BeerBeerCategory beerBeerCategory) {
        this.beerBeerCategories.add(beerBeerCategory);
        if (beerBeerCategory.getBeerCategory() != this) {
            beerBeerCategory.addBeerCategory(this);
        }
    }
}
