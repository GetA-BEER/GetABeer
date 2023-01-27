package be.domain.beer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsCounts {

    private Integer starCount;
    private Integer commentCount;
    private Integer pairingCount;

    @Builder
    public BeerDetailsCounts(Integer starCount, Integer commentCount, Integer pairingCount) {
        this.starCount = starCount;
        this.commentCount = commentCount;
        this.pairingCount = pairingCount;
    }
}
