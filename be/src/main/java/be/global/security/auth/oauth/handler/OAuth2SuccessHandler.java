package be.global.security.auth.oauth.handler;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import be.domain.mail.controller.MailController;
import be.domain.mail.dto.MailDto;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.UserStatus;
import be.domain.user.repository.UserRepository;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenizer jwtTokenizer;
	private final UserRepository userRepository;
	private final MailController mailController;
	private final PasswordEncoder passwordEncoder;
	private final CustomAuthorityUtils authorityUtils;
	private final RedisTemplate<String, String> redisTemplate;
	@Override

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		try {
			String authorizedClientRegistrationId = ((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId();
			if (authorizedClientRegistrationId.equals("google")) {
				var oAuth2User = (OAuth2User)authentication.getPrincipal();

				String username = String.valueOf(oAuth2User.getAttributes().get("email"));
				String nickname = String.valueOf(oAuth2User.getAttributes().get("name"));
				String imageUrl = String.valueOf(oAuth2User.getAttributes().get("picture"));

				String provider = "google";

				String uuid = UUID.randomUUID().toString().substring(0, 15);
				String password = passwordEncoder.encode(uuid);

				List<String> authorities = authorityUtils.createRoles(username);

				if (userRepository.findByEmail(username).isEmpty()) {
					sendTempPassword(username, uuid);
					saveUser(nickname, username, password, provider, imageUrl);
				}
				redirect(request, response, username, provider, authorities);
			}

			if (authorizedClientRegistrationId.equals("naver")) {
				var oAuth2User = (OAuth2User)authentication.getPrincipal();

				HashMap userInfo = oAuth2User.getAttribute("response");
				String email = userInfo.get("email").toString();
				String imageUrl = userInfo.get("profile_image").toString();
				String nickname = userInfo.get("nickname").toString();
				List<String> authorities = authorityUtils.createRoles(email);

				String provider = "naver";

				String uuid = UUID.randomUUID().toString().substring(0, 15);
				String password = passwordEncoder.encode(uuid);

				if (userRepository.findByEmail(email).isEmpty()) {
					sendTempPassword(email, uuid);
					saveUser(nickname, email, password, provider, imageUrl);
				}
				redirect(request, response, email, provider, authorities);
			}

			if (authorizedClientRegistrationId.equals("kakao")) {
				var oAuth2User = (OAuth2User)authentication.getPrincipal();

				HashMap userInfo = oAuth2User.getAttribute("properties");

				HashMap account = oAuth2User.getAttribute("kakao_account");
				String email = account.get("email").toString();

				String nickname = userInfo.get("nickname").toString();
				String imageUrl = userInfo.get("profile_image").toString();
				String provider = "kakao";

				String uuid = UUID.randomUUID().toString().substring(0, 15);
				String password = passwordEncoder.encode(uuid);

				List<String> authorities = authorityUtils.createRoles(email);

				if (userRepository.findByEmail(email).isEmpty()) {
					sendTempPassword(email, uuid);
					saveUser(nickname, email, password, provider, imageUrl);
				}

				redirect(request, response, email, provider, authorities);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void saveUser(String nickname, String email, String password, String provider, String imageUrl) {
		User user = User.builder()
			.email(email)
			.nickname(nickname)
			.provider(provider)
			.password(password)
			.imageUrl(imageUrl)
			.build();

		registerUser(user);
	}

	private void registerUser(User user) {
		User saved = User.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.status(UserStatus.ACTIVE_USER.getStatus())
			.provider(user.getProvider().toUpperCase())
			.imageUrl(user.getImageUrl())
			.roles(authorityUtils.createRoles(user.getEmail()))
			.password(passwordEncoder.encode(user.getPassword()))
			.build();

		saved.randomProfileImage(saved.getImageUrl());

		userRepository.save(saved);
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
			.scheme("https")
			.host("server.getabeer.co.kr")
			// .port(8081)
			.build()
			.toUri();
	}

	private void sendTempPassword(String email, String uuid) {
		MailDto.sendPWMail post = MailDto.sendPWMail.builder()
			.email(email)
			.password(uuid)
			.build();

		mailController.sendOAuth2PasswordEmail(post);
	}

}
