package be.domain.beerwishlist.dto;

import be.domain.beer.dto.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class BeerWishlistDto {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class UserWishlist {
		private BeerDto.WishlistResponse beer;
		private Boolean isUserWish;
	}
}
