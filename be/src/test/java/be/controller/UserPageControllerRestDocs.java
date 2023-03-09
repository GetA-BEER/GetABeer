package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.UserPageTestConstants.*;
import static be.utils.UserTestConstants.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import be.domain.like.repository.PairingLikeRepository;
import be.domain.like.repository.RatingLikeRepository;
import be.domain.pairing.mapper.PairingMapper;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.mapper.RatingMapper;
import be.domain.user.dto.MyPageMultiResponseDto;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserPageService;
import be.domain.user.service.UserService;
import be.global.dto.PageInfo;
import be.global.security.auth.userdetails.UserDetailService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class UserPageControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@MockBean
	private UserService userService;
	@MockBean
	private UserPageService userPageService;
	@MockBean
	private UserMapper userMapper;
	@MockBean
	private RatingMapper ratingMapper;
	@MockBean
	private PairingMapper pairingMapper;
	@MockBean
	private MyPageMultiResponseDto myPageMultiResponseDto;

	@Test
	void getMyRatingsTest() throws Exception {

		Integer page = 1;

		given(userPageService.getUserRating(anyInt())).willReturn(new PageImpl<>(new ArrayList<>()));
		given(ratingMapper.ratingToRatingResponse(Mockito.any(Page.class), Mockito.any(RatingLikeRepository.class)))
			.willReturn(MY_RATING_RESPONSE_PAGE);
		given(userService.getLoginUser()).willReturn(User.builder().build());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/mypage/ratings?page={page}", page)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_My_Ratings",
				getDocumentResponse(),
				requestParameters(
					parameterWithName("page").description("페이지 번호")
				),
				responseFields(
					List.of(
						fieldWithPath("data.").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath(".data[].beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath(".data[].ratingId").type(JsonFieldType.NUMBER).description("레이팅 아이디"),
						fieldWithPath(".data[].userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath(".data[].nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath(".data[].userImage").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath(".data[].content").type(JsonFieldType.STRING).description("레이팅 내용"),
						fieldWithPath(".data[].ratingTag[]").type(JsonFieldType.ARRAY).description("레이팅 태그"),
						fieldWithPath(".data[].star").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath(".data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 숫자"),
						fieldWithPath(".data[].commentCount").type(JsonFieldType.NUMBER).description("코멘트 숫자"),
						fieldWithPath(".data[].isUserLikes").type(JsonFieldType.BOOLEAN).description("좋아요 여부"),
						fieldWithPath(".data[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
						fieldWithPath(".data[].modifiedAt").type(JsonFieldType.STRING).description("마지막 수정 시간"),
						fieldWithPath(".pageInfo").type(JsonFieldType.OBJECT).description("Pageble 설정"),
						fieldWithPath(".pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
						fieldWithPath(".pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
						fieldWithPath(".pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 레이팅 수"),
						fieldWithPath(".pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
					)
				)));
	}

	@Test
	void getMyPairingTest() throws Exception {

		Integer page = 1;

		given(userPageService.getUserPairing(anyInt())).willReturn(new PageImpl<>(new ArrayList<>()));
		given(pairingMapper.pairingToPairingResponse(Mockito.any(Page.class), Mockito.any(PairingLikeRepository.class)))
			.willReturn(MY_PAIRING_RESPONSE_PAGE);
		given(userService.getLoginUser()).willReturn(User.builder().build());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/mypage/pairing?page={page}", page)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Get_My_Ratings",
				requestParameters(
					parameterWithName("page").description("페이지 번호")
				),
				responseFields(
					List.of(
						fieldWithPath("data.").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath(".data[].beerId").type(JsonFieldType.NUMBER).description("맥주 아이디"),
						fieldWithPath(".data[].korName").type(JsonFieldType.STRING).description("맥주 한글 이름"),
						fieldWithPath(".data[].pairingId").type(JsonFieldType.NUMBER).description("페어링 아이디"),
						fieldWithPath(".data[].userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
						fieldWithPath(".data[].nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath(".data[].userImage").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath(".data[].content").type(JsonFieldType.STRING).description("페어링 내용"),
						fieldWithPath(".data[].thumbnail").type(JsonFieldType.STRING).description("페어링 사진"),
						fieldWithPath(".data[].category").type(JsonFieldType.STRING).description("페어링 카테고리"),
						fieldWithPath(".data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 숫자"),
						fieldWithPath(".data[].commentCount").type(JsonFieldType.NUMBER).description("코멘트 숫자"),
						fieldWithPath(".data[].isUserLikes").type(JsonFieldType.BOOLEAN).description("좋아요 여부"),
						fieldWithPath(".data[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
						fieldWithPath(".data[].modifiedAt").type(JsonFieldType.STRING).description("마지막 수정 시간"),
						fieldWithPath(".pageInfo").type(JsonFieldType.OBJECT).description("Pageble 설정"),
						fieldWithPath(".pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
						fieldWithPath(".pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
						fieldWithPath(".pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 레이팅 수"),
						fieldWithPath(".pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
					)
				)));
	}
}
