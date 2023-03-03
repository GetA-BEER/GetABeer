package be.global.security.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import be.global.aop.DiscordWebhook;
import be.global.exception.ErrorResponder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		Exception exception = (Exception)request.getAttribute("exception");
		ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);

		logExceptionMessage(authException, exception);
		// sendErrorToDiscord(authException, exception);
	}

	private void logExceptionMessage(AuthenticationException authException, Exception exception) {

		String message = exception != null ? exception.getMessage() : authException.getMessage();
		log.warn("Unauthorized error happened: {}", message);
	}

	private static void sendErrorToDiscord(AuthenticationException authException, Exception exception) throws IOException {
		String message = exception != null ? exception.getMessage() : authException.getMessage();
		DiscordWebhook webhook = new DiscordWebhook();
		webhook.setContent(message);
		webhook.execute();
	}
}
