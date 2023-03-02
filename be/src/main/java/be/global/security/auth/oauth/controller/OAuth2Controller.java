package be.global.security.auth.oauth.controller;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//
// import be.global.security.auth.oauth.dto.OAuthDto;
// import be.global.security.auth.oauth.service.OAuthService;
import be.global.security.auth.oauth.handler.OAuth2SuccessHandler;
import be.global.security.auth.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
	// private final OAuthService oAuthService;
	private final CustomOAuth2UserService oAuth2UserService;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;

	/**
	 * OAuth 로그인 후 인증코드를 넘겨받는 api
	 * 첫 로그인인 경우 회원가입
	 * @param provider
	 * @param code
	 * @return
	 */
	@GetMapping("/oauth/{provider}")
	public ResponseEntity OAuth2Login(@PathVariable String provider, @RequestParam String code) {
		oAuth2UserService.login(provider, code);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@GetMapping("/oauth/handler")
	public ResponseEntity OAuth2Handler(HttpServletRequest request, @RequestParam String email) {

		String act = request.getHeader("Authorization");
		String[] cookies = request.getHeader("Cookie").split(";");
		Stream<String> stream = Arrays.stream(cookies)
			.map(cookie -> cookie.replace(" ", ""))
			.filter(c -> c.startsWith("refreshToken"));
		String value = stream.reduce((first, second) -> second)
			.map(v -> v.replace("refreshToken=", ""))
			.orElse(null);

		oAuth2SuccessHandler.handler(act, value, email);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
