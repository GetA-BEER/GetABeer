package be.domain.beer.entity;

import be.domain.beercategory.entity.BeerCategory;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeerBeerCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beer_beer_category_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "beer_id")
    private Beer beer;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "beer_category_id")
    private BeerCategory beerCategory;

    public void addBeer(Beer beer) {
        this.beer = beer;
        if (!this.beer.getBeerBeerCategories().contains(this)) {
            this.beer.getBeerBeerCategories().add(this);
        }
    }

    public void addBeerCategory(BeerCategory beerCategory) {
        this.beerCategory = beerCategory;
        if (!this.beerCategory.getBeerBeerCategories().contains(this)) {
            this.beerCategory.addBeerBeerCategory(this);
        }
    }
}
