package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.BeerWishlistControllerConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.anyInt;
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

import com.google.gson.Gson;

import be.domain.beerwishlist.mapper.BeerWishlistMapper;
import be.domain.beerwishlist.service.BeerWishlistService;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class BeerWishlistControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private UserService userService;
	@MockBean
	private BeerWishlistMapper beerWishlistMapper;
	@MockBean
	private BeerWishlistService beerWishlistService;

	@Test
	void wishBeerTest() throws Exception {

		Long beerId = 1L;

		doNothing().when(beerWishlistService).verifyWishState(anyLong());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.patch("/api/beers/{beer-id}/wish", beerId)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Wish_Beer",
				pathParameters(
					parameterWithName("beer-id").description("맥주 번호")
				)
			));
	}

	@Test
	void getMyPageBeerTest() throws Exception {

		Integer page = 1;
		Integer size = 10;

		given(beerWishlistService.getUserWishlist(anyInt(), anyInt())).willReturn(new PageImpl<>(new ArrayList<>()));
		given(beerWishlistMapper.beersAndWishlistToResponse(Mockito.any(Page.class)))
			.willReturn(USER_WISHLIST_RESPONSE_PAGE);
		given(userService.getLoginUser()).willReturn(User.builder().build());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/beers/mypage/wishlist?page={page}&size={size}", page, size)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_My_Page_Beer",
				getDocumentResponse(),
				requestParameters(
					parameterWithName("page").description("페이지 번호"),
					parameterWithName("size").description("페이지 크기")
				),
				responseFields(
					List.of(
						fieldWithPath("data.").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath(".data[].beer.beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath(".data[].beer.korName").type(JsonFieldType.STRING).description("맥주 한글 이름"),
						fieldWithPath(".data[].beer.beerCategories[].beerCategoryType").type(JsonFieldType.STRING)
							.description("맥주 카테고리"),
						fieldWithPath(".data[].beer.thumbnail").type(JsonFieldType.STRING).description("맥주 사진"),
						fieldWithPath(".data[].beer.country").type(JsonFieldType.STRING).description("생산 국가"),
						fieldWithPath(".data[].beer.abv").type(JsonFieldType.NUMBER).description("알코올 도수"),
						fieldWithPath(".data[].beer.ibu").type(JsonFieldType.NUMBER).description("IBU"),
						fieldWithPath(".data[].isUserWish").type(JsonFieldType.BOOLEAN).description("좋아요 여부"),
						fieldWithPath(".pageInfo").type(JsonFieldType.OBJECT).description("Pageble 설정"),
						fieldWithPath(".pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
						fieldWithPath(".pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
						fieldWithPath(".pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 레이팅 수"),
						fieldWithPath(".pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
					)
				)));
	}
}
