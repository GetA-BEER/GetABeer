package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.BeerControllerConstants.*;
import static be.utils.PairingControllerConstants.*;
import static be.utils.UserControllerConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.mapper.PairingMapper;
import be.domain.pairing.service.PairingService;
import be.global.aop.GetABeerAop;
import be.global.init.Init;
import be.utils.WithMockCustomUser;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PairingControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private PairingService pairingService;
	@MockBean
	private BeerService beerService;
	@MockBean
	private PairingMapper mapper;

	@Test
	void postPairingTest() throws Exception {

		String content = gson.toJson(PAIRING_POST_DTO);

		MockMultipartFile json =
			new MockMultipartFile("post", "dto",
				"application/json", content.getBytes(StandardCharsets.UTF_8));

		given(mapper.pairingPostDtoToPairing(PAIRING_POST_DTO))
			.willReturn(Pairing.builder().build());
		given(pairingService.create(Mockito.any(Pairing.class), anyList(), anyLong()))
			.willReturn(new String());

		ResultActions actions =
			mockMvc.perform(
				// RestDocumentationRequestBuilders.post("/api/pairings")
				RestDocumentationRequestBuilders.multipart("/api/pairings")
					.file(json)
					.file(PAIRING_MOCK_MULTIPART_FILE)
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.accept(MediaType.APPLICATION_JSON)
					// .contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isCreated())
			.andDo(document(
				"Post_Pairing",
				getDocumentRequest(),
				requestParts(
					partWithName("post").description("요청 바디"),
					partWithName("files").description("페어링 사진")
				),
				requestFields(
					List.of(
						fieldWithPath("beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("페어링 내용"),
						fieldWithPath("category").type(JsonFieldType.STRING).description("페어링 카테고리")

					)
				)));
	}

	@Test
	void patchPairingTest() throws Exception {

		Long pairingId = 1L;

		String content = gson.toJson(PAIRING_PATCH_DTO);

		MockMultipartFile json =
			new MockMultipartFile("patch", "dto",
				"application/json", content.getBytes(StandardCharsets.UTF_8));

		given(mapper.pairingPatchDtoToPairing(PAIRING_PATCH_DTO))
			.willReturn(Pairing.builder().build());
		given(pairingService.update(Mockito.any(Pairing.class), anyLong(), anyList(), anyList(), anyList()))
			.willReturn(new String());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.multipart("/api/pairings/{pairingId}", pairingId)
					.file(json)
					.file(PAIRING_MOCK_MULTIPART_FILE)
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.accept(MediaType.APPLICATION_JSON)
					// .contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Patch_Pairing",
				getDocumentRequest(),
				requestParts(
					partWithName("patch").description("요청 바디"),
					partWithName("files").description("페어링 사진")
				),
				requestFields(
					List.of(
						fieldWithPath("beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("페어링 내용"),
						fieldWithPath("type").type(JsonFieldType.ARRAY).description("요청 타입"),
						fieldWithPath("url").type(JsonFieldType.ARRAY).description("이미지 주소"),
						fieldWithPath("category").type(JsonFieldType.STRING).description("페어링 카테고리")
					)
				)));
	}

	@Test
	void getPairingTest() throws Exception {

		Long pairingId = 1L;

		given(pairingService.getPairing(anyLong())).willReturn(PAIRING_DETAIL_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/pairings/{pairingId}", pairingId)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Pairing",
				getDocumentResponse(),
				pathParameters(
					parameterWithName("pairingId").description("페어링 번호")
				),
				responseFields(
					List.of(
						fieldWithPath("beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("korName").type(JsonFieldType.STRING).description("한글 이름"),
						fieldWithPath("pairingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("userImage").type(JsonFieldType.STRING).description("프로필 사진"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("페어링 내용"),
						fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("섬네일"),
						fieldWithPath("imageList").type(JsonFieldType.ARRAY).description("이미지 리스트"),
						fieldWithPath("commentList").type(JsonFieldType.ARRAY).description("코멘트 리스트"),
						fieldWithPath("category").type(JsonFieldType.STRING).description("페어링 카테고리"),
						fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
						fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("코멘트 개수"),
						fieldWithPath("isUserLikes").type(JsonFieldType.BOOLEAN).description("좋아요 여부"),
						fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성 시간"),
						fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("마지막 수정 시간")
					)
				)));
	}

	@Test
	void getPairingPageOrderByRecentlyTest() throws Exception {

		String type = "recency";
		String category = "카테고리";
		Long beerId = 1L;
		Integer page = 1;
		Integer size = 10;

		Beer beer = Beer.builder()
			.id(1L)
			.beerDetailsBasic(BEER_DETAILS_BASIC)
			.build();

		given(pairingService.getPairingPageOrderBy(anyLong(), anyString(), anyString(), anyInt(), anyInt()))
			.willReturn(PAIRING_TOTAL_PAGE);
		given(beerService.getBeer(anyLong())).willReturn(beer);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders
					.get("/api/pairings/page/{type}/{category}?beerId={beerId}&page={page}&size={size}",
						type, category, beerId, page, size)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Pairing_Page",
				getDocumentResponse(),
				pathParameters(
					parameterWithName("type").description("타입"),
					parameterWithName("category").description("페어링 카테고리")
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
						fieldWithPath(".data[].pairingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath(".data[].userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath(".data[].nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath(".data[].userImage").type(JsonFieldType.STRING).description("프로필 사진"),
						fieldWithPath(".data[].content").type(JsonFieldType.STRING).description("페어링 내용"),
						fieldWithPath(".data[].thumbnail").type(JsonFieldType.STRING).description("섬네일"),
						fieldWithPath(".data[].category").type(JsonFieldType.STRING).description("페어링 카테고리"),
						fieldWithPath(".data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
						fieldWithPath(".data[].commentCount").type(JsonFieldType.NUMBER).description("코멘트 개수"),
						fieldWithPath(".data[].isUserLikes").type(JsonFieldType.BOOLEAN).description("좋아요 여부"),
						fieldWithPath(".data[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
						fieldWithPath(".data[].modifiedAt").type(JsonFieldType.STRING).description("마지막 수정 시간"),
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
	void deletePairingTest() throws Exception {

		Long pairingId = 1L;

		given(pairingService.delete(anyLong())).willReturn(new String());

		mockMvc.perform(
				RestDocumentationRequestBuilders.delete("/api/pairings/{pairingId}", pairingId)
			)
			.andExpect(status().isOk())
			.andDo(
				document(
					"Delete_Rating",
					pathParameters(
						parameterWithName("pairingId").description("페어링 번호")
					)
				)
			);
	}
}
