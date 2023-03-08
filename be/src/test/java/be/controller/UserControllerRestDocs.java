package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.UserTestConstants.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.beust.ah.A;
import com.google.gson.Gson;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserService;
import be.global.security.auth.userdetails.AuthUser;
import be.global.security.auth.userdetails.UserDetailService;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class UserControllerRestDocs {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@MockBean
	private UserService userService;
	@MockBean
	private UserMapper userMapper;
	@MockBean
	private UserDetailService userDetailService;

	@Test
	void registerUserTest() throws Exception {

		String content = gson.toJson(USER_REGISTER_POST_DTO);

		given(userMapper.postToUser(Mockito.any(UserDto.RegisterPost.class))).willReturn(User.builder().build());
		given(userService.registerUser(Mockito.any(User.class))).willReturn(User.builder().build());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/register/user")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Register_User",
				getDocumentRequest(),
				requestFields(
					List.of(
						fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
					)
				)));
	}

	// @Test
	// void loginTest() throws Exception {
	//
	// 	String content = gson.toJson(USER_LOGIN_DTO);
	//
	// 	User user =
	// 		User.builder()
	// 			.id(1L)
	// 			.age(Age.TWENTIES)
	// 			.gender(Gender.FEMALE)
	// 			.roles(List.of("ROLE_USER"))
	// 			.nickname("닉네임")
	// 			.email("e@mail.com")
	// 			.password(passwordEncoder.encode("password1@"))
	// 			.provider("NONE")
	// 			.build();
	//
	// 	AuthUser authUser = AuthUser.of(user);
	//
	// 	given(userDetailService.loadUserByUsername(anyString())).willReturn(authUser);
	//
	// 	// given(userMapper.postToUser(Mockito.any(UserDto.RegisterPost.class))).willReturn(User.builder().build());
	// 	// given(userService.findUserEmail(Mockito.any(String.class))).willReturn(1L);
	// 	// given(userService.findVerifiedUser(Mockito.any(Long.class))).willReturn(User.builder().build());
	// 	// doNothing().when(userService).verifyUserStatus(anyString());
	// 	given(userMapper.userToInfoResponse(Mockito.any(User.class))).willReturn(USER_INFO_RESPONSE);
	//
	// 	ResultActions actions =
	// 		mockMvc.perform(
	// 			RestDocumentationRequestBuilders.post("/api/login")
	// 				.accept(MediaType.APPLICATION_JSON)
	// 				.contentType(MediaType.APPLICATION_JSON)
	// 				.content(content)
	// 		);
	//
	// 	actions
	// 		.andExpect(status().isOk())
	// 		.andDo(document(
	// 			"Login",
	// 			getDocumentRequest(),
	// 			getDocumentResponse(),
	// 			requestFields(
	// 				List.of(
	// 					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
	// 					fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
	// 				)
	// 			),
	// 			responseFields(
	// 				List.of(
	// 					fieldWithPath(".imageUrl").type(JsonFieldType.STRING).description("프로필 이미지"),
	// 					fieldWithPath(".nickname").type(JsonFieldType.STRING).description("닉네임"),
	// 					fieldWithPath(".gender").type(JsonFieldType.STRING).description("성별"),
	// 					fieldWithPath(".age").type(JsonFieldType.STRING).description("연령대"),
	// 					fieldWithPath(".followerCount").type(JsonFieldType.NUMBER).description("팔로워 숫자"),
	// 					fieldWithPath(".followingCount").type(JsonFieldType.NUMBER).description("팔로잉 숫자"),
	// 					fieldWithPath(".userBeerCategories").type(JsonFieldType.ARRAY).description("선호 맥주 카테고리"),
	// 					fieldWithPath(".userBeerTags").type(JsonFieldType.ARRAY).description("선호 맥주 태그")
	// 				)
	// 			)));
	// }
}
