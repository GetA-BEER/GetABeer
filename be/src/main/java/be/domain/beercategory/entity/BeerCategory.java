package be.domain.beercategory.entity;

import be.domain.beer.entity.BeerBeerCategory;
import be.domain.beer.entity.MonthlyBeer;
import com.querydsl.core.annotations.QueryProjection;
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
    private Long quantity;

    @Enumerated(EnumType.STRING)
    private BeerCategoryType beerCategoryType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "monthly_beer_id")
    private MonthlyBeer monthlyBeer;

    @OneToMany(mappedBy = "beerCategory")
    private List<BeerBeerCategory> beerBeerCategories = new ArrayList<>();

    public void addBeerBeerCategory(BeerBeerCategory beerBeerCategory) {
        this.beerBeerCategories.add(beerBeerCategory);
        if (beerBeerCategory.getBeerCategory() != this) {
            beerBeerCategory.addBeerCategory(this);
        }
    }

    @QueryProjection
    public BeerCategory(Long id, Long quantity, BeerCategoryType beerCategoryType) {
        this.id = id;
        this.quantity = quantity;
        this.beerCategoryType = beerCategoryType;
    }
}
