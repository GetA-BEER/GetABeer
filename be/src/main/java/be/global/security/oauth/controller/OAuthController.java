package be.global.security.oauth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.global.aop.ChatAop;
import be.global.security.auth.cookieManager.CookieManager;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.oauth.service.GoogleService;
import be.global.security.oauth.service.KakaoService;
import be.global.security.oauth.service.NaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

	private final KakaoService kakaoService;
	private final NaverService naverService;
	private final GoogleService googleService;
	private final UserMapper userMapper;
	private final CookieManager cookieManager;
	private final JwtTokenizer jwtTokenizer;
	private final ChatAop chatAop;

	@ResponseBody
	@GetMapping("/{provider_id}")
	public ResponseEntity<UserDto.LoginResponse> oAuthCallback(
		@PathVariable("provider_id") String providerId,
		@RequestParam("code") String code, HttpServletResponse httpServletResponse) {

		User findUser = User.builder().build();

		switch (providerId) {

			case "kakao":
				findUser = kakaoService.doFilter(code);
				break;

			case "naver":
				findUser = naverService.doFilter(code);
				break;

			case "google":
				findUser = googleService.doFilter(code);
				break;
		}

		String accessToken = jwtTokenizer.delegateAccessToken(findUser);
		String refreshToken = jwtTokenizer.delegateRefreshToken(findUser);

		jwtTokenizer.addRefreshToken(findUser.getEmail(), refreshToken);

		ResponseCookie cookie = cookieManager.createCookie("refreshToken", refreshToken);

		chatAop.createChatRoom(findUser);

		httpServletResponse.setHeader("Authorization", "Bearer " + accessToken);
		httpServletResponse.setHeader("Set-Cookie", cookie.toString());

		UserDto.LoginResponse response = userMapper.userToLoginResponse(findUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
