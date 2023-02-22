package be.domain.beer.controller;

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.entity.WeeklyBeer;
import be.domain.beer.mapper.BeerMapper;
import be.domain.beer.service.BeerService;
import be.domain.beertag.entity.BeerTag;
import be.domain.beerwishlist.service.BeerWishlistService;
import be.domain.rating.entity.Rating;
import be.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@Validated
@RestController
@RequestMapping({"/api/beers", "/api"})
@RequiredArgsConstructor
public class BeerController {
	private final BeerMapper beerMapper;
	private final BeerService beerService;
	private final BeerWishlistService beerWishlistService;

	@PostMapping("/add")
	public ResponseEntity<BeerDto.DetailsResponse> postBeer(@Valid @RequestBody BeerDto.Post postBeer) {

		Beer beer = beerMapper.beerPostToBeer(postBeer);
		Beer createdBeer = beerService.createBeer(beer);
		createdBeer.addBeerBeerCategories(beer.getBeerBeerCategories()); // Response DTO
		BeerDto.DetailsResponse response = beerMapper.beerToPostDetailsResponse(createdBeer);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/{beer_id}/edit")
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
		Boolean isWishlist = beerWishlistService.getIsWishlist(beer);
		BeerDto.DetailsResponse response = beerMapper.beerToDetailsResponse(beer, beerTags, isWishlist);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{beer_id}/delete")
	public ResponseEntity<String> deleteBeer(@PathVariable("beer_id") Long beerId) {

		beerService.deleteBeer(beerId);

		return ResponseEntity.noContent().build();
	}

	//    -----------------------------------------조회 API 세분화---------------------------------------------------

	@GetMapping("/monthly")
	public ResponseEntity<List<BeerDto.MonthlyBestResponse>> getMonthlyBeer() {

		List<MonthlyBeer> monthlyBeerList = beerService.findMonthlyBeers();
		List<BeerDto.MonthlyBestResponse> responses = beerMapper.beersToMonthlyBestBeerResponse(monthlyBeerList);

		return ResponseEntity.ok(responses);
	}

	@GetMapping("/category")
	public ResponseEntity<MultiResponseDto<BeerDto.SearchResponse>> getCategoryBeer(

		@RequestParam(name = "category") String queryParam, @RequestParam(name = "page", defaultValue = "1") int page) {

		Page<Beer> beerPage = beerService.findCategoryBeers(queryParam, page);

		PageImpl<BeerDto.SearchResponse> responsePage = beerMapper.beersPageToSearchResponse(beerPage);

		return ResponseEntity.ok(new MultiResponseDto<>(responsePage.getContent(), beerPage));
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

	@GetMapping("/users/mypage/wishlist")
	public ResponseEntity<MultiResponseDto<BeerDto.WishlistResponse>> getMyPageBeer(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {

		Page<Beer> beerPage = beerService.findWishlistBeers(page);
		List<Rating> ratingList = beerService.findMyRatingWithWishlist();
		PageImpl<BeerDto.WishlistResponse> responses = beerMapper.beersToWishlistResponse(beerPage, ratingList);

		return ResponseEntity.ok(new MultiResponseDto<>(responses.getContent(), beerPage));
	}
}
