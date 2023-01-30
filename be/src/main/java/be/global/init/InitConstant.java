package be.global.init;

import be.domain.beer.entity.BeerDetailsCounts;
import be.domain.beer.entity.BeerDetailsStars;

public class InitConstant {
    public static final Integer RAND_FIFTY = (int) ((Math.random() * 50) + 1);
    public static final Integer RAND_THIRTY = (int) ((Math.random() * 30) + 1);
    public static final Integer RAND_TWENTY = (int) ((Math.random() * 20) + 1);
    public static final Integer RAND_SEVEN = (int) (Math.random() * 7);
    public static final Integer RAND_SIXTEEN = (int) (Math.random() * 16);
    public static final Double RAND_STAR = (double) (int) ((Math.random() * 5) * 10) / 10;
    public static final BeerDetailsCounts BEER_DETAILS_COUNTS =
            BeerDetailsCounts.builder()
                    .totalStarCount(50)
                    .femaleStarCount(30)
                    .maleStarCount(20)
                    .commentCount(15)
                    .pairingCount(10)
                    .build();

}
