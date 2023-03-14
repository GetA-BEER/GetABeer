package be.global.init;

import be.domain.beer.entity.BeerDetailsCounts;

public class InitConstant {
	public static final BeerDetailsCounts BEER_DETAILS_COUNTS =
		BeerDetailsCounts.builder()
			// .totalStarCount(50)
			.femaleStarCount(30)
			.maleStarCount(20)
			.ratingCount(15)
			.pairingCount(10)
			.build();
}
