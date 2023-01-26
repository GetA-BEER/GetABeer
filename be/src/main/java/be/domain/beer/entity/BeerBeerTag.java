package be.domain.beer.entity;

import be.domain.beerCategory.entity.BeerCategory;
import be.domain.beerTag.entity.BeerTag;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeerBeerTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beer_beer_tag_id")
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "beer_id")
    private Beer beer;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "beer_tag_id")
    private BeerTag beerTag;

    public void addBeer(Beer beer) {
        this.beer = beer;
        if (!this.beer.getBeerBeerTags().contains(this)) {
            this.beer.getBeerBeerTags().add(this);
        }
    }

    public void addBeerTag(BeerTag beerTag) {
        this.beerTag = beerTag;
        if (!this.beerTag.getBeerBeerTags().contains(this)) {
            this.beerTag.addBeerBeerTag(this);
        }
    }
}
