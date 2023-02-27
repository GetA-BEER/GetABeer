package be.domain.beer.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import be.domain.beer.entity.BeerDetailsBasic;
import be.domain.beer.entity.BeerDetailsBestRating;
import be.domain.beer.entity.BeerDetailsCounts;
import be.domain.beer.entity.BeerDetailsStars;
import be.domain.beer.entity.BeerDetailsTopTags;
import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beercategory.entity.BeerCategoryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BeerDto {

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Post {

		@NotBlank
		private String korName;
		@NotBlank
		private String engName;
		@NotBlank
		private String country;
		@NotNull
		private List<BeerCategoryDto.Response> beerCategories;
		@NotBlank
		private String thumbnail;
		@NotNull
		private Double abv;
		private Integer ibu;

	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Patch {

		private String korName;
		private String engName;
		private String country;
		private List<BeerCategoryDto.Response> beerCategories;
		private String thumbnail;
		private Double abv;
		private Integer ibu;

	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SearchResponse {

		private Long beerId;
		private String korName;
		private String country;
		private List<String> category;
		private Double abv;
		private Integer ibu;
		private BeerDetailsTopTags beerDetailsTopTags;
		private Double totalAverageStar;
		private Integer totalStarcount;
		private String thumbnail;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class MonthlyBestResponse {

		private Long beerId;
		private String korName;
		// private List<String> beerDetailsTopTags;
		private BeerDetailsTopTags beerDetailsTopTags;
		private Double totalAverageStars;
		private Integer totalStarCount;
		private String thumbnail;
		private BeerDetailsBestRating bestRating;

	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class WeeklyBestResponse {

		private Long beerId;
		private String korName;
		private String thumbnail;
		private List<BeerCategoryDto.Response> beerCategories;
		private String country;
		private Double abv;
		private Integer ibu;
		private Double averageStar;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RecommendResponse {

		private Long beerId;
		private String korName;
		private String thumbnail;
		private List<BeerCategoryDto.Response> beerCategories;
		private String country;
		private Double abv;
		private Integer ibu;
		private Double averageStar;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class WishlistResponse {

		private Long beerId;
		private String korName;
		private List<BeerCategoryDto.BeerResponse> beerCategories;
		private String thumbnail;
		private String country;
		private Double abv;
		private Integer ibu;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SimilarResponse {

		private Long beerId;
		private String korName;
		private String country;
		private List<BeerCategoryDto.BeerResponse> beerCategories;
		private Double averageStar;
		private Integer starCount;
		private String thumbnail;
		private Double abv;
		private Integer ibu;

	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class DetailsResponse {

		private Long beerId;
		private Boolean isWishlist;
		private BeerDetailsBasic beerDetailsBasic;
		private List<BeerCategoryType> beerCategoryTypes;
		private List<String> beerDetailsTopTags;
		// private BeerDetailsTopTags beerDetailsTopTags;
		private BeerDetailsStars beerDetailsStars;
		private BeerDetailsCounts beerDetailsCounts;
		private List<SimilarResponse> similarBeers;

	}
}
