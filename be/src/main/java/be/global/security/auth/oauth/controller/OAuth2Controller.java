package be.global.security.auth.oauth.controller;

import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.global.security.auth.oauth.dto.OAuthDto;
import be.global.security.auth.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
	private final CustomOAuth2UserService oAuth2UserService;

	/**
	 * OAuth 로그인 후 인증코드를 넘겨받는 api
	 * 첫 로그인인 경우 회원가입
	 *
	 * @param provider
	 * @param code
	 * @return
	 */
	@GetMapping("/oauth/{provider}")
	public ResponseEntity OAuth2Login(@PathVariable String provider, @RequestParam String code) throws
		URISyntaxException {
		OAuthDto.TokenDto tokenDto = oAuth2UserService.login(provider, code);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenDto.getRefresh_token())
			.maxAge(7 * 24 * 60 * 60)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();

		log.info(tokenDto.getRefresh_token());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Methods", "*");
		headers.set("Access-Control-Allow-Credentials", "true");
		headers.set("Access-Control-Allow-Origin", "*");
		headers.set("Access-Control-Allow-Headers", "*");
		headers.add("Set-Cookie", cookie.toString());
		headers.add("Authorization", "Bearer " + tokenDto.getAccess_token());

		return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
	}

}
