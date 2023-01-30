package be.domain.beercategory.entity;

import be.domain.beer.entity.BeerBeerCategory;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.entity.MonthlyBeerBeerCategory;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "beerCategory")
    private List<BeerBeerCategory> beerBeerCategories = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "beerCategory")
    private List<MonthlyBeerBeerCategory> monthlyBeerBeerCategories = new ArrayList<>();

    public void addBeerBeerCategory(BeerBeerCategory beerBeerCategory) {
        this.beerBeerCategories.add(beerBeerCategory);
        if (beerBeerCategory.getBeerCategory() != this) {
            beerBeerCategory.addBeerCategory(this);
        }
    }

    @QueryProjection
    public BeerCategory(Long id, BeerCategoryType beerCategoryType) {
        this.id = id;
        this.beerCategoryType = beerCategoryType;
    }
}
