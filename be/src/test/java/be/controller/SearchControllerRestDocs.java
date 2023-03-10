package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.SearchControllerConstants.*;
import static be.utils.UserControllerConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import be.domain.beer.mapper.BeerMapper;
import be.domain.search.service.SearchService;
import be.domain.search.service.VisionService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class SearchControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private SearchService searchService;
	@MockBean
	private BeerMapper beerMapper;
	@MockBean
	private VisionService visionService;

	@Test
	void getSearchResultTest() throws Exception {

		String queryParam = "검색어";
		Integer page = 1;

		given(searchService.findBeersPageByQueryParam(anyString(), anyInt()))
			.willReturn(new PageImpl<>(new ArrayList<>()));
		given(beerMapper.beersPageToSearchResponse(Mockito.any(Page.class))).willReturn(SEARCH_RESPONSE_PAGE);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/search?query={query}&page={page}", queryParam, page)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Search_Result",
				getDocumentResponse(),
				requestParameters(
					parameterWithName("query").description("검색어"),
					parameterWithName("page").description("페이지 번호")
				),
				responseFields(
					List.of(
						fieldWithPath("data.").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath(".data[].beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath(".data[].korName").type(JsonFieldType.STRING).description("맥주 한글 이름"),
						fieldWithPath(".data[].country").type(JsonFieldType.STRING).description("생산 국가"),
						fieldWithPath(".data[].category").type(JsonFieldType.ARRAY).description("맥주 카테고리"),
						fieldWithPath(".data[].abv").type(JsonFieldType.NUMBER).description("알코올 도수"),
						fieldWithPath(".data[].ibu").type(JsonFieldType.NUMBER).description("IBU"),
						fieldWithPath(".data[].beerDetailsTopTags").type(JsonFieldType.ARRAY).description("맥주 태그"),
						fieldWithPath(".data[].totalAverageStar").type(JsonFieldType.NUMBER).description("평균 별점"),
						fieldWithPath(".data[].totalStarcount").type(JsonFieldType.NUMBER).description("별점 개수"),
						fieldWithPath(".data[].thumbnail").type(JsonFieldType.STRING).description("맥주 사진"),
						fieldWithPath(".pageInfo").type(JsonFieldType.OBJECT).description("Pageble 설정"),
						fieldWithPath(".pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
						fieldWithPath(".pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
						fieldWithPath(".pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 레이팅 수"),
						fieldWithPath(".pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
					)
				)));
	}

	@Test
	void getImageResultTest() throws Exception {

		String queryParam = "검색어";
		Integer page = 1;

		given(visionService.getSimilarProductsFile(Mockito.any(MultipartFile.class))).willReturn(new ArrayList<>());
		given(visionService.findBeersListByImage(anyList())).willReturn(new ArrayList<>());
		given(beerMapper.beersListToSearchResponse(anyList())).willReturn(SEARCH_RESPONSE_LIST);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.multipart("/api/search/image")
					.file(MOCK_MULTIPART_FILE)
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.accept(MediaType.APPLICATION_JSON)
					.characterEncoding("UTF-8")
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Image_Search_Result",
				getDocumentResponse(),
				responseFields(
					List.of(
						fieldWithPath("[].beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath("[].korName").type(JsonFieldType.STRING).description("맥주 한글 이름"),
						fieldWithPath("[].country").type(JsonFieldType.STRING).description("생산 국가"),
						fieldWithPath("[].category").type(JsonFieldType.ARRAY).description("맥주 카테고리"),
						fieldWithPath("[].abv").type(JsonFieldType.NUMBER).description("알코올 도수"),
						fieldWithPath("[].ibu").type(JsonFieldType.NUMBER).description("IBU"),
						fieldWithPath("[].beerDetailsTopTags").type(JsonFieldType.ARRAY).description("맥주 태그"),
						fieldWithPath("[].totalAverageStar").type(JsonFieldType.NUMBER).description("평균 별점"),
						fieldWithPath("[].totalStarcount").type(JsonFieldType.NUMBER).description("별점 개수"),
						fieldWithPath("[].thumbnail").type(JsonFieldType.STRING).description("맥주 사진")
					)
				)));
	}
}
