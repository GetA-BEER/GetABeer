package be.domain.beer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsBasic {

    private String korName;
    private String engName;
    private String country;
    private String thumbnail;
    private Double abv;
    private Integer ibu;

    @Builder
    public BeerDetailsBasic(String korName, String engName, String country,
                            String thumbnail, Double abv, Integer ibu) {
        this.korName = korName;
        this.engName = engName;
        this.country = country;
        this.thumbnail = thumbnail;
        this.abv = abv;
        this.ibu = ibu;
    }
}
