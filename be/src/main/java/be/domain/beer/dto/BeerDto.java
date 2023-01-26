package be.domain.beer.dto;

import be.domain.beerCategory.dto.BeerCategoryDto;
import be.domain.beerTag.dto.BeerTagDto;
import lombok.*;

import java.util.List;

public class BeerDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Post {

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
    public static class Patch {

        private Long beerId;
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
        private Integer ratingCount;
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
        private Integer ratingCount;
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
        private Integer ratingCount;
        private Long thumbnail;
        private Double abv;
        private Integer ibu;

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailsResponse {

        private Long id;
        private String korName;
        private String engName;
        private String country;
        private Long thumbnail;
        private Double abv;
        private Integer ibu;
        private Double averageRating;
        private Integer ratingCount;
        private Integer commentCount;
        private Integer pairingCount;
        private Integer wishlistCount;
        private Boolean isWishListed;
        List<SimilarResponse> similarBeers;
        List<BeerCategoryDto.Response> beerCategories;
        List<BeerTagDto.Response> beerTags;

    }
}
