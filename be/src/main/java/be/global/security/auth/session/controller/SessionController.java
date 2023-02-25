package be.global.security.auth.session.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.global.security.auth.oauth.handler.OAuth2SuccessHandler;
import be.global.security.auth.session.annotation.LoginOAuth2User;
import be.global.security.auth.session.user.SessionUser;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SessionController {

	// private final OAuth2SuccessHandler oAuth2SuccessHandler;
	//
	// @GetMapping("/session")
	// public void getHeader(HttpServletRequest request, HttpServletResponse response,
	// 	@LoginOAuth2User SessionUser sessionUser) throws IOException {
	// 	oAuth2SuccessHandler.redirect(request, response, sessionUser);
	// }
}
