package be.domain.beer.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import be.domain.beer.entity.BeerDetailsBasic;
import be.domain.beer.entity.BeerDetailsCounts;
import be.domain.beer.entity.BeerDetailsStars;
import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beertag.dto.BeerTagDto;
import be.domain.beertag.entity.BeerTagType;
import be.domain.rating.entity.Rating;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

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
		@NotNull
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
		private List<String> topTags;
		private Double averageStar;
		private Integer totalStarCount;
		private String thumbnail;
		private Rating bestRating;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class MonthlyBestResponse {

		private Long beerId;
		private String korName;
		private String country;
		private List<BeerCategoryDto.Response> beerCategories;
		private List<BeerTagDto.Response> beerTags;
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
	public static class WeeklyBestResponse {

		private Long beerId;

	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class MyPageResponse {

		private Long beerId;
		private String korName;
		private Double myStar;

	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SimilarResponse {

		private Long beerId;
		private String korName;
		private String country;
		private List<BeerCategoryDto.Response> beerCategories;
		private Double averageRating;
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
		private BeerDetailsBasic beerDetailsBasic;
		private BeerDetailsCounts beerDetailsCounts;
		private BeerDetailsStars beerDetailsStars;
		private Boolean isWishListed;
		List<BeerCategoryType> beerCategoryTypes;
		List<BeerTagType> beerTags;
		List<SimilarResponse> similarBeers;

	}
}
