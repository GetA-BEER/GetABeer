package be.global.security.auth.refreshToken.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.security.auth.cookieManager.CookieManager;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.auth.refreshToken.entity.RefreshToken;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RefreshTokenController {
	private final JwtTokenizer jwtTokenizer;
	private final UserService userService;
	private final CookieManager cookieManager;

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String refreshToken = cookieManager.outCookie(request, "refreshToken");

		try {
			jwtTokenizer.verifySignature(refreshToken);

			RefreshToken findRefreshToken = jwtTokenizer.getRefreshToken(refreshToken);
			if (findRefreshToken == null)
				response.sendError(401, "사용할 수 없는 Refresh Token입니다");

			User user = userService.findUserByEmail(findRefreshToken.getEmail());
			String accessToken = jwtTokenizer.delegateAccessToken(user);
			response.setHeader("Authorization", "Bearer " + accessToken);
		} catch (ExpiredJwtException ee) {
			jwtTokenizer.removeRefreshToken(refreshToken);
			response.sendError(401, "Refresh Token이 만료되었습니다");
		}
	}
}
