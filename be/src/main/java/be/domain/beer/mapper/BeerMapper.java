package be.domain.beer.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.entity.BeerBeerCategory;
import be.domain.beer.entity.BeerDetailsBasic;
import be.domain.beer.entity.BeerDetailsCounts;
import be.domain.beer.entity.BeerDetailsStars;
import be.domain.beer.entity.MonthlyBeer;
import be.domain.beer.entity.WeeklyBeer;
import be.domain.beer.service.BeerService;
import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beercategory.entity.BeerCategory;
import be.domain.beertag.entity.BeerTag;
import be.domain.beerwishlist.entity.BeerWishlist;

@Mapper(componentModel = "spring")
public interface BeerMapper {

	default Beer beerPostToBeer(BeerDto.Post postBeer) {

		BeerDetailsBasic beerDetailsBasic = postBeerToBeerDetailsBasic(postBeer);
		List<BeerBeerCategory> beerBeerCategories = getBeerBeerCategoriesFromResponseDto(postBeer.getBeerCategories());

		return Beer.builder()
			.beerDetailsBasic(beerDetailsBasic)
			.beerBeerCategories(beerBeerCategories)
			.build();
	}

	default Beer beerPatchToBeer(BeerDto.Patch patchBeer) {

		BeerDetailsBasic beerDetailsBasic = patchBeerToBeerDetailsBasic(patchBeer);
		List<BeerBeerCategory> beerBeerCategories = getBeerBeerCategoriesFromResponseDto(patchBeer.getBeerCategories());

		return Beer.builder()
			.beerDetailsBasic(beerDetailsBasic)
			.beerBeerCategories(beerBeerCategories)
			.build();
	}

	default BeerDto.DetailsResponse beerToPostDetailsResponse(Beer beer) {

		BeerDto.DetailsResponse.DetailsResponseBuilder detailsResponse = BeerDto.DetailsResponse.builder();

		detailsResponse.beerId(beer.getId());
		detailsResponse.beerDetailsBasic(beer.getBeerDetailsBasic());
		detailsResponse.beerDetailsStars(beer.getBeerDetailsStars());
		detailsResponse.beerDetailsCounts(beer.getBeerDetailsCounts());
		detailsResponse.beerCategoryTypes(beer.getBeerBeerCategories().stream()
			.map(beerBeerCategory -> beerBeerCategory.getBeerCategory()
				.getBeerCategoryType())
			.collect(Collectors.toList()));
		//        detailsResponse.beerTags(beersToSimilarBeerResponse(beer.getSimilarBeers()));

		return detailsResponse.build();
	}

	default BeerDto.DetailsResponse beerToPatchDetailsResponse(Beer beer) {

		BeerDto.DetailsResponse.DetailsResponseBuilder detailsResponse = BeerDto.DetailsResponse.builder();

		detailsResponse.beerId(beer.getId());
		detailsResponse.beerDetailsBasic(beer.getBeerDetailsBasic());
		detailsResponse.beerDetailsStars(beer.getBeerDetailsStars());
		detailsResponse.beerDetailsCounts(beer.getBeerDetailsCounts());
		detailsResponse.beerDetailsTopTags(beer.getBeerDetailsTopTags().createList());
		// detailsResponse.similarBeers(beersToSimilarBeerResponse(beer.getSimilarBeers()));
		detailsResponse.beerCategoryTypes(beer.getBeerBeerCategories().stream()
			.map(beerBeerCategory -> beerBeerCategory.getBeerCategory()
				.getBeerCategoryType())
			.collect(Collectors.toList()));
		//        detailsResponse.beerTags(beersToSimilarBeerResponse(beer.getSimilarBeers()));

		return detailsResponse.build();
	}

	default BeerDto.DetailsResponse beerToDetailsResponse(Beer beer, List<BeerTag> beerTags,
		BeerWishlist beerWishlist) {

		BeerDto.DetailsResponse.DetailsResponseBuilder detailsResponse = BeerDto.DetailsResponse.builder();

		detailsResponse.beerId(beer.getId());
		detailsResponse.beerDetailsBasic(beer.getBeerDetailsBasic());
		detailsResponse.beerDetailsStars(beer.getBeerDetailsStars());
		detailsResponse.beerDetailsCounts(beer.getBeerDetailsCounts());

		if (beerTags != null && beerTags.size() > 3) {
			List<String> tempList = new ArrayList<>();
			for (int i = 0; i < beerTags.size(); i++) {
				tempList.add(beerTags.get(i).getBeerTagType().toString());
				detailsResponse.beerDetailsTopTags(tempList);
			}
		}
		detailsResponse.isWishlist(beerWishlist != null && beerWishlist.getWished());
		// detailsResponse.similarBeers(beersToSimilarBeerResponse(beer.getSimilarBeers()));
		detailsResponse.beerCategoryTypes(beer.getBeerBeerCategories().stream()
			.map(beerBeerCategory -> beerBeerCategory.getBeerCategory()
				.getBeerCategoryType())
			.collect(Collectors.toList()));
		//        detailsResponse.beerTags(beersToSimilarBeerResponse(beer.getSimilarBeers()));

		return detailsResponse.build();
	}

	default List<BeerDto.MonthlyBestResponse> beersToMonthlyBestBeerResponse(List<MonthlyBeer> beerList) {

		return beerList.stream()
			.map(monthlyBeer -> {

				BeerDto.MonthlyBestResponse.MonthlyBestResponseBuilder monthlyBestResponse = BeerDto.MonthlyBestResponse.builder();

				monthlyBestResponse.monthlyBeerId(monthlyBeer.getId());
				monthlyBestResponse.beerId(monthlyBeer.getBeerId());
				monthlyBestResponse.korName(monthlyBeer.getKorName());
				monthlyBestResponse.thumbnail(monthlyBeer.getThumbnail());
				monthlyBestResponse.totalAverageStars(monthlyBeer.getAverageStar());
				monthlyBestResponse.totalStarCount(monthlyBeer.getRatingCount());
				monthlyBestResponse.beerDetailsTopTags(monthlyBeer.getBeerDetailsTopTags());

				monthlyBestResponse.bestRating(monthlyBeer.getBeerDetailsBestRating());

				return monthlyBestResponse.build();

			}).collect(Collectors.toList());
	}

	default List<BeerDto.WeeklyBestResponse> beersToWeeklyBestBeerResponse(List<WeeklyBeer> beerList) {

		return beerList.stream()
			.map(weeklyBeer -> {

				BeerDto.WeeklyBestResponse.WeeklyBestResponseBuilder weeklyBestResponseBuilder = BeerDto.WeeklyBestResponse.builder();

				weeklyBestResponseBuilder.weeklyBeerId(weeklyBeer.getId());
				weeklyBestResponseBuilder.beerId(weeklyBeer.getBeerId());
				weeklyBestResponseBuilder.korName(weeklyBeer.getKorName());
				weeklyBestResponseBuilder.thumbnail(weeklyBeer.getThumbnail());

				weeklyBestResponseBuilder.beerCategories(weeklyBeer.getWeeklyBeerCategory().createList());

				weeklyBestResponseBuilder.country(weeklyBeer.getCountry());
				weeklyBestResponseBuilder.abv(weeklyBeer.getAbv());
				weeklyBestResponseBuilder.ibu(weeklyBeer.getIbu());

				weeklyBestResponseBuilder.averageStar(weeklyBeer.getAverageStar());

				return weeklyBestResponseBuilder.build();

			}).collect(Collectors.toList());
	}

	default List<BeerDto.RecommendResponse> beersToRecommendResponse(List<Beer> beerList) {

		return beerList.stream()
			.map(beer -> {
				return BeerDto.RecommendResponse.builder()
					.beerId(beer.getId())
					.korName(beer.getBeerDetailsBasic().getKorName())
					.thumbnail(beer.getBeerDetailsBasic().getThumbnail())
					.beerCategories(beer.getBeerBeerCategories().stream()
						.map(category -> BeerCategoryDto.Response.builder()
							.beerCategoryId(category.getBeerCategory().getId())
							.beerCategoryType(category.getBeerCategory().getBeerCategoryType())
							.build())
						.collect(Collectors.toList()))
					.country(beer.getBeerDetailsBasic().getCountry())
					.abv(beer.getBeerDetailsBasic().getAbv())
					.ibu(beer.getBeerDetailsBasic().getIbu())
					.averageStar(beer.getBeerDetailsStars().getTotalAverageStars())
					.build();
			})
			.collect(Collectors.toList());
	}

	default List<BeerDto.SimilarResponse> beersToSimilarBeerResponse(List<Beer> beerList) {

		return beerList.stream()
			.map(beer -> {
				return BeerDto.SimilarResponse.builder()
					.beerId(beer.getId())
					.korName(beer.getBeerDetailsBasic().getKorName())
					.country(beer.getBeerDetailsBasic().getCountry())
					.beerCategories(beer.getBeerBeerCategories().stream()
						.map(category -> BeerCategoryDto.BeerResponse.builder()
							.beerCategoryType(category.getBeerCategory().getBeerCategoryType())
							.build())
						.collect(Collectors.toList()))
					.averageStar(beer.getBeerDetailsStars().getTotalAverageStars())
					.starCount(beer.getBeerDetailsCounts().getRatingCount())
					.thumbnail(beer.getBeerDetailsBasic().getThumbnail())
					.abv(beer.getBeerDetailsBasic().getAbv())
					.ibu(beer.getBeerDetailsBasic().getIbu())
					.build();
			})
			.collect(Collectors.toList());
	}

	default PageImpl<BeerDto.SearchResponse> beersPageToSearchResponse(Page<Beer> beerPage) {

		return new PageImpl<>(beerPage.stream()
			.map(beer -> {

				BeerDto.SearchResponse.SearchResponseBuilder searchResponseBuilder = BeerDto.SearchResponse.builder();

				searchResponseBuilder.beerId(beer.getId());
				searchResponseBuilder.korName(beer.getBeerDetailsBasic().getKorName());
				searchResponseBuilder.thumbnail(beer.getBeerDetailsBasic().getThumbnail());
				searchResponseBuilder.country(beer.getBeerDetailsBasic().getCountry());
				searchResponseBuilder.category(beer.getBeerBeerCategories().stream()
					.map(beerBeerCategory -> beerBeerCategory.getBeerCategory().getBeerCategoryType().toString())
					.collect(Collectors.toList()));
				searchResponseBuilder.abv(beer.getBeerDetailsBasic().getAbv());
				searchResponseBuilder.ibu(beer.getBeerDetailsBasic().getIbu());
				searchResponseBuilder.totalAverageStar(beer.getBeerDetailsStars().getTotalAverageStars());
				searchResponseBuilder.totalStarcount(beer.getBeerDetailsCounts().getRatingCount());
				// searchResponseBuilder.beerDetailsTopTags(beer.getBeerDetailsTopTags());

				if (beer.getBeerDetailsTopTags() != null) {
					searchResponseBuilder.beerDetailsTopTags(beer.getBeerDetailsTopTags().createList());
				}

				return searchResponseBuilder.build();
			})
			.collect(Collectors.toList()));
	}

	default List<BeerDto.SearchResponse> beersListToSearchResponse(List<Beer> beerList) {

		return beerList.stream()
			.filter(Objects::nonNull)
			.map(beer -> {

				BeerDto.SearchResponse.SearchResponseBuilder searchResponseBuilder = BeerDto.SearchResponse.builder();

				searchResponseBuilder.beerId(beer.getId());
				searchResponseBuilder.korName(beer.getBeerDetailsBasic().getKorName());
				searchResponseBuilder.thumbnail(beer.getBeerDetailsBasic().getThumbnail());
				searchResponseBuilder.country(beer.getBeerDetailsBasic().getCountry());
				searchResponseBuilder.category(beer.getBeerBeerCategories().stream()
					.map(beerBeerCategory -> beerBeerCategory.getBeerCategory().getBeerCategoryType().toString())
					.collect(Collectors.toList()));
				searchResponseBuilder.abv(beer.getBeerDetailsBasic().getAbv());
				searchResponseBuilder.ibu(beer.getBeerDetailsBasic().getIbu());
				searchResponseBuilder.totalAverageStar(beer.getBeerDetailsStars().getTotalAverageStars());
				searchResponseBuilder.totalStarcount(beer.getBeerDetailsCounts().getRatingCount());
				// searchResponseBuilder.beerDetailsTopTags(beer.getBeerDetailsTopTags());

				if (beer.getBeerDetailsTopTags() != null) {
					searchResponseBuilder.beerDetailsTopTags(beer.getBeerDetailsTopTags().createList());
				}

				// if (beerTags != null && beerTags.size() > 3) {
				// 	List<String> tempList = new ArrayList<>();
				// 	for (int i = 0; i < beerTags.size(); i++) {
				// 		tempList.add(beerTags.get(i).getBeerTagType().toString());
				// 		detailsResponse.beerDetailsTopTags(tempList);
				// 	}
				// }

				return searchResponseBuilder.build();
			})
			.collect(Collectors.toList());
	}

	private static List<BeerBeerCategory> getBeerBeerCategoriesFromResponseDto(
		List<BeerCategoryDto.Response> responseList) {
		return responseList.stream()
			.map(response ->
				BeerBeerCategory.builder()
					.beerCategory(BeerCategory.builder()
						.id(response.getBeerCategoryId())
						.beerCategoryType(response.getBeerCategoryType())
						.build())
					.build())
			.collect(Collectors.toList());
	}

	private static BeerDetailsBasic postBeerToBeerDetailsBasic(BeerDto.Post postBeer) {

		BeerDetailsBasic.BeerDetailsBasicBuilder beerDetailsBasic = BeerDetailsBasic.builder();

		beerDetailsBasic.korName(postBeer.getKorName());
		beerDetailsBasic.engName(postBeer.getEngName());
		beerDetailsBasic.country(postBeer.getCountry());
		beerDetailsBasic.thumbnail(postBeer.getThumbnail());
		beerDetailsBasic.abv(postBeer.getAbv());
		beerDetailsBasic.ibu(postBeer.getIbu());

		return beerDetailsBasic.build();
	}

	private static BeerDetailsBasic patchBeerToBeerDetailsBasic(BeerDto.Patch patchBeer) {

		BeerDetailsBasic.BeerDetailsBasicBuilder beerDetailsBasic = BeerDetailsBasic.builder();

		beerDetailsBasic.korName(patchBeer.getKorName());
		beerDetailsBasic.engName(patchBeer.getEngName());
		beerDetailsBasic.country(patchBeer.getCountry());
		beerDetailsBasic.thumbnail(patchBeer.getThumbnail());
		beerDetailsBasic.abv(patchBeer.getAbv());
		beerDetailsBasic.ibu(patchBeer.getIbu());

		return beerDetailsBasic.build();
	}

	private static BeerDetailsStars beerToBeerDetailsRatings(Beer beer) {

		BeerDetailsStars.BeerDetailsStarsBuilder beerDetailsStars = BeerDetailsStars.builder();

		beerDetailsStars.totalAverageStars(beer.getBeerDetailsStars().getTotalAverageStars());
		beerDetailsStars.femaleAverageStars(beer.getBeerDetailsStars().getFemaleAverageStars());
		beerDetailsStars.maleAverageStars(beer.getBeerDetailsStars().getMaleAverageStars());

		return beerDetailsStars.build();
	}

	private static BeerDetailsCounts beerToBeerDetailsCounts(Beer beer) {

		BeerDetailsCounts.BeerDetailsCountsBuilder beerDetailsCounts = BeerDetailsCounts.builder();

		// beerDetailsCounts.totalStarCount(beer.getBeerDetailsCounts().getTotalStarCount());
		beerDetailsCounts.femaleStarCount(beer.getBeerDetailsCounts().getFemaleStarCount());
		beerDetailsCounts.maleStarCount(beer.getBeerDetailsCounts().getMaleStarCount());
		beerDetailsCounts.ratingCount(beer.getBeerDetailsCounts().getRatingCount());
		beerDetailsCounts.pairingCount(beer.getBeerDetailsCounts().getPairingCount());

		return beerDetailsCounts.build();
	}
}
