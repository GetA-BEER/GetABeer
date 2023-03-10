package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.LikeControllerConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
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

import be.domain.like.service.PairingLikeService;
import be.domain.like.service.RatingLikeService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class LikeControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private RatingLikeService ratingLikeService;
	@MockBean
	private PairingLikeService pairingLikeService;

	@Test
	void clickRatingLikeTest() throws Exception {

		Long ratingId = 1L;

		given(ratingLikeService.clickLike(anyLong())).willReturn(LIKE_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/ratings/likes?ratingId={ratingId}", ratingId)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Post_Rating_Like",
				getDocumentResponse(),
				requestParameters(
					parameterWithName("ratingId").description("레이팅 번호")
				),
				responseFields(
					List.of(
						fieldWithPath(".isUserLikes").type(JsonFieldType.BOOLEAN).description("좋아요 여부")
					))
			));
	}

	@Test
	void clickPairingLikeTest() throws Exception {

		Long pairingId = 1L;

		given(pairingLikeService.clickLike(anyLong())).willReturn(LIKE_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/pairings/likes?pairingId={pairingId}", pairingId)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Post_Pairing_Like",
				getDocumentResponse(),
				requestParameters(
					parameterWithName("pairingId").description("레이팅 번호")
				),
				responseFields(
					List.of(
						fieldWithPath(".isUserLikes").type(JsonFieldType.BOOLEAN).description("좋아요 여부")
					))
			));
	}
}
