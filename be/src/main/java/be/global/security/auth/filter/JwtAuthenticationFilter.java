package be.global.security.auth.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.global.security.auth.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenizer jwtTokenizer;

	@SneakyThrows
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) {

		ObjectMapper objectMapper = new ObjectMapper();
		UserDto.Login login = objectMapper.readValue(request.getInputStream(), UserDto.Login.class);

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain chain,
											Authentication authResult) throws ServletException, IOException {

		User user = (User)authResult.getPrincipal();

		String accessToken = delegateAccessToken(user);
		String refreshToken = delegateRefreshToken(user);
		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.maxAge(7 * 24 * 60 * 60)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();

		response.setHeader("Set-Cookie", cookie.toString());
		response.setHeader("Authorization", "Bearer " + accessToken);
		response.addIntHeader("id", user.getId().intValue());

		this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
	}

	private String delegateAccessToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", user.getEmail());
		claims.put("roles", user.getRoles());
		claims.put("provider", user.getProvider());

		String subject = user.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		return jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
	}

	private String delegateRefreshToken(User user) {
		String subject = user.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		return jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
	}
}
