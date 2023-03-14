package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.FollowControllerConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.anyList;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import be.domain.follow.mapper.FollowMapper;
import be.domain.follow.service.FollowService;
import be.domain.user.service.UserService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class FollowControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private UserService userService;
	@MockBean
	private FollowService followService;
	@MockBean
	private FollowMapper followMapper;

	@Test
	void followTest() throws Exception {

		Long userId = 1L;

		given(followService.createOrDeleteFollow(anyLong())).willReturn(new String());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/follows/{userId}", userId)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Follow",
				pathParameters(
					parameterWithName("userId").description("유저 번호")
				)
			));
	}

	@Test
	void getFollowersListTest() throws Exception {

		Long userId = 1L;
		Integer page = 1;

		given(userService.getLoginUserReturnNull()).willReturn(null);
		given(followService.findFollowers(anyLong(), anyInt())).willReturn(new PageImpl<>(new ArrayList<>()));
		given(followMapper.followersToFollowerResponses(Mockito.any(PageImpl.class), anyList()))
			.willReturn(FOLLOWER_RESPONSE_PAGE);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/follows/{userId}/followers?page={page}", userId, page)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Followers_List",
				getDocumentResponse(),
				pathParameters(
					parameterWithName("userId").description("유저 번호")
				),
				requestParameters(
					parameterWithName("page").description("페이지 번호")
				),
				responseFields(
					List.of(
						fieldWithPath("data.").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath(".data[].userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath(".data[].nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath(".data[].imageUrl").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath(".data[].isFollowing").type(JsonFieldType.BOOLEAN).description("팔로우 여부"),
						fieldWithPath(".pageInfo").type(JsonFieldType.OBJECT).description("Pageble 설정"),
						fieldWithPath(".pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
						fieldWithPath(".pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
						fieldWithPath(".pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 레이팅 수"),
						fieldWithPath(".pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
					)
				)));
	}

	@Test
	void getFollowingsListTest() throws Exception {

		Long userId = 1L;
		Integer page = 1;

		given(userService.getLoginUserReturnNull()).willReturn(null);
		given(followService.findFollowings(anyLong(), anyInt())).willReturn(new PageImpl<>(new ArrayList<>()));
		given(followMapper.followingsToFollowingResponses(Mockito.any(PageImpl.class), anyList()))
			.willReturn(FOLLOWING_RESPONSE_PAGE);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/follows/{userId}/followings?page={page}", userId, page)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_Followings_List",
				getDocumentResponse(),
				pathParameters(
					parameterWithName("userId").description("유저 번호")
				),
				requestParameters(
					parameterWithName("page").description("페이지 번호")
				),
				responseFields(
					List.of(
						fieldWithPath("data.").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath(".data[].userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath(".data[].nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath(".data[].imageUrl").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath(".data[].isFollowing").type(JsonFieldType.BOOLEAN).description("팔로우 여부"),
						fieldWithPath(".pageInfo").type(JsonFieldType.OBJECT).description("Pageble 설정"),
						fieldWithPath(".pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
						fieldWithPath(".pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
						fieldWithPath(".pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 레이팅 수"),
						fieldWithPath(".pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
					)
				)));
	}
}
