package be.global.security.auth.refresh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.domain.user.service.UserService;
import be.global.security.auth.refresh.service.RefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RefreshTokenController {
	private final UserService userService;
	private final RefreshService refreshService;

	/**
	 * 토큰 재발급
	 */
	@PostMapping("/refresh")
	public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
		refreshService.refreshToken(request, response, userService.getLoginUser());

		return ResponseEntity.ok("Access Token reissue Success.");
	}
}
