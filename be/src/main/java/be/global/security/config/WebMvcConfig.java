package be.global.security.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import be.global.security.auth.session.resolver.LoginOAuth2UserArgumentResolver;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoginOAuth2UserArgumentResolver loginOAuth2UserArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginOAuth2UserArgumentResolver);
	}

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry
			.addMapping("/**")
			// .allowedOrigins("http://localhost:8080", "http://localhost:3000",
			// 	"https://www.getabeer.co.kr", "https://getabeer.co.kr", "https://server.getabeer.co.kr")
			.allowedOriginPatterns("*")
			.allowedMethods("*")
			.allowedHeaders("*")
			.exposedHeaders("Authorization")
			.allowCredentials(true)
			.maxAge(3600);
	}
}
