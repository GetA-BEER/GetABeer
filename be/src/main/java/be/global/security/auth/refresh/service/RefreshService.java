package be.global.security.auth.refresh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.domain.user.entity.User;

public interface RefreshService {

	void refreshToken(HttpServletRequest request, HttpServletResponse response, User user);
}
