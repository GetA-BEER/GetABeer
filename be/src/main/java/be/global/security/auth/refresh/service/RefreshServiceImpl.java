package be.global.security.auth.refresh.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import be.domain.user.entity.User;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.security.auth.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService {

	private final JwtTokenizer jwtTokenizer;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void refreshToken(HttpServletRequest request, HttpServletResponse response, User user) {

		String[] cookies = request.getHeader("Cookie").split(";");
		Stream<String> stream = Arrays.stream(cookies)
			.map(cookie -> cookie.replace(" ", ""))
			.filter(c -> c.startsWith("refreshToken"));
		String value = stream.reduce((first, second) -> second)
			.map(v -> v.replace("refreshToken=", ""))
			.orElse(null);

		if (!Objects.equals(redisTemplate.opsForValue().get(user.getEmail()), value)) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		redisTemplate.delete(user.getEmail());

		String accessToken = jwtTokenizer.delegateAccessToken(user);
		String refreshToken = jwtTokenizer.delegateRefreshToken(user);

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

		response.setHeader("Set-Cookie", cookie.toString());
		response.setHeader("Authorization", "Bearer " + accessToken);
	}
}
