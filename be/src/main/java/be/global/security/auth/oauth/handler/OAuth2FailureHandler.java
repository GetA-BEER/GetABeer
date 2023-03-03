// package be.global.security.auth.oauth.handler;
//
// import java.io.IOException;
//
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.web.authentication.AuthenticationFailureHandler;
// import org.springframework.stereotype.Component;
//
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Component
// public class OAuth2FailureHandler implements AuthenticationFailureHandler {
// 	@Override
// 	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
// 		AuthenticationException exception) throws IOException, ServletException {
// 		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
// 		response.getWriter().write("소셜 로그인에 실패하셨습니다.");
// 		log.info("# 소셜로그인 실패 : {}", exception.getMessage());
// 	}
// }
