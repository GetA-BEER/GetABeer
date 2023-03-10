package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.UserControllerConstants.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserService;
import be.global.security.auth.userdetails.AuthUser;
import be.global.security.auth.userdetails.UserDetailService;
import be.utils.WithMockCustomUser;

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

	@Test
	void postUserInfoTest() throws Exception {

		Long userId = 1L;

		String content = gson.toJson(USER_INFO_POST_DTO);

		given(userMapper.infoPostToUser(anyLong(), Mockito.any(UserDto.UserInfoPost.class)))
			.willReturn(User.builder().build());
		doNothing().when(userService).postUserInfo(Mockito.any(User.class));

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/register/user/{user-id}", userId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Post_User_Info",
				getDocumentRequest(),
				pathParameters(
					parameterWithName("user_id").description("사용자 번호")
				),
				requestFields(
					List.of(
						fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
						fieldWithPath("age").type(JsonFieldType.STRING).description("나이"),
						fieldWithPath("userBeerCategories").type(JsonFieldType.ARRAY).description("선호 맥주 카테고리"),
						fieldWithPath("userBeerTags").type(JsonFieldType.ARRAY).description("선호 맥주 태그")
					)
				)));
	}

	@Test
	void patchUserTest() throws Exception {

		String content = gson.toJson(USER_EDIT_INFO_DTO);

		given(userMapper.editToUser(Mockito.any(UserDto.EditUserInfo.class))).willReturn(User.builder().build());
		given(userService.updateUser(Mockito.any(User.class))).willReturn(User.builder().build());
		given(userMapper.userToInfoResponse(Mockito.any(User.class))).willReturn(USER_INFO_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.patch("/api/mypage/userinfo")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Patch_User_Info",
				getDocumentRequest(),
				getDocumentResponse(),
				requestFields(
					List.of(
						fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
						fieldWithPath("age").type(JsonFieldType.STRING).description("나이"),
						fieldWithPath("userBeerCategories").type(JsonFieldType.ARRAY).description("선호 맥주 카테고리"),
						fieldWithPath("userBeerTags").type(JsonFieldType.ARRAY).description("선호 맥주 태그")
					)),
				responseFields(
					List.of(
						fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
						fieldWithPath("age").type(JsonFieldType.STRING).description("나이"),
						fieldWithPath("followerCount").type(JsonFieldType.NUMBER).description("팔로워 숫자"),
						fieldWithPath("followingCount").type(JsonFieldType.NUMBER).description("팔로잉 숫자"),
						fieldWithPath("userBeerCategories").type(JsonFieldType.ARRAY).description("선호 맥주 카테고리"),
						fieldWithPath("userBeerTags").type(JsonFieldType.ARRAY).description("선호 맥주 태그")
					)
				)));
	}

	@Test
	void editProfileImageTest() throws Exception {

		MockMultipartFile image =
			new MockMultipartFile("image",
				"image.png", "image/png", "<<png data>>".getBytes());

		String content = gson.toJson(image);

		given(userService.updateProfileImage(Mockito.any(MultipartFile.class))).willReturn(User.builder().build());
		given(userMapper.userToInfoResponse(Mockito.any(User.class))).willReturn(USER_INFO_RESPONSE_DTO);

		ResultActions actions =

			mockMvc.perform(multipart("/api/mypage/userinfo/image")
				.file(MOCK_MULTIPART_FILE)
				// .param("value", "image")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Edit_Profile_Image",
				getDocumentResponse(),
				responseFields(
					List.of(
						fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
						fieldWithPath("age").type(JsonFieldType.STRING).description("나이"),
						fieldWithPath("followerCount").type(JsonFieldType.NUMBER).description("팔로워 숫자"),
						fieldWithPath("followingCount").type(JsonFieldType.NUMBER).description("팔로잉 숫자"),
						fieldWithPath("userBeerCategories").type(JsonFieldType.ARRAY).description("선호 맥주 카테고리"),
						fieldWithPath("userBeerTags").type(JsonFieldType.ARRAY).description("선호 맥주 태그")
					)
				)));
	}

	@Test
	void readUserTest() throws Exception {

		given(userService.getLoginUser()).willReturn(User.builder().build());
		given(userMapper.userToInfoResponse(Mockito.any(User.class))).willReturn(USER_INFO_RESPONSE_DTO);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/user")
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Read_User_Info",
				getDocumentResponse(),
				responseFields(
					List.of(
						fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("프로필 이미지"),
						fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
						fieldWithPath("age").type(JsonFieldType.STRING).description("나이"),
						fieldWithPath("followerCount").type(JsonFieldType.NUMBER).description("팔로워 숫자"),
						fieldWithPath("followingCount").type(JsonFieldType.NUMBER).description("팔로잉 숫자"),
						fieldWithPath("userBeerCategories").type(JsonFieldType.ARRAY).description("선호 맥주 카테고리"),
						fieldWithPath("userBeerTags").type(JsonFieldType.ARRAY).description("선호 맥주 태그")
					)
				)));
	}

	@Test
	void editPasswordTest() throws Exception {

		String content = gson.toJson(EDIT_PASSWORD_DTO);

		given(userService.verifyPassword(Mockito.any(UserDto.EditPassword.class))).willReturn(User.builder().build());

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/user")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Edit_Password",
				getDocumentRequest(),
				requestFields(
					List.of(
						fieldWithPath("oldPassword").type(JsonFieldType.STRING).description("이전 비밀번호"),
						fieldWithPath("newPassword").type(JsonFieldType.STRING).description("새 비밀번호"),
						fieldWithPath("newVerifyPassword").type(JsonFieldType.STRING).description("바뀐 비밀번호")
					)
				)));
	}

	@Test
	void logoutUserTest() throws Exception {

		given(userService.getLoginUser()).willReturn(User.builder().build());
		doNothing().when(userService).logout(Mockito.any(HttpServletRequest.class), Mockito.any(User.class));

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/user/logout")
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Logout_User"
			));
	}

	@Test
	void withDrawUserTest() throws Exception {

		given(userService.getLoginUser()).willReturn(User.builder().build());
		doNothing().when(userService).withdraw();
		doNothing().when(userService).logout(Mockito.any(HttpServletRequest.class), Mockito.any(User.class));

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/user/logout")
					.accept(MediaType.APPLICATION_JSON)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Withdraw_User"
			));
	}
}
