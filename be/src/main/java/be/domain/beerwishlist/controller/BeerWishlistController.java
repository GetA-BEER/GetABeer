package be.domain.beerwishlist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.domain.beerwishlist.service.BeerWishlistServiceImpl;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping({"/api/beers"})
@RequiredArgsConstructor
public class BeerWishlistController {
	private final BeerWishlistServiceImpl beerWishlistServiceImpl;

	@PatchMapping("/{beer_id}/wishlist/add")
	public ResponseEntity<HttpStatus> postWishlist(@PathVariable("beer_id") Long beerId) {

		beerWishlistServiceImpl.createWishlist(beerId);

		return new ResponseEntity(HttpStatus.OK);
	}

	@PatchMapping("/{beer_id}/wishlist/remove")
	public ResponseEntity<HttpStatus> deleteWishlist(@PathVariable("beer_id") Long beerId) {

		beerWishlistServiceImpl.deleteWishlist(beerId);

		return new ResponseEntity(HttpStatus.OK);
	}
}
