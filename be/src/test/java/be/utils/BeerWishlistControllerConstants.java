package be.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.beer.dto.BeerDto;
import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beerwishlist.dto.BeerWishlistDto;

public class BeerWishlistControllerConstants {

	public static final BeerDto.WishlistResponse BEER_WITHLIST_RESPONSE_DTO =
		BeerDto.WishlistResponse.builder()
			.beerId(1L)
			.korName("한글 이름")
			.beerCategories(
				List.of(BeerCategoryDto.BeerResponse.builder().beerCategoryType(BeerCategoryType.ALE).build()))
			.thumbnail("맥주 이미지")
			.country("생산 국가")
			.abv(4.5)
			.ibu(10)
			.build();

	public static final BeerWishlistDto.UserWishlist USER_WISHLIST_RESPONSE_DTO =
		BeerWishlistDto.UserWishlist.builder()
			.beer(BEER_WITHLIST_RESPONSE_DTO)
			.isUserWish(true)
			.build();

	public static final Page<BeerWishlistDto.UserWishlist> USER_WISHLIST_RESPONSE_PAGE =
		new PageImpl<>(List.of(USER_WISHLIST_RESPONSE_DTO, USER_WISHLIST_RESPONSE_DTO));
}
