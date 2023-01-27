package be.domain.beer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsRatings {

    private Double totalAverageRating;
    private Double femaleAverageRating;
    private Double maleAverageRating;

    @Builder
    public BeerDetailsRatings(Double totalAverageRating, Double femaleAverageRating, Double maleAverageRating) {
        this.totalAverageRating = totalAverageRating;
        this.femaleAverageRating = femaleAverageRating;
        this.maleAverageRating = maleAverageRating;
    }
}
