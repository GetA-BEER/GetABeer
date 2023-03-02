package be.global.security.auth.oauth.handler;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import be.domain.user.entity.User;
import be.domain.user.repository.UserRepository;
import be.domain.user.service.UserService;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.auth.oauth.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenizer jwtTokenizer;
	private final UserRepository userRepository;
	private final RedisTemplate<String, String> redisTemplate;

	public void handler(String act, String rft, String email) {
		log.info("# 핸들러가 잘 작동하나??? 해야하는데ㅜ");

		try {
			User user = userRepository.findByEmail(email).orElseThrow();
			if (user.getAge() == null) {
				// 첫 소셜 로그인 시 회원정보입력으로 기기
				String accessToken = jwtTokenizer.delegateAccessToken(
					user.getEmail(),
					user.getRoles(),
					user.getProvider());

				if (Boolean.TRUE.equals(redisTemplate.hasKey(user.getEmail()))) {
					redisTemplate.delete(user.getEmail());
				}
				redisTemplate.opsForValue()
					.set(user.getEmail(), rft, 168 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);

				ResponseCookie cookie = ResponseCookie.from("refreshToken", rft)
					.maxAge(7 * 24 * 60 * 60)
					.path("/")
					.secure(true)
					.sameSite("None")
					.httpOnly(true)
					.build();

				HttpHeaders headers = new HttpHeaders();
				headers.set("Access-Control-Allow-Methods", "*");
				headers.set("Access-Control-Allow-Credentials", "true");
				headers.set("Access-Control-Allow-Origin", "*");
				headers.set("Access-Control-Allow-Headers", "*");
				headers.add("Set-Cookie", cookie.toString());
				headers.add("Authorization", "Bearer " + accessToken);
				// headers.add("id", user.getId().intValue());
				headers.setLocation(URI.create("https://getabeer.co.kr/signup/information")); // 회원정보입력으로 가버려..
				// headers.setLocation(URI.create("http://localhost:3000/signup/information")); // 회원정보입력으로 가버려..

			} else {
				loginSuccess(user);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private void loginSuccess(User user) throws IOException {
		String accessToken = jwtTokenizer.delegateAccessToken(user.getEmail(), user.getRoles(), user.getProvider());
		String refreshToken = jwtTokenizer.delegateRefreshToken(user.getEmail());

		if (Boolean.TRUE.equals(redisTemplate.hasKey(user.getEmail()))) {
			redisTemplate.delete(user.getEmail());
		}
		redisTemplate.opsForValue()
			.set(user.getEmail(), refreshToken, 168 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.maxAge(7 * 24 * 60 * 60)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Methods", "*");
		headers.set("Access-Control-Allow-Credentials", "true");
		headers.set("Access-Control-Allow-Origin", "*");
		headers.set("Access-Control-Allow-Headers", "*");
		headers.add("Set-Cookie", cookie.toString());
		headers.add("Authorization", "Bearer " + accessToken);
		headers.setLocation(createURI());
	}

	private URI createURI() {
		return UriComponentsBuilder
			.newInstance()
			.scheme("https")
			// .scheme("http")
			.host("www.getabeer.co.kr")
			// .path("/signup/information")
			// .host("localhost")
			// .port(3000)
			.build()
			.toUri();
	}

	// @Override
	// public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	// 	Authentication authentication) throws IOException, ServletException {
	//
	// 	log.info("소셜 로그인 성공이어야함 제발");
	//
	// 	try {
	// 		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
	//
	// 		// 첫 소셜 로그인 시 회원정보입력으로 기기
	// 		User user = userRepository.findByEmail(oAuth2User.getEmail()).orElseThrow();
	// 		if (user.getAge() == null) {
	//
	// 			String accessToken = jwtTokenizer.delegateAccessToken(user.getEmail(), user.getRoles(),
	// 				user.getProvider());
	//
	// 			String refreshToken = jwtTokenizer.delegateRefreshToken(user.getEmail());
	// 			if (Boolean.TRUE.equals(redisTemplate.hasKey(user.getEmail()))) {
	// 				redisTemplate.delete(user.getEmail());
	// 			}
	// 			redisTemplate.opsForValue()
	// 				.set(user.getEmail(), refreshToken, 168 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);
	//
	// 			ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
	// 				.maxAge(7 * 24 * 60 * 60)
	// 				.path("/")
	// 				.secure(true)
	// 				.sameSite("None")
	// 				.httpOnly(true)
	// 				.build();
	//
	// 			response.setHeader("Access-Control-Allow-Methods", "*");
	// 			response.setHeader("Access-Control-Allow-Credentials", "true");
	// 			response.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));
	// 			response.setHeader("Access-Control-Allow-Headers", "*");
	// 			response.addHeader("Set-Cookie", cookie.toString());
	// 			response.addHeader("Authorization", "Bearer " + accessToken);
	// 			response.addIntHeader("id", user.getId().intValue());
	// 			// response.sendRedirect("http://localhost:3000/signup/information"); // 회원정보입력으로 가버려..
	//
	// 			getRedirectStrategy().sendRedirect(request, response, "https://getabeer.co.kr/signup/information");
	//
	// 		} else {
	// 			loginSuccess(request, response, user);
	// 		}
	// 	} catch (Exception e) {
	// 		log.error(e.getMessage());
	// 	}
	// }
	//
	// private void loginSuccess(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
	// 	String accessToken = jwtTokenizer.delegateAccessToken(user.getEmail(), user.getRoles(), user.getProvider());
	// 	String refreshToken = jwtTokenizer.delegateRefreshToken(user.getEmail());
	//
	// 	if (Boolean.TRUE.equals(redisTemplate.hasKey(user.getEmail()))) {
	// 		redisTemplate.delete(user.getEmail());
	// 	}
	// 	redisTemplate.opsForValue()
	// 		.set(user.getEmail(), refreshToken, 168 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);
	//
	// 	ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
	// 		.maxAge(7 * 24 * 60 * 60)
	// 		.path("/")
	// 		.secure(true)
	// 		.sameSite("None")
	// 		.httpOnly(true)
	// 		.build();
	//
	// 	response.setHeader("Access-Control-Allow-Methods", "*");
	// 	response.setHeader("Access-Control-Allow-Credentials", "true");
	// 	response.setHeader("Access-Control-Allow-Origin", "*");
	// 	response.setHeader("Access-Control-Allow-Headers", "*");
	// 	response.setHeader("Authorization", "Bearer " + accessToken);
	// 	response.setHeader("Set-Cookie", cookie.toString());
	// 	response.addIntHeader("id", user.getId().intValue());
	//
	// 	String uri = createURI().toString();
	// 	getRedirectStrategy().sendRedirect(request, response, uri);
	// }
	//
	// private URI createURI() {
	// 	return UriComponentsBuilder
	// 		.newInstance()
	// 		.scheme("https")
	// 		// .scheme("http")
	// 		.host("www.getabeer.co.kr")
	// 		// .path("/signup/information")
	// 		// .host("localhost")
	// 		// .port(3000)
	// 		.build()
	// 		.toUri();
	// }
}
