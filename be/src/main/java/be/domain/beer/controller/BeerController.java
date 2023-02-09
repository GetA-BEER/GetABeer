package be.domain.beer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import be.domain.beer.mapper.BeerMapper;
import be.domain.beer.service.BeerService;
import be.domain.beertag.entity.BeerTag;
import be.domain.beerwishlist.service.BeerWishlistServiceImpl;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping({"/api/beers", "/api"})
@RequiredArgsConstructor
public class BeerController {
	private final BeerMapper beerMapper;
	private final BeerService beerService;
	private final BeerWishlistServiceImpl beerWishlistServiceImpl;

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
		Boolean isWishlist = beerWishlistServiceImpl.getIsWishlist(beer);
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

	@GetMapping("/weekly")
	public ResponseEntity<List<BeerDto.WeeklyBestResponse>> getWeeklyBeer() {
		return null;
	}

	@GetMapping("/similar")
	public ResponseEntity<List<BeerDto.SimilarResponse>> getSimilarBeer(Beer beer) {

		List<Beer> beerList = beerService.findSimilarBeers(beer);
		List<BeerDto.SimilarResponse> responses = beerMapper.beersToSimilarBeerResponse(beerList);

		return ResponseEntity.ok(responses);
	}

	@GetMapping("/users/mypage/beers")
	public ResponseEntity<PageImpl<BeerDto.MyPageResponse>> getMyPageBeer(
		@RequestParam(name = "page", defaultValue = "1") Integer page,
		@PageableDefault(size = 10, sort = "username", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<Beer> beerPage = beerService.findMyPageBeers(page);
		PageImpl<BeerDto.MyPageResponse> responses = beerMapper.beersToMyPageResponse(beerPage);

		return ResponseEntity.ok(responses);
	}
}
