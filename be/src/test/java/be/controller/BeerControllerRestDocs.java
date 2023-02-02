package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.BeerTestConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.mapper.BeerMapper;
import be.domain.beer.service.BeerService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class BeerControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private BeerMapper beerMapper;
	@MockBean
	private BeerService beerService;

	@BeforeEach
	private void init() {
	}

	// @Test
	// void postBeerTest() throws Exception {
	//
	// 	String content = gson.toJson(BEER_POST_DTO);
	//
	// 	BeerDto.DetailsResponse response =
	// 		BeerDto.DetailsResponse.builder()
	// 			.beerId(1L)
	// 			.beerDetailsBasic(BEER_DETAILS_BASIC)
	// 			.beerDetailsCounts(BEER_DETAILS_COUNTS_EMPTY)
	// 			.beerDetailsStars(BEER_DETAILS_STARS_EMPTY)
	// 			.isWishListed(false)
	// 			.beerCategoryTypes(List.of(BEER_CATEGORY_WITH_ID_AND_TYPE.getBeerCategoryType()))
	// 			.beerTags(new ArrayList<>())
	// 			.similarBeers(new ArrayList<>())
	// 			.build();
	//
	// 	given(beerMapper.beerPostToBeer(Mockito.any(BeerDto.Post.class))).willReturn(Beer.builder().build());
	// 	given(beerService.createBeer(Mockito.any(Beer.class))).willReturn(Beer.builder().build());
	// 	given(beerMapper.beerToPostDetailsResponse(Mockito.any(Beer.class))).willReturn(response);
	//
	// 	ResultActions actions =
	// 		mockMvc.perform(
	// 			RestDocumentationRequestBuilders.post("/beers/add")
	// 				.accept(MediaType.APPLICATION_JSON)
	// 				.contentType(MediaType.APPLICATION_JSON)
	// 				.content(content)
	// 		);
	//
	// 	actions
	// 		.andExpect(status().isCreated())
	// 		.andExpect(jsonPath(".korName").value(response.getBeerDetailsBasic().getKorName()))
	// 		.andExpect(jsonPath(".engName").value(response.getBeerDetailsBasic().getEngName()))
	// 		.andDo(document(
	// 			"Post_Beer",
	// 			getDocumentRequest(),
	// 			getDocumentResponse(),
	// 			requestFields(
	// 				List.of(
	// 					fieldWithPath("korName").type(JsonFieldType.STRING).description("한글 이름"),
	// 					fieldWithPath("engName").type(JsonFieldType.STRING).description("영문 이름"),
	// 					fieldWithPath("country").type(JsonFieldType.STRING).description("생산 국가"),
	// 					fieldWithPath("beerCategories[]").type(JsonFieldType.ARRAY).description("맥주 카테고리"),
	// 					fieldWithPath("beerCategories[].beerCategoryId").type(JsonFieldType.NUMBER)
	// 						.description("맥주 카테고리 아이디"),
	// 					fieldWithPath("beerCategories[].beerCategoryType").type(JsonFieldType.STRING)
	// 						.description("맥주 카테고리 타입"),
	// 					fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("썸네일 이미지 주소"),
	// 					fieldWithPath("abv").type(JsonFieldType.NUMBER).description("알코올 도수"),
	// 					fieldWithPath("ibu").type(JsonFieldType.NUMBER).description("IBU")
	// 				)
	// 			),
	// 			responseFields(
	// 				List.of(
	// 					fieldWithPath(".beerId").type(JsonFieldType.NUMBER).description("맥주 번호"),
	//
	// 					fieldWithPath(".beerDetailsBasic").type(JsonFieldType.OBJECT).description("맥주 기본 정보"),
	// 					fieldWithPath(".beerDetailsBasic.korName").type(JsonFieldType.STRING).description("한글 이름"),
	// 					fieldWithPath(".beerDetailsBasic.engName").type(JsonFieldType.STRING).description("영문 이름"),
	// 					fieldWithPath(".beerDetailsBasic.country").type(JsonFieldType.STRING).description("생산 국가"),
	// 					fieldWithPath(".beerDetailsBasic.thumbnail").type(JsonFieldType.STRING)
	// 						.description("썸네일 이미지 주소"),
	// 					fieldWithPath(".beerDetailsBasic.abv").type(JsonFieldType.NUMBER).description("알코올 도수"),
	// 					fieldWithPath(".beerDetailsBasic.ibu").type(JsonFieldType.NUMBER).description("IBU"),
	//
	// 					fieldWithPath(".beerDetailsCounts").type(JsonFieldType.OBJECT).description("맥주 관련 카운팅"),
	// 					fieldWithPath(".beerDetailsCounts.totalStarCount").type(JsonFieldType.NUMBER)
	// 						.description("전체 별점 수"),
	// 					fieldWithPath(".beerDetailsCounts.femaleStarCount").type(JsonFieldType.NUMBER)
	// 						.description("여성 별점 수"),
	// 					fieldWithPath(".beerDetailsCounts.maleStarCount").type(JsonFieldType.NUMBER)
	// 						.description("남성 별점 수"),
	// 					fieldWithPath(".beerDetailsCounts.commentCount").type(JsonFieldType.NUMBER)
	// 						.description("코멘트 수"),
	// 					fieldWithPath(".beerDetailsCounts.pairingCount").type(JsonFieldType.NUMBER)
	// 						.description("페어링 수"),
	//
	// 					fieldWithPath(".beerDetailsStars").type(JsonFieldType.OBJECT).description("맥주 관련 별점"),
	// 					fieldWithPath(".beerDetailsStars.totalAverageStars").type(JsonFieldType.NUMBER)
	// 						.description("맥주 총 별점 평균"),
	// 					fieldWithPath(".beerDetailsStars.femaleAverageStars").type(JsonFieldType.NUMBER)
	// 						.description("맥주 여성 별점 평균"),
	// 					fieldWithPath(".beerDetailsStars.maleAverageStars").type(JsonFieldType.NUMBER)
	// 						.description("맥주 남성 별점 평균"),
	//
	// 					fieldWithPath(".isWishListed").type(JsonFieldType.BOOLEAN).description("위시리스트 등록 여부"),
	// 					fieldWithPath(".beerCategoryTypes[]").type(JsonFieldType.ARRAY).description("맥주 카테고리 타입 리스트"),
	// 					fieldWithPath(".beerTags").type(JsonFieldType.ARRAY).description("맥주 태그 리스트"),
	// 					fieldWithPath(".similarBeers").type(JsonFieldType.ARRAY).description("비슷한 맥주 리스트")
	// 				)
	// 			)));
	// }
	//
	// @Test
	// void patchBeerTest() throws Exception {
	//
	// 	Long beerId = 1L;
	//
	// 	String content = gson.toJson(BEER_PATCH_DTO);
	//
	// 	BeerDto.DetailsResponse response =
	// 		BeerDto.DetailsResponse.builder()
	// 			.beerId(1L)
	// 			.beerDetailsBasic(BEER_DETAILS_UPDATED)
	// 			.beerDetailsCounts(BEER_DETAILS_COUNTS_WITH_VALUES)
	// 			.beerDetailsStars(BEER_DETAILS_STARS_WITH_VALUES)
	// 			.isWishListed(false)
	// 			.beerCategoryTypes(List.of(BEER_CATEGORY_WITH_ID_AND_TYPE.getBeerCategoryType()))
	// 			.beerTags(new ArrayList<>())
	// 			.similarBeers(new ArrayList<>())
	// 			.build();
	//
	// 	given(beerMapper.beerPatchToBeer(Mockito.any(BeerDto.Patch.class))).willReturn(Beer.builder().build());
	// 	given(beerService.findTop4BeerTags(Mockito.any(Beer.class))).willReturn(new ArrayList<>());
	// 	given(beerService.updateBeer(Mockito.any(Beer.class), anyLong())).willReturn(Beer.builder().build());
	// 	given(beerMapper.beerToDetailsResponse(Mockito.any(Beer.class), anyList())).willReturn(response);
	//
	// 	ResultActions actions =
	// 		mockMvc.perform(
	// 			RestDocumentationRequestBuilders.patch("/beers/{beer_id}/edit", beerId)
	// 				.accept(MediaType.APPLICATION_JSON)
	// 				.contentType(MediaType.APPLICATION_JSON)
	// 				.content(content)
	// 		);
	//
	// 	actions
	// 		.andExpect(status().isOk())
	// 		.andExpect(jsonPath(".korName").value(response.getBeerDetailsBasic().getKorName()))
	// 		.andExpect(jsonPath(".engName").value(response.getBeerDetailsBasic().getEngName()))
	// 		.andDo(document(
	// 			"Patch_Beer",
	// 			getDocumentRequest(),
	// 			getDocumentResponse(),
	// 			pathParameters(
	// 				parameterWithName("beer_id").description("맥주 번호")
	// 			),
	// 			requestFields(
	// 				List.of(
	// 					fieldWithPath("korName").type(JsonFieldType.STRING).description("수정된 한글 이름"),
	// 					fieldWithPath("engName").type(JsonFieldType.STRING).description("수정된 영문 이름"),
	// 					fieldWithPath("country").type(JsonFieldType.STRING).description("수정된 생산 국가"),
	// 					fieldWithPath("beerCategories[]").type(JsonFieldType.ARRAY).description("수정된 맥주 카테고리"),
	// 					fieldWithPath("beerCategories[].beerCategoryId").type(JsonFieldType.NUMBER)
	// 						.description("수정된 맥주 카테고리 아이디"),
	// 					fieldWithPath("beerCategories[].beerCategoryType").type(JsonFieldType.STRING)
	// 						.description("수정된 맥주 카테고리 타입"),
	// 					fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("수정된 썸네일 이미지 주소"),
	// 					fieldWithPath("abv").type(JsonFieldType.NUMBER).description("수정된 알코올 도수"),
	// 					fieldWithPath("ibu").type(JsonFieldType.NUMBER).description("수정된 IBU")
	// 				)
	// 			),
	// 			responseFields(
	// 				List.of(
	// 					fieldWithPath(".beerId").type(JsonFieldType.NUMBER).description("맥주 번호"),
	//
	// 					fieldWithPath(".beerDetailsBasic").type(JsonFieldType.OBJECT).description("맥주 기본 정보"),
	// 					fieldWithPath(".beerDetailsBasic.korName").type(JsonFieldType.STRING).description("수정된 한글 이름"),
	// 					fieldWithPath(".beerDetailsBasic.engName").type(JsonFieldType.STRING).description("수정된 영문 이름"),
	// 					fieldWithPath(".beerDetailsBasic.country").type(JsonFieldType.STRING).description("수정된 생산 국가"),
	// 					fieldWithPath(".beerDetailsBasic.thumbnail").type(JsonFieldType.STRING)
	// 						.description("수정된 썸네일 이미지 주소"),
	// 					fieldWithPath(".beerDetailsBasic.abv").type(JsonFieldType.NUMBER).description("수정된 알코올 도수"),
	// 					fieldWithPath(".beerDetailsBasic.ibu").type(JsonFieldType.NUMBER).description("수정된 IBU"),
	//
	// 					fieldWithPath(".beerDetailsCounts").type(JsonFieldType.OBJECT).description("맥주 관련 카운팅"),
	// 					fieldWithPath(".beerDetailsCounts.totalStarCount").type(JsonFieldType.NUMBER)
	// 						.description("전체 별점 수"),
	// 					fieldWithPath(".beerDetailsCounts.femaleStarCount").type(JsonFieldType.NUMBER)
	// 						.description("여성 별점 수"),
	// 					fieldWithPath(".beerDetailsCounts.maleStarCount").type(JsonFieldType.NUMBER)
	// 						.description("남성 별점 수"),
	// 					fieldWithPath(".beerDetailsCounts.commentCount").type(JsonFieldType.NUMBER)
	// 						.description("코멘트 수"),
	// 					fieldWithPath(".beerDetailsCounts.pairingCount").type(JsonFieldType.NUMBER)
	// 						.description("페어링 수"),
	//
	// 					fieldWithPath(".beerDetailsStars").type(JsonFieldType.OBJECT).description("맥주 관련 별점"),
	// 					fieldWithPath(".beerDetailsStars.totalAverageStars").type(JsonFieldType.NUMBER)
	// 						.description("맥주 총 별점 평균"),
	// 					fieldWithPath(".beerDetailsStars.femaleAverageStars").type(JsonFieldType.NUMBER)
	// 						.description("맥주 여성 별점 평균"),
	// 					fieldWithPath(".beerDetailsStars.maleAverageStars").type(JsonFieldType.NUMBER)
	// 						.description("맥주 남성 별점 평균"),
	//
	// 					fieldWithPath(".isWishListed").type(JsonFieldType.BOOLEAN).description("위시리스트 등록 여부"),
	// 					fieldWithPath(".beerCategoryTypes[]").type(JsonFieldType.ARRAY).description("맥주 카테고리 타입 리스트"),
	// 					fieldWithPath(".beerTags").type(JsonFieldType.ARRAY).description("맥주 태그 리스트"),
	// 					fieldWithPath(".similarBeers").type(JsonFieldType.ARRAY).description("비슷한 맥주 리스트")
	// 				)
	// 			)));
	// }
	//
	// @Test
	// void getBeerTest() throws Exception {
	//
	// 	Long beerId = 1L;
	//
	// 	BeerDto.DetailsResponse response =
	// 		BeerDto.DetailsResponse.builder()
	// 			.beerId(1L)
	// 			.beerDetailsBasic(BEER_DETAILS_BASIC)
	// 			.beerDetailsCounts(BEER_DETAILS_COUNTS_WITH_VALUES)
	// 			.beerDetailsStars(BEER_DETAILS_STARS_WITH_VALUES)
	// 			.isWishListed(false)
	// 			.beerCategoryTypes(List.of(BEER_CATEGORY_WITH_ID_AND_TYPE.getBeerCategoryType()))
	// 			.beerTags(new ArrayList<>())
	// 			.similarBeers(new ArrayList<>())
	// 			.build();
	//
	// 	given(beerService.findVerifiedBeer(anyLong())).willReturn(Beer.builder().build());
	// 	given(beerService.findTop4BeerTags(Mockito.any(Beer.class))).willReturn(new ArrayList<>());
	// 	given(beerMapper.beerToDetailsResponse(Mockito.any(Beer.class), anyList())).willReturn(response);
	//
	// 	ResultActions actions =
	// 		mockMvc.perform(
	// 			RestDocumentationRequestBuilders.get("/beers/{beer_id}", beerId)
	// 				.accept(MediaType.APPLICATION_JSON)
	// 		);
	//
	// 	actions
	// 		.andExpect(status().isOk())
	// 		.andExpect(jsonPath(".korName").value(response.getBeerDetailsBasic().getKorName()))
	// 		.andExpect(jsonPath(".engName").value(response.getBeerDetailsBasic().getEngName()))
	// 		.andDo(document(
	// 			"Get_Beer",
	// 			getDocumentResponse(),
	// 			pathParameters(
	// 				parameterWithName("beer_id").description("맥주 번호")
	// 			),
	// 			responseFields(
	// 				List.of(
	// 					fieldWithPath(".beerId").type(JsonFieldType.NUMBER).description("맥주 번호"),
	//
	// 					fieldWithPath(".beerDetailsBasic").type(JsonFieldType.OBJECT).description("맥주 기본 정보"),
	// 					fieldWithPath(".beerDetailsBasic.korName").type(JsonFieldType.STRING).description("한글 이름"),
	// 					fieldWithPath(".beerDetailsBasic.engName").type(JsonFieldType.STRING).description("영문 이름"),
	// 					fieldWithPath(".beerDetailsBasic.country").type(JsonFieldType.STRING).description("생산 국가"),
	// 					fieldWithPath(".beerDetailsBasic.thumbnail").type(JsonFieldType.STRING)
	// 						.description("썸네일 이미지 주소"),
	// 					fieldWithPath(".beerDetailsBasic.abv").type(JsonFieldType.NUMBER).description("알코올 도수"),
	// 					fieldWithPath(".beerDetailsBasic.ibu").type(JsonFieldType.NUMBER).description("IBU"),
	//
	// 					fieldWithPath(".beerDetailsCounts").type(JsonFieldType.OBJECT).description("맥주 관련 카운팅"),
	// 					fieldWithPath(".beerDetailsCounts.totalStarCount").type(JsonFieldType.NUMBER)
	// 						.description("전체 별점 수"),
	// 					fieldWithPath(".beerDetailsCounts.femaleStarCount").type(JsonFieldType.NUMBER)
	// 						.description("여성 별점 수"),
	// 					fieldWithPath(".beerDetailsCounts.maleStarCount").type(JsonFieldType.NUMBER)
	// 						.description("남성 별점 수"),
	// 					fieldWithPath(".beerDetailsCounts.commentCount").type(JsonFieldType.NUMBER)
	// 						.description("코멘트 수"),
	// 					fieldWithPath(".beerDetailsCounts.pairingCount").type(JsonFieldType.NUMBER)
	// 						.description("페어링 수"),
	//
	// 					fieldWithPath(".beerDetailsStars").type(JsonFieldType.OBJECT).description("맥주 관련 별점"),
	// 					fieldWithPath(".beerDetailsStars.totalAverageStars").type(JsonFieldType.NUMBER)
	// 						.description("맥주 총 별점 평균"),
	// 					fieldWithPath(".beerDetailsStars.femaleAverageStars").type(JsonFieldType.NUMBER)
	// 						.description("맥주 여성 별점 평균"),
	// 					fieldWithPath(".beerDetailsStars.maleAverageStars").type(JsonFieldType.NUMBER)
	// 						.description("맥주 남성 별점 평균"),
	//
	// 					fieldWithPath(".isWishListed").type(JsonFieldType.BOOLEAN).description("위시리스트 등록 여부"),
	// 					fieldWithPath(".beerCategoryTypes[]").type(JsonFieldType.ARRAY).description("맥주 카테고리 타입 리스트"),
	// 					fieldWithPath(".beerTags").type(JsonFieldType.ARRAY).description("맥주 태그 리스트"),
	// 					fieldWithPath(".similarBeers").type(JsonFieldType.ARRAY).description("비슷한 맥주 리스트")
	// 				)
	// 			)));
	// }
	//
	// @Test
	// void getMonthlyBeerTest() throws Exception {
	//
	// 	given(beerService.findMonthlyBeers()).willReturn(new ArrayList<>());
	// 	given(beerMapper.beersToMonthlyBestBeerResponse(Mockito.any())).willReturn(GET_MONTHLY_BEER_RESPONSE_LIST);
	//
	// 	ResultActions actions =
	// 		mockMvc.perform(
	// 			RestDocumentationRequestBuilders.get("/beers/monthly")
	// 				.accept(MediaType.APPLICATION_JSON)
	// 		);
	//
	// 	actions
	// 		.andExpect(status().isOk())
	// 		.andDo(document(
	// 			"Get_Monthly_Beers",
	// 			getDocumentResponse(),
	// 			responseFields(
	// 				List.of(
	// 					fieldWithPath("[].beerId").type(JsonFieldType.NUMBER).description("맥주 번호"),
	// 					fieldWithPath("[].korName").type(JsonFieldType.STRING).description("한글 이름"),
	// 					fieldWithPath("[].country").type(JsonFieldType.STRING).description("생산 국가"),
	// 					fieldWithPath("[].thumbnail").type(JsonFieldType.STRING).description("썸네일 이미지 주소"),
	// 					fieldWithPath("[].abv").type(JsonFieldType.NUMBER).description("알코올 도수"),
	// 					fieldWithPath("[].ibu").type(JsonFieldType.NUMBER).description("IBU"),
	// 					fieldWithPath("[].averageStar").type(JsonFieldType.NUMBER).description("맥주 총 별점 평균"),
	// 					fieldWithPath("[].starCount").type(JsonFieldType.NUMBER).description("맥주 총 별점 평균"),
	// 					fieldWithPath("[].beerCategories[]").type(JsonFieldType.ARRAY).description("맥주 카테고리 리스트"),
	// 					fieldWithPath("[].beerCategories[].beerCategoryId").type(JsonFieldType.NUMBER)
	// 						.description("맥주 카테고리 타입 번호"),
	// 					fieldWithPath("[].beerCategories[].beerCategoryType").type(JsonFieldType.STRING)
	// 						.description("맥주 카테고리 타입"),
	// 					fieldWithPath("[].beerTags[]").type(JsonFieldType.ARRAY).description("맥주 태그 리스트"),
	// 					fieldWithPath("[].beerTags[].beerTagId").type(JsonFieldType.NUMBER).description("맥주 태그 번호"),
	// 					fieldWithPath("[].beerTags[].beerTagType").type(JsonFieldType.STRING).description("맥주 태그 타입")
	// 				)
	// 			)));
	//
	// }
	//
	// @Test
	// void getMyPageBeerTest() throws Exception {
	//
	// 	Integer page = 1;
	//
	// 	given(beerService.findMyPageBeers(anyInt())).willReturn(new PageImpl<>(new ArrayList<>()));
	// 	given(beerMapper.beersToMyPageResponse(Mockito.any())).willReturn(GET_MY_PAGE_RESPONSE_PAGE_IMPL);
	//
	// 	ResultActions actions =
	// 		mockMvc.perform(
	// 			RestDocumentationRequestBuilders.get("/users/mypage/beers?page={page}", page)
	// 				.accept(MediaType.APPLICATION_JSON)
	// 		);
	// 	actions
	// 		.andExpect(status().isOk())
	// 		.andDo(document(
	// 			"Get_MyPage_Beers",
	// 			getDocumentResponse(),
	// 			requestParameters(
	// 				parameterWithName("page").description("페이지 번호")
	// 			),
	// 			responseFields(
	// 				List.of(
	// 					fieldWithPath("content[].beerId").type(JsonFieldType.NUMBER).description("맥주 번호"),
	// 					fieldWithPath("content[].korName").type(JsonFieldType.STRING).description("한글 이름"),
	// 					fieldWithPath("content[].myStar").type(JsonFieldType.NUMBER).description("맥주 총 별점 평균"),
	//
	// 					fieldWithPath("pageable").type(JsonFieldType.STRING).description("Pageble 설정"),
	// 					fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 크기"),
	// 					fieldWithPath("number").type(JsonFieldType.NUMBER).description("페이지 번호"),
	// 					fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
	// 					fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 피드 수"),
	// 					fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("Pageble Empty"),
	// 					fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("Pageble Unsorted"),
	// 					fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("Pageble Sorted"),
	// 					fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("Pageble First"),
	// 					fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("Pageble Last"),
	// 					fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("페이지 요소 개수"),
	// 					fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("페이지 Empty 여부")
	// 				)
	// 			)));
	// }

	@Test
	void deleteBeerTest() throws Exception {

		Long beerId = 1L;

		doNothing().when(beerService).deleteBeer(anyLong());

		mockMvc.perform(
				RestDocumentationRequestBuilders.delete("/beers//{beer_id}/delete", beerId)
			)
			.andExpect(status().isNoContent())
			.andDo(
				document(
					"Delete_Beer",
					pathParameters(
						parameterWithName("beer_id").description("맥주 번호")
					)
				)
			);
	}

}
