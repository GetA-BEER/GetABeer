package be.domain.beer.entity;

import be.domain.beercategory.entity.BeerCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyBeerBeerCategory {

    @Id
    @Column(name = "monthly_beer_beer_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "monthly_beer_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @NotFound(action = NotFoundAction.IGNORE)
    private MonthlyBeer monthlyBeer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "beer_category_id")
    private BeerCategory beerCategory;

    public void addMonthlyBeer(MonthlyBeer monthlyBeer) {
        this.monthlyBeer = monthlyBeer;
        if (!this.monthlyBeer.getMonthlyBeerBeerCategories().contains(this)) {
            this.monthlyBeer.getMonthlyBeerBeerCategories().add(this);
        }
    }

    public void addBeerCategory(BeerCategory beerCategory) {
        this.beerCategory = beerCategory;
        if (!this.beerCategory.getMonthlyBeerBeerCategories().contains(this)) {
            this.beerCategory.addMonthlyBeerBeerCategory(this);
        }
    }

}
