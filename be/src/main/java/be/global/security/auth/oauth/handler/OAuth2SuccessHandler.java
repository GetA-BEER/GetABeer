package be.global.security.auth.oauth.handler;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

	@Override

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		String authorizedClientRegistrationId = ((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId();
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

		String nickname;
		if (oAuth2User.getAttributes().get("properties") == null) {
			if (authorizedClientRegistrationId.equals("google")) {
				nickname = oAuth2User.getAttributes().get("name").toString();
			} else {
				nickname = oAuth2User.getAttributes().get("nickname").toString();
			}
		} else {
			nickname = ((Map)oAuth2User.getAttributes().get("properties")).get("nickname").toString();
		}

		User user = userRepository.findByNickname(nickname);

		redirect(request, response, user.getEmail(), user.getProvider(), user.getRoles());
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response, String email, String provider,
		List<String> authorities) throws IOException {

		String accessToken = jwtTokenizer.delegateAccessToken(email, authorities, provider);
		String refreshToken = jwtTokenizer.delegateRefreshToken(email);

		if (Boolean.TRUE.equals(redisTemplate.hasKey(email))) {
			redisTemplate.delete(email);
		}
		redisTemplate.opsForValue()
			.set(email, refreshToken, 168 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.maxAge(7 * 24 * 60 * 60)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();

		response.setHeader("Set-Cookie", cookie.toString());
		response.setHeader("Authorization", "Bearer " + accessToken);

		String uri = createURI().toString();
		getRedirectStrategy().sendRedirect(request, response, uri);
	}

	private URI createURI() {

		return UriComponentsBuilder
			.newInstance()
			// .scheme("https")
			// .host("www.getabeer.co.kr")
			.scheme("http")
			.host("localhost")
			.port(3000)
			.build()
			.toUri();
	}

}
