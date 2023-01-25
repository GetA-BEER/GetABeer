package be.domain.beer.mapper;

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.entity.MonthlyBeer;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    Beer beerPostToBeer(BeerDto.Post postBeer);
    Beer beerPatchToBeer(BeerDto.Patch patchBeer);
    BeerDto.DetailsResponse beerToBeerDetailsResponse(Beer beer);
    List<BeerDto.MonthlyBestResponse> beersToMonthlyBeerResponse(List<MonthlyBeer> beerList);
    List<BeerDto.SimilarResponse> beersToSimilarBeerResponse(List<Beer> beerList);
    default PageImpl<BeerDto.MyPageResponse> beersToMyPageResponse(Page<Beer> beerPage) {
     return null;
    }
}
