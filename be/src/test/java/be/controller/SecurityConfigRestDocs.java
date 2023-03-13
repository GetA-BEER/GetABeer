package be.controller;

import static be.utils.ApiDocumentUtils.*;
import static be.utils.UserControllerConstants.*;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.google.gson.Gson;

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
public class SecurityConfigRestDocs {

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
	void loginTest() throws Exception {

		String content = gson.toJson(USER_LOGIN_DTO);

		User user =
			User.builder()
				.id(1L)
				.age(Age.TWENTIES)
				.gender(Gender.FEMALE)
				.roles(List.of("ROLE_USER"))
				.nickname("닉네임")
				.email("e@mail.com")
				.password(passwordEncoder.encode("password1@"))
				.provider("NONE")
				.build();

		AuthUser authUser = AuthUser.of(user);

		given(userDetailService.loadUserByUsername(anyString())).willReturn(authUser);
		given(userMapper.userToInfoResponse(Mockito.any(User.class))).willReturn(USER_INFO_RESPONSE);

		ResultActions actions =
			mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/login")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		actions
			.andExpect(status().isOk())
			.andDo(document(
				"Login",
				getDocumentRequest(),
				getDocumentResponse(),
				requestFields(
					List.of(
						fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
					)
				)));
	}
}
