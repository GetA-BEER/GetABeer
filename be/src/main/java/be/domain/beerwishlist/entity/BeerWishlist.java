package be.domain.beerwishlist.entity;

import be.domain.beer.entity.Beer;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeerWishlist {

    @Id
    @Column(name = "beer_wishlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "beer_id")
    private Beer beer;

    public void addBeer(Beer beer) {
        this.beer = beer;
        if (!this.beer.getBeerWishlists().contains(this)) {
            this.beer.getBeerWishlists().add(this);
        }
    }
}
