package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.NotificationControllerConstants.*;
import static be.utils.UserControllerConstants.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
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
import org.springframework.http.ResponseCookie;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.global.security.auth.cookieManager.CookieManager;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.oauth.service.GoogleService;
import be.global.security.oauth.service.KakaoService;
import be.global.security.oauth.service.NaverService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class OAuthControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@MockBean
	private KakaoService kakaoService;
	@MockBean
	private UserMapper userMapper;
	@MockBean
	private CookieManager cookieManager;
	@MockBean
	private JwtTokenizer jwtTokenizer;

	@Test
	void oAuthCallbackTest() throws Exception {

		String provider = "kakao";
		String code = "인증 토큰";

		given(kakaoService.doFilter(anyString())).willReturn(User.builder().build());
		given(jwtTokenizer.delegateAccessToken(Mockito.any(User.class))).willReturn("");
		given(jwtTokenizer.delegateRefreshToken(Mockito.any(User.class))).willReturn("");
		doNothing().when(jwtTokenizer).addRefreshToken(anyString(), anyString());
		given(cookieManager.createCookie(anyString(), anyString())).willReturn(ResponseCookie.from("a", "b").build());
		doNothing().when(jwtTokenizer).addRefreshToken(anyString(), anyString());
		given(userMapper.userToLoginResponse(Mockito.any(User.class))).willReturn(USER_LOGIN_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/oauth/{provider_id}?code={code}", provider, code)
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isCreated())
			.andDo(document(
				"OAuth2",
				getDocumentResponse(),
				responseHeaders(
					headerWithName("Authorization").description("액세스 토큰"),
					headerWithName("Set-Cookie").description("쿠키 설정")
				),
				pathParameters(
					parameterWithName("provider_id").description("OAuth 제공자")
				),
				requestParameters(
					parameterWithName("code").description("인증 토큰")
				),
				responseFields(
					List.of(
						fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 번호"),
						fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
					)
				)));
	}
}
