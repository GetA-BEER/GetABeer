package be.domain.beer.controller;

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.mapper.BeerMapper;
import be.domain.beer.service.BeerService;
import be.global.dto.SingleResponseDto;
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
@RequestMapping({"/beers", ""})
@RequiredArgsConstructor
public class BeerController {
    private final BeerMapper beerMapper;
    private final BeerService beerService;

    @PostMapping("/add")
    public ResponseEntity<BeerDto.DetailsResponse> postBeer(@Valid @RequestBody BeerDto.Post postBeer) {

        Beer beer = beerMapper.beerPostToBeer(postBeer);
        Beer createdBeer = beerService.createBeer(beer);
        BeerDto.DetailsResponse response = beerMapper.beerToBeerDetailsResponse(createdBeer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{beer_id}/edit")
    public ResponseEntity<BeerDto.DetailsResponse> patchBeer(@PathVariable("beer_id") Long beerId,
                                                             @Valid @RequestBody BeerDto.Patch patchBeer) {

        Beer beer = beerMapper.beerPatchToBeer(patchBeer);
        Beer updatedBeer = beerService.updateBeer(beer, beerId);
        BeerDto.DetailsResponse response = beerMapper.beerToBeerDetailsResponse(updatedBeer);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{beer_id}")
    public ResponseEntity<BeerDto.DetailsResponse> getBeer(@PathVariable("beer_id") Long beerId,
                                                           @Valid @RequestBody BeerDto.Patch patchBeer) {

        Beer beer = beerService.findBeer(beerId);
        BeerDto.DetailsResponse response = beerMapper.beerToBeerDetailsResponse(beer);

        return ResponseEntity.ok().body(response);
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
        List<BeerDto.MonthlyBestResponse> responses = beerMapper.beersToMonthlyBeerResponse(monthlyBeerList);

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<BeerDto.WeeklyBestResponse>> getWeeklyBeer() {
        return null;
    }

    @GetMapping("/similar")
    public ResponseEntity<List<BeerDto.SimilarResponse>> getSimilarBeer(Beer beer) {

        List<Beer> beerList = beerService.findSimilarBeers(beer);
        List<BeerDto.SimilarResponse> responses = beerMapper.beersToSimilarBeerResponse(beerList);

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/users/mypage/beers")
    public ResponseEntity<PageImpl<BeerDto.MyPageResponse>> getMyPageBeer(@RequestParam(name = "page", defaultValue = "1") Integer page) {

        Page<Beer> beerPage = beerService.findMyPageBeers(page);
        PageImpl<BeerDto.MyPageResponse> responses = beerMapper.beersToMyPageResponse(beerPage);

        return ResponseEntity.ok().body(responses);
    }

}
