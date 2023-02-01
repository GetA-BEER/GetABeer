package be.global.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry
			.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*")
			.exposedHeaders("Authorization")
			.allowCredentials(true)
			.maxAge(3600);
	}
}
