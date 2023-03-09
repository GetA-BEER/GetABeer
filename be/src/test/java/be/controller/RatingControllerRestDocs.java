package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.BeerControllerConstants.*;
import static be.utils.RatingControllerConstants.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.beertag.entity.BeerTagType;
import be.domain.rating.dto.RatingRequestDto;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;
import be.domain.rating.mapper.RatingMapper;
import be.domain.rating.mapper.RatingTagMapper;
import be.domain.rating.service.RatingService;

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
				"Patch_Rating",
				getDocumentResponse(),
				responseFields(
					List.of(
						fieldWithPath("beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("korName").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("ratingId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("userId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("nickname").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("userImage").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("평가 내용"),
						fieldWithPath("ratingTag").type(JsonFieldType.STRING).description("평가 내용"),
						fieldWithPath("star").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("ratingCommentList").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("isUserLikes").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("createdAt").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("modifiedAt").type(JsonFieldType.NUMBER).description("별점")
						// .beerId(1L)
						// .korName("한글 이름")
						// .ratingId(1L)
						// .userId(1L)
						// .nickname("닉네임")
						// .userImage("프로필 이미지")
						// .content("평가 내용")
						// .ratingTag(List.of(BeerTagType.STRAW))
						// .star(4.5)
						// .likeCount(10)
						// .commentCount(10)
						// .ratingCommentList(new ArrayList<>())
						// .isUserLikes(false)
						// .createdAt(LocalDateTime.now())
						// .modifiedAt(LocalDateTime.now())

					)
				)));
	}
}
