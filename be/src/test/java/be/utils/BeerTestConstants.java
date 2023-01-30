package be.utils;

import be.domain.beer.dto.BeerDto;
import be.domain.beer.dto.BeerDto.MyPageResponse;
import be.domain.beer.entity.BeerDetailsBasic;
import be.domain.beer.entity.BeerDetailsCounts;
import be.domain.beer.entity.BeerDetailsStars;
import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beertag.dto.BeerTagDto;
import be.domain.beertag.entity.BeerTagType;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class BeerTestConstants {

    public static final BeerCategoryDto.Response BEER_CATEGORY_WITH_ID_AND_TYPE =
            BeerCategoryDto.Response.builder()
                    .beerCategoryId(1L)
                    .beerCategoryType(BeerCategoryType.ALE)
                    .build();

    public static final BeerTagDto.Response BEER_TAG_WITH_ID_AND_TYPE =
            BeerTagDto.Response.builder()
                    .beerTagId(1L)
                    .beerTagType(BeerTagType.STRAW)
                    .build();

    @Getter
    public static final BeerDto.Post BEER_POST_DTO =
            BeerDto.Post.builder()
                    .korName("한글 이름")
                    .engName("English Name")
                    .country("생산 국가")
                    .beerCategories(List.of(BEER_CATEGORY_WITH_ID_AND_TYPE))
                    .thumbnail("썸네일 이미지 주소")
                    .abv(4.5)
                    .ibu(28)
                    .build();

    @Getter
    public static final BeerDto.Patch BEER_PATCH_DTO =
            BeerDto.Patch.builder()
                    .korName("수정된 한글 이름")
                    .engName("Updated English Name")
                    .country("수정된 생산 국가")
                    .beerCategories(List.of(BEER_CATEGORY_WITH_ID_AND_TYPE))
                    .thumbnail("수정된 썸네일 이미지 주소")
                    .abv(4.0)
                    .ibu(22)
                    .build();

    public static final BeerDetailsBasic BEER_DETAILS_BASIC =
            BeerDetailsBasic.builder()
                    .korName("한글 이름")
                    .engName("English Name")
                    .country("생산 국가")
                    .thumbnail("썸네일 이미지 주소")
                    .abv(4.5)
                    .ibu(28)
                    .build();

    public static final BeerDetailsBasic BEER_DETAILS_UPDATED =
            BeerDetailsBasic.builder()
                    .korName("수정된 한글 이름")
                    .engName("Updated English Name")
                    .country("수정된 생산 국가")
                    .thumbnail("수정된 썸네일 이미지 주소")
                    .abv(4.0)
                    .ibu(22)
                    .build();

    public static final BeerDetailsCounts BEER_DETAILS_COUNTS_EMPTY =
            BeerDetailsCounts.builder()
                    .totalStarCount(0)
                    .femaleStarCount(0)
                    .maleStarCount(0)
                    .commentCount(0)
                    .pairingCount(0)
                    .build();

    public static final BeerDetailsCounts BEER_DETAILS_COUNTS_WITH_VALUES =
            BeerDetailsCounts.builder()
                    .totalStarCount(25)
                    .femaleStarCount(12)
                    .maleStarCount(13)
                    .commentCount(25)
                    .pairingCount(17)
                    .build();

    public static final BeerDetailsStars BEER_DETAILS_STARS_EMPTY =
            BeerDetailsStars.builder()
                    .totalAverageStars(0.0)
                    .femaleAverageStars(0.0)
                    .maleAverageStars(0.0)
                    .build();

    public static final BeerDetailsStars BEER_DETAILS_STARS_WITH_VALUES =
            BeerDetailsStars.builder()
                    .totalAverageStars(4.3)
                    .femaleAverageStars(4.5)
                    .maleAverageStars(3.7)
                    .build();

    public static final BeerDto.MonthlyBestResponse GET_MONTHLY_BEER_RESPONSE =
            BeerDto.MonthlyBestResponse.builder()
                    .beerId(1L)
                    .korName("한글 이름")
                    .country("생산 국가")
                    .beerCategories(List.of(BEER_CATEGORY_WITH_ID_AND_TYPE))
                    .beerTags(List.of(BEER_TAG_WITH_ID_AND_TYPE))
                    .averageStar(4.0)
                    .starCount(20)
                    .thumbnail("썸네일 이미지 경로")
                    .abv(4.0)
                    .ibu(15)
                    .build();

    public static final List<BeerDto.MonthlyBestResponse> GET_MONTHLY_BEER_RESPONSE_LIST =
            List.of(GET_MONTHLY_BEER_RESPONSE,
                    GET_MONTHLY_BEER_RESPONSE,
                    GET_MONTHLY_BEER_RESPONSE,
                    GET_MONTHLY_BEER_RESPONSE,
                    GET_MONTHLY_BEER_RESPONSE);

    public static final BeerDto.MyPageResponse GET_MY_PAGE_BEER_RESPONSE =
            BeerDto.MyPageResponse.builder()
                    .beerId(1L)
                    .korName("한글 이름")
                    .myStar(4.5)
                    .build();

    public static final PageImpl<MyPageResponse> GET_MY_PAGE_RESPONSE_PAGE_IMPL =
            new PageImpl<>(
                    List.of(GET_MY_PAGE_BEER_RESPONSE,
                            GET_MY_PAGE_BEER_RESPONSE,
                            GET_MY_PAGE_BEER_RESPONSE,
                            GET_MY_PAGE_BEER_RESPONSE,
                            GET_MY_PAGE_BEER_RESPONSE
                    ));

}
