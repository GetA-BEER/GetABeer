package be.global.security.auth.filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.global.security.auth.cookieManager.CookieManager;
import be.global.security.auth.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenizer jwtTokenizer;
	private final UserMapper userMapper;
	private final CookieManager cookieManager;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	@SneakyThrows
	public Authentication attemptAuthentication(
		HttpServletRequest request, HttpServletResponse response) {

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

		String accessToken = jwtTokenizer.delegateAccessToken(user);
		response.setHeader("Authorization", "Bearer " + accessToken);

		String refreshToken = jwtTokenizer.delegateRefreshToken(user);
		jwtTokenizer.addRefreshToken(user.getEmail(), refreshToken);

		if (Boolean.TRUE.equals(redisTemplate.hasKey(user.getEmail()))) {
			redisTemplate.delete(user.getEmail());
		}
		redisTemplate.opsForValue()
			.set(user.getEmail(), refreshToken, 168 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);

		ResponseCookie cookie = cookieManager.createCookie("refreshToken", refreshToken);
		response.setHeader("Set-Cookie", cookie.toString());

		UserDto.LoginResponse responseDto = userMapper.userToLoginResponse(user);
		String json = new Gson().toJson(responseDto);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);

		this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
	}

}
