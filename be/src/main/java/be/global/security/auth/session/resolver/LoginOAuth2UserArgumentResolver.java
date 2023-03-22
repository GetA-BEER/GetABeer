// package be.global.security.auth.session.resolver;
//
// import javax.servlet.http.HttpSession;
//
// import org.springframework.core.MethodParameter;
// import org.springframework.stereotype.Component;
// import org.springframework.web.bind.support.WebDataBinderFactory;
// import org.springframework.web.context.request.NativeWebRequest;
// import org.springframework.web.method.support.HandlerMethodArgumentResolver;
// import org.springframework.web.method.support.ModelAndViewContainer;
//
// import be.global.security.auth.session.annotation.LoginOAuth2User;
// import be.global.security.auth.session.user.SessionUser;
// import lombok.RequiredArgsConstructor;
//
// @Component
// @RequiredArgsConstructor
// public class LoginOAuth2UserArgumentResolver implements HandlerMethodArgumentResolver {
//
// 	private final HttpSession httpSession;
//
// 	@Override
// 	public boolean supportsParameter(MethodParameter parameter) {
// 		boolean isLoginUserAnnotation = parameter
// 			.getParameterAnnotation(LoginOAuth2User.class) != null;
// 		boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
// 		return isLoginUserAnnotation && isUserClass;
// 	}
//
// 	@Override
// 	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
// 		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
// 		return httpSession.getAttribute("user");
// 	}
// }
