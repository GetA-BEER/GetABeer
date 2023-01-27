package be.domain.beertag.entity;

import be.domain.beer.entity.BeerBeerTag;
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
    @OneToMany(mappedBy = "beerTag")
    private List<BeerBeerTag> beerBeerTags = new ArrayList<>();

    public void addBeerBeerTag(BeerBeerTag beerBeerTag) {
        this.beerBeerTags.add(beerBeerTag);
        if (beerBeerTag.getBeerTag() != this) {
            beerBeerTag.addBeerTag(this);
        }
    }
}
