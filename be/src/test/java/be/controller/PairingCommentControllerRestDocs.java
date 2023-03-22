package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.PairingCommentControllerConstants.*;
import static org.mockito.ArgumentMatchers.*;
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
import be.domain.comment.entity.PairingComment;
import be.domain.comment.mapper.PairingCommentMapper;
import be.domain.comment.service.PairingCommentService;
import be.utils.WithMockCustomUser;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PairingCommentControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private PairingCommentService pairingCommentService;
	@MockBean
	private PairingCommentMapper mapper;

	@Test
	void postPairingCommentTest() throws Exception {

		String content = gson.toJson(PAIRING_COMMENT_POST_DTO);

		given(mapper.postPairingCommentDtoToPairingComment(Mockito.any(PairingCommentDto.Post.class)))
			.willReturn(PairingComment.builder().build());
		given(pairingCommentService.create(Mockito.any(PairingComment.class), anyLong()))
			.willReturn(PairingComment.builder().build());
		given(mapper.pairingCommentToPairingResponse(Mockito.any(PairingComment.class)))
			.willReturn(PAIRING_COMMENT_POST_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/pairings/comments")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isCreated())
			.andDo(document(
				"Post_Pairing_Comment",
				getDocumentRequest(),
				getDocumentResponse(),
				requestFields(
					List.of(
						fieldWithPath("pairingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("코멘트 내용")
					)),
				responseFields(
					List.of(
						fieldWithPath(".pairingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath(".pairingCommentId").type(JsonFieldType.NUMBER).description("페어링 코멘트 아이디"),
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
	void patchPairingCommentTest() throws Exception {

		Long commentId = 1L;

		String content = gson.toJson(PAIRING_COMMENT_PATCH_DTO);

		given(mapper.patchPairingCommentDtoToPairingComment(Mockito.any(PairingCommentDto.Patch.class)))
			.willReturn(PairingComment.builder().build());
		given(pairingCommentService.update(Mockito.any(PairingComment.class), anyLong()))
			.willReturn(PairingComment.builder().build());
		given(mapper.pairingCommentToPairingResponse(Mockito.any(PairingComment.class)))
			.willReturn(PAIRING_COMMENT_POST_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.patch("/api/pairings/comments/{commentId}", commentId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Patch_Pairing_Comment",
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
						fieldWithPath(".pairingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath(".pairingCommentId").type(JsonFieldType.NUMBER).description("페어링 코멘트 아이디"),
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
	void getPairingCommentPageTest() throws Exception {

		Long pairingId = 1L;

		given(pairingCommentService.getPairingComment(anyLong())).willReturn(PAIRING_COMMENT_POST_RESPONSE_LIST);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/pairings/comments?pairingId={pairingId}", pairingId)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Pairing_Comment",
				getDocumentResponse(),
				requestParameters(
					parameterWithName("pairingId").description("페어링 번호")
				),
				responseFields(
					List.of(
						fieldWithPath("[].pairingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath("[].pairingCommentId").type(JsonFieldType.NUMBER).description("페어링 코멘트 아이디"),
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
	void deletePairingCommentTest() throws Exception {

		Long commentId = 1L;

		given(pairingCommentService.delete(anyLong())).willReturn(new String());

		mockMvc.perform(
				RestDocumentationRequestBuilders.delete("/api/pairings/comments/{commentId}", commentId)
			)
			.andExpect(status().isOk())
			.andDo(
				document(
					"Delete_Pairing_Comment",
					pathParameters(
						parameterWithName("commentId").description("코멘트 번호")
					)
				)
			);
	}
}
