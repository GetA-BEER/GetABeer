package be.domain.beer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsCounts {

    private Integer totalStarCount;
    private Integer femaleStarCount;
    private Integer maleStarCount;
    private Integer commentCount;
    private Integer pairingCount;

    @Builder
    public BeerDetailsCounts(Integer totalStarCount, Integer femaleStarCount, Integer maleStarCount,
                             Integer commentCount, Integer pairingCount) {
        this.totalStarCount = totalStarCount;
        this.femaleStarCount = femaleStarCount;
        this.maleStarCount = maleStarCount;
        this.commentCount = commentCount;
        this.pairingCount = pairingCount;
    }
}
