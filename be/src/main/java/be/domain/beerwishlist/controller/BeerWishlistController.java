package be.domain.beerwishlist.controller;

import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.beerwishlist.dto.BeerWishlistDto;
import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.beerwishlist.mapper.BeerWishlistMapper;
import be.domain.beerwishlist.service.BeerWishlistService;
import be.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping({"/api/beers", "/api"})
@RequiredArgsConstructor
public class BeerWishlistController {
	private final BeerWishlistMapper beerWishlistMapper;
	private final BeerWishlistService beerWishlistService;

	@PatchMapping("/{beer-id}/wish")
	public ResponseEntity<String> wishBeer(@PathVariable("beer-id") @Positive Long beerId) {
		beerWishlistService.verifyWishState(beerId);

		return ResponseEntity.ok("Success to click Wish.");
	}

	@GetMapping("/mypage/wishlist")
	public ResponseEntity<MultiResponseDto<BeerWishlistDto.UserWishlist>> getMyPageBeer(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {

		Page<BeerWishlist> beerWishlists = beerWishlistService.getUserWishlist(page);
		Page<BeerWishlistDto.UserWishlist> responses = beerWishlistMapper.beersAndWishlistToResponse(
			beerWishlists.getContent());

		return ResponseEntity.ok(new MultiResponseDto<>(responses.getContent(), beerWishlists));
	}
}
