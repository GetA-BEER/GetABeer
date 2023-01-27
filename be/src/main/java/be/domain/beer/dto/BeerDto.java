package be.domain.beer.dto;

import be.domain.beer.entity.BeerDetailsBasic;
import be.domain.beer.entity.BeerDetailsCounts;
import be.domain.beer.entity.BeerDetailsRatings;
import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beertag.entity.BeerTagType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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
        private Long thumbnail;
        @NotBlank
        private Double abv;
        @NotBlank
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
        private Long thumbnail;
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
        private List<BeerCategoryDto.Response> beerCategories;
        private Double averageRating;
        private Integer starCount;
        private Long thumbnail;
        private Double abv;
        private Integer ibu;

//        private Comment bestComment;
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
        private Double averageRating;
        private Integer starCount;
        private Long thumbnail;
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
        private Double myRating;

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
        private Long thumbnail;
        private Double abv;
        private Integer ibu;

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailsResponse {

        private BeerDetailsBasic beerDetailsBasic;
        private BeerDetailsRatings beerDetailsRatings;
        private BeerDetailsCounts beerDetailsCounts;
        private Boolean isWishListed;
        List<SimilarResponse> similarBeers;
        List<BeerCategoryType> beerCategories;
        List<BeerTagType> beerTags;

    }
}
