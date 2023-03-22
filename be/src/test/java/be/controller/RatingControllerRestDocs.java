package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.BeerControllerConstants.*;
import static be.utils.RatingControllerConstants.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.rating.dto.RatingRequestDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;
import be.domain.rating.mapper.RatingMapper;
import be.domain.rating.mapper.RatingTagMapper;
import be.domain.rating.service.RatingService;
import be.utils.WithMockCustomUser;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class RatingControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private RatingService ratingService;
	@MockBean
	private BeerService beerService;
	@MockBean
	private RatingMapper ratingMapper;
	@MockBean
	private RatingTagMapper tagMapper;

	@Test
	void postRatingTest() throws Exception {

		String content = gson.toJson(RATING_POST_DTO);

		doNothing().when(ratingService).checkVerifiedTag(anyString(), anyString(), anyString(), anyString());
		given(tagMapper.ratingPostDtoToRatingTag(Mockito.any(RatingRequestDto.Post.class)))
			.willReturn(RatingTag.builder().build());
		given(ratingMapper.ratingPostDtoToRating(Mockito.any(RatingRequestDto.Post.class)))
			.willReturn(Rating.builder().build());
		given(ratingService.create(Mockito.any(Rating.class), anyLong(), Mockito.any(RatingTag.class)))
			.willReturn(new String());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/ratings")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isCreated())
			.andDo(document(
				"Post_Rating",
				getDocumentRequest(),
				requestFields(
					List.of(
						fieldWithPath("beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("평가 내용"),
						fieldWithPath("star").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("color").type(JsonFieldType.STRING).description("색상"),
						fieldWithPath("taste").type(JsonFieldType.STRING).description("맛"),
						fieldWithPath("flavor").type(JsonFieldType.STRING).description("향"),
						fieldWithPath("carbonation").type(JsonFieldType.STRING).description("탄산")

					)
				)));
	}

	@Test
	void patchRatingTest() throws Exception {

		Long ratingId = 1L;

		String content = gson.toJson(RATING_PATCH_DTO);

		given(tagMapper.ratingPatchDtoToRatingTag(Mockito.any(RatingRequestDto.Patch.class)))
			.willReturn(RatingTag.builder().build());
		given(ratingMapper.ratingPatchDtoToRating(Mockito.any(RatingRequestDto.Patch.class)))
			.willReturn(Rating.builder().build());
		given(ratingService.update(Mockito.any(Rating.class), anyLong(), Mockito.any(RatingTag.class)))
			.willReturn(new String());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.patch("/api/ratings/{ratingId}", ratingId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Patch_Rating",
				getDocumentRequest(),
				pathParameters(
					parameterWithName("ratingId").description("레이팅 아이디")
				),
				requestFields(
					List.of(
						fieldWithPath("beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("평가 내용"),
						fieldWithPath("star").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("color").type(JsonFieldType.STRING).description("색상"),
						fieldWithPath("taste").type(JsonFieldType.STRING).description("맛"),
						fieldWithPath("flavor").type(JsonFieldType.STRING).description("향"),
						fieldWithPath("carbonation").type(JsonFieldType.STRING).description("탄산")

					)
				)));
	}

	@Test
	void getRatingTest() throws Exception {

		Long ratingId = 1L;

		given(ratingService.getRatingResponse(anyLong())).willReturn(RATING_DETAIL_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/ratings/{ratingId}", ratingId)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Rating",
				getDocumentResponse(),
				pathParameters(
					parameterWithName("ratingId").description("레이팅 아이디")
				),
				responseFields(
					List.of(
						fieldWithPath("beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("korName").type(JsonFieldType.STRING).description("한글 이름"),
						fieldWithPath("ratingId").type(JsonFieldType.NUMBER).description("레이팅 아이디"),
						fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("userImage").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("평가 내용"),
						fieldWithPath("ratingTag").type(JsonFieldType.ARRAY).description("레이팅 태그"),
						fieldWithPath("star").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
						fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("코멘트 개수"),
						fieldWithPath("ratingCommentList").type(JsonFieldType.ARRAY).description("코멘트 리스트"),
						fieldWithPath("isUserLikes").type(JsonFieldType.BOOLEAN).description("좋아요 여부"),
						fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성 시간"),
						fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("최종 수정 시간")
					)
				)));
	}

	@Test
	void getRatingPageOrderByRecentlyTest() throws Exception {

		String type = "recency";
		Long beerId = 1L;
		Integer page = 1;
		Integer size = 10;

		Beer beer = Beer.builder()
			.id(1L)
			.beerDetailsBasic(BEER_DETAILS_BASIC)
			.build();

		given(ratingService.getRatingPageOrderBy(anyLong(), anyInt(), anyInt(), anyString()))
			.willReturn(RATING_TOTAL_PAGE);
		given(beerService.getBeer(anyLong())).willReturn(beer);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders
					.get("/api/ratings/page/{type}?beerId={beerId}&page={page}&size={size}",
						type, beerId, page, size)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Rating_Page",
				getDocumentResponse(),
				pathParameters(
					parameterWithName("type").description("타입")
				),
				requestParameters(
					parameterWithName("beerId").description("맥주 번호"),
					parameterWithName("page").description("페이지 번호"),
					parameterWithName("size").description("페이지 사이즈")
				),
				responseFields(
					List.of(
						fieldWithPath("data.").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath(".data[].beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath(".data[].korName").type(JsonFieldType.STRING).description("한글 이름"),
						fieldWithPath(".data[].ratingId").type(JsonFieldType.NUMBER).description("레이팅 아이디"),
						fieldWithPath(".data[].userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath(".data[].nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath(".data[].userImage").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath(".data[].content").type(JsonFieldType.STRING).description("평가 내용"),
						fieldWithPath(".data[].ratingTag").type(JsonFieldType.ARRAY).description("레이팅 태그"),
						fieldWithPath(".data[].star").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath(".data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
						fieldWithPath(".data[].commentCount").type(JsonFieldType.NUMBER).description("코멘트 개수"),
						fieldWithPath(".data[].isUserLikes").type(JsonFieldType.BOOLEAN).description("좋아요 여부"),
						fieldWithPath(".data[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
						fieldWithPath(".data[].modifiedAt").type(JsonFieldType.STRING).description("최종 수정 시간"),
						fieldWithPath(".pageInfo").type(JsonFieldType.OBJECT).description("Pageble 설정"),
						fieldWithPath(".pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
						fieldWithPath(".pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
						fieldWithPath(".pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 레이팅 수"),
						fieldWithPath(".pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
						fieldWithPath(".pageInfo.beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath(".pageInfo.beerKorName").type(JsonFieldType.STRING).description("한글 이름"),
						fieldWithPath(".pageInfo.beerEngName").type(JsonFieldType.STRING).description("영어 이름")
					)
				)));
	}

	@Test
	@WithMockCustomUser
	void deleteRatingTest() throws Exception {

		Long ratingId = 1L;

		given(ratingService.delete(anyLong())).willReturn(new String());

		mockMvc.perform(
				RestDocumentationRequestBuilders.delete("/api/ratings/{ratingId}", ratingId)
			)
			.andExpect(status().isOk())
			.andDo(
				document(
					"Delete_Rating",
					pathParameters(
						parameterWithName("ratingId").description("레이팅 번호")
					)
				)
			);
	}
}
