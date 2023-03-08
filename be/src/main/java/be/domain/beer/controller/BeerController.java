package be.domain.beer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.entity.WeeklyBeer;
import be.domain.beer.mapper.BeerMapper;
import be.domain.beer.service.BeerService;
import be.domain.beertag.entity.BeerTag;
import be.domain.beerwishlist.entity.BeerWishlist;
import be.domain.beerwishlist.service.BeerWishlistService;
import be.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping({"/api/beers", "/api"})
@RequiredArgsConstructor
public class BeerController {
	private final BeerMapper beerMapper;
	private final BeerService beerService;
	private final BeerWishlistService beerWishlistService;

	@PostMapping("/add")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<BeerDto.DetailsResponse> postBeer(@Valid @RequestBody BeerDto.Post postBeer) {

		Beer beer = beerMapper.beerPostToBeer(postBeer);
		Beer createdBeer = beerService.createBeer(beer);
		createdBeer.addBeerBeerCategories(beer.getBeerBeerCategories()); // Response DTO
		BeerDto.DetailsResponse response = beerMapper.beerToPostDetailsResponse(createdBeer);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/{beer_id}/edit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<BeerDto.DetailsResponse> patchBeer(@PathVariable("beer_id") Long beerId,
		@Valid @RequestBody BeerDto.Patch patchBeer) {

		Beer beer = beerMapper.beerPatchToBeer(patchBeer);
		Beer updatedBeer = beerService.updateBeer(beer, beerId);
		updatedBeer.addBeerBeerCategories(beer.getBeerBeerCategories()); // Response DTO
		BeerDto.DetailsResponse response = beerMapper.beerToPatchDetailsResponse(updatedBeer);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{beer_id}")
	public ResponseEntity<BeerDto.DetailsResponse> getBeer(@PathVariable("beer_id") Long beerId) {

		Beer beer = beerService.getBeer(beerId);
		List<BeerTag> beerTags = beerService.findTop4BeerTags(beer);
		BeerWishlist beerWishlist = beerWishlistService.getIsWishlist(beer);
		BeerDto.DetailsResponse response = beerMapper.beerToDetailsResponse(beer, beerTags, beerWishlist);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{beer_id}/delete")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> deleteBeer(@PathVariable("beer_id") Long beerId) {

		beerService.deleteBeer(beerId);
		beerWishlistService.deleteWishlist(beerId);

		return ResponseEntity.noContent().build();
	}

	//    -----------------------------------------조회 API 세분화---------------------------------------------------

	@GetMapping("/monthly")
	public ResponseEntity<List<BeerDto.MonthlyBestResponse>> getMonthlyBeer() {

		List<MonthlyBeer> monthlyBeerList = beerService.findMonthlyBeers();
		List<BeerDto.MonthlyBestResponse> responses = beerMapper.beersToMonthlyBestBeerResponse(monthlyBeerList);

		return ResponseEntity.ok(responses);
	}

	@GetMapping("/weekly")
	public ResponseEntity<List<BeerDto.WeeklyBestResponse>> getWeeklyBeer() {

		List<WeeklyBeer> weeklyBeerList = beerService.findWeeklyBeers();
		List<BeerDto.WeeklyBestResponse> responses = beerMapper.beersToWeeklyBestBeerResponse(weeklyBeerList);

		return ResponseEntity.ok(responses);
	}

	@GetMapping("/recommend")
	public ResponseEntity<List<BeerDto.RecommendResponse>> getRecommendBeer() {

		List<Beer> beerList = beerService.findRecommendBeers();
		List<BeerDto.RecommendResponse> responses = beerMapper.beersToRecommendResponse(beerList);

		return ResponseEntity.ok(responses);
	}

	@GetMapping("/{beer_id}/similar")
	public ResponseEntity<List<BeerDto.SimilarResponse>> getSimilarBeer(@PathVariable("beer_id") Long beerId) {

		List<Beer> beerList = beerService.findSimilarBeers(beerId);
		List<BeerDto.SimilarResponse> responses = beerMapper.beersToSimilarBeerResponse(beerList);

		return ResponseEntity.ok(responses);
	}
}
