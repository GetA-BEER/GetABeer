package be.domain.beer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsStars {

    private Double totalAverageStars;
    private Double femaleAverageStars;
    private Double maleAverageStars;

    @Builder
    public BeerDetailsStars(Double totalAverageStars, Double femaleAverageStars, Double maleAverageStars) {
        this.totalAverageStars = totalAverageStars;
        this.femaleAverageStars = femaleAverageStars;
        this.maleAverageStars = maleAverageStars;
    }
}
