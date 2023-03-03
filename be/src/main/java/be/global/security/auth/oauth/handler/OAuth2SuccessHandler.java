package be.global.security.auth.oauth.handler;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import be.domain.user.entity.User;
import be.domain.user.repository.UserRepository;
import be.global.security.auth.jwt.JwtTokenizer;
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
}
