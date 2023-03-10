package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.PairingCommentControllerConstants.*;
import static be.utils.RatingCommentControllerConstants.*;
import static org.mockito.ArgumentMatchers.anyLong;
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

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.PairingComment;
import be.domain.comment.entity.RatingComment;
import be.domain.comment.mapper.RatingCommentMapper;
import be.domain.comment.service.RatingCommentService;
import be.utils.WithMockCustomUser;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class RatingCommentControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private RatingCommentService ratingCommentService;
	@MockBean
	private RatingCommentMapper mapper;

	@Test
	void postRatingCommentTest() throws Exception {

		String content = gson.toJson(RATING_COMMENT_POST_DTO);

		given(mapper.ratingCommentPostDtoToRatingComment(Mockito.any(RatingCommentDto.Post.class)))
			.willReturn(RatingComment.builder().build());
		given(ratingCommentService.create(Mockito.any(RatingComment.class), anyLong()))
			.willReturn(RatingComment.builder().build());
		given(mapper.ratingCommentToRatingCommentResponse(Mockito.any(RatingComment.class)))
			.willReturn(RATING_COMMENT_POST_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/ratings/comments")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isCreated())
			.andDo(document(
				"Post_Rating_Comment",
				getDocumentRequest(),
				getDocumentResponse(),
				requestFields(
					List.of(
						fieldWithPath("ratingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("코멘트 내용")
					)),
				responseFields(
					List.of(
						fieldWithPath(".ratingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath(".ratingCommentId").type(JsonFieldType.NUMBER).description("페어링 코멘트 아이디"),
						fieldWithPath(".userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath(".nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath(".userImage").type(JsonFieldType.STRING).description("프로필 사진"),
						fieldWithPath(".content").type(JsonFieldType.STRING).description("코멘트 내용"),
						fieldWithPath(".createdAt").type(JsonFieldType.STRING).description("작성 시간"),
						fieldWithPath(".modifiedAt").type(JsonFieldType.STRING).description("마지막 수정 시간")
					))
			));
	}

	@Test
	void patchRatingCommentTest() throws Exception {

		Long commentId = 1L;

		String content = gson.toJson(RATING_COMMENT_PATCH_DTO);

		given(mapper.ratingCommentPatchDtoToRatingComment(Mockito.any(RatingCommentDto.Patch.class)))
			.willReturn(RatingComment.builder().build());
		given(ratingCommentService.update(Mockito.any(RatingComment.class), anyLong()))
			.willReturn(RatingComment.builder().build());
		given(mapper.ratingCommentToRatingCommentResponse(Mockito.any(RatingComment.class)))
			.willReturn(RATING_COMMENT_POST_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.patch("/api/ratings/comments/{commentId}", commentId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Patch_Rating_Comment",
				getDocumentRequest(),
				getDocumentResponse(),
				pathParameters(
					parameterWithName("commentId").description("코멘트 번호")
				),
				requestFields(
					List.of(
						fieldWithPath("content").type(JsonFieldType.STRING).description("코멘트 내용")
					)),
				responseFields(
					List.of(
						fieldWithPath(".ratingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath(".ratingCommentId").type(JsonFieldType.NUMBER).description("페어링 코멘트 아이디"),
						fieldWithPath(".userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath(".nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath(".userImage").type(JsonFieldType.STRING).description("프로필 사진"),
						fieldWithPath(".content").type(JsonFieldType.STRING).description("코멘트 내용"),
						fieldWithPath(".createdAt").type(JsonFieldType.STRING).description("작성 시간"),
						fieldWithPath(".modifiedAt").type(JsonFieldType.STRING).description("마지막 수정 시간")
					))
			));
	}

	@Test
	void getRatingCommentPageTest() throws Exception {

		Long ratingId = 1L;

		given(ratingCommentService.getRatingComment(anyLong())).willReturn(RATING_COMMENT_POST_RESPONSE_LIST);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/ratings/comments?ratingId={ratingId}", ratingId)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Patch_Pairing_Comment",
				getDocumentResponse(),
				requestParameters(
					parameterWithName("ratingId").description("레이팅 번호")
				),
				responseFields(
					List.of(
						fieldWithPath("[].ratingId").type(JsonFieldType.NUMBER).description("레이팅 아이디"),
						fieldWithPath("[].ratingCommentId").type(JsonFieldType.NUMBER).description("레이팅 코멘트 아이디"),
						fieldWithPath("[].userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath("[].nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("[].userImage").type(JsonFieldType.STRING).description("프로필 사진"),
						fieldWithPath("[].content").type(JsonFieldType.STRING).description("코멘트 내용"),
						fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
						fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING).description("마지막 수정 시간")
					))
			));
	}

	@Test
	@WithMockCustomUser
	void deleteRatingCommentTest() throws Exception {

		Long commentId = 1L;

		given(ratingCommentService.delete(anyLong())).willReturn(new String());

		mockMvc.perform(
				RestDocumentationRequestBuilders.delete("/api/ratings/comments/{commentId}", commentId)
			)
			.andExpect(status().isOk())
			.andDo(
				document(
					"Delete_Rating_Comment",
					pathParameters(
						parameterWithName("commentId").description("코멘트 번호")
					)
				)
			);
	}
}
