package be.domain.beerwishlist.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.beer.dto.BeerDto;
import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beerwishlist.dto.BeerWishlistDto;
import be.domain.beerwishlist.entity.BeerWishlist;

@Mapper(componentModel = "spring")
public interface BeerWishlistMapper {

	default Page<BeerWishlistDto.UserWishlist> beersAndWishlistToResponse(List<BeerWishlist> beerWishlists) {

		return new PageImpl<>(beerWishlists.stream()
			.map(beerWishlist -> BeerWishlistDto.UserWishlist.builder()
				.beer(BeerDto.WishlistResponse.builder()
					.beerId(beerWishlist.getBeer().getId())
					.korName(beerWishlist.getBeer().getBeerDetailsBasic().getKorName())
					.beerCategories(beerWishlist.getBeer().getBeerBeerCategories().stream()
						.map(category -> BeerCategoryDto.BeerResponse.builder()
							.beerCategoryType(category.getBeerCategory().getBeerCategoryType())
							.build())
						.collect(Collectors.toList()))
					.thumbnail(beerWishlist.getBeer().getBeerDetailsBasic().getThumbnail())
					.country(beerWishlist.getBeer().getBeerDetailsBasic().getCountry())
					.abv(beerWishlist.getBeer().getBeerDetailsBasic().getAbv())
					.ibu(beerWishlist.getBeer().getBeerDetailsBasic().getIbu())
					.build())
				.isUserWish(beerWishlist.getWished())
				.build()).collect(Collectors.toList()));
	}
}
