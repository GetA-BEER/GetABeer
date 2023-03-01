package be.global.security.config;

import static org.springframework.security.config.Customizer.*;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import be.domain.mail.controller.MailController;
import be.domain.user.mapper.UserMapper;
import be.domain.user.repository.UserRepository;
import be.global.security.auth.filter.JwtAuthenticationFilter;
import be.global.security.auth.filter.JwtVerificationFilter;
import be.global.security.auth.handler.UserAccessDeniedHandler;
import be.global.security.auth.handler.UserAuthenticationEntryPoint;
import be.global.security.auth.handler.UserAuthenticationFailureHandler;
import be.global.security.auth.handler.UserAuthenticationSuccessHandler;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.auth.oauth.handler.OAuth2FailureHandler;
import be.global.security.auth.oauth.handler.OAuth2SuccessHandler;
import be.global.security.auth.oauth.service.CustomOAuth2UserService;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
	private final UserMapper userMapper;
	private final JwtTokenizer jwtTokenizer;
	private final UserRepository userRepository;
	private final MailController mailController;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	private final OAuth2FailureHandler oAuth2FailureHandler;
	private final CustomAuthorityUtils customAuthorityUtils;
	private final RedisTemplate<String, String> redisTemplate;
	// private final SecuritySessionExpiredStrategy securitySessionExpiredStrategy;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.headers().frameOptions().sameOrigin()

			.and()
			.csrf().disable()
			.cors().configurationSource(corsConfigurationSource())
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			// .and()
			// .sessionManagement(s ->
			// 	s.maximumSessions(1) // 동일 세션 개수 제한 (중복 로그인 방지)
			// 		.maxSessionsPreventsLogin(false) // 중복 로그인 시 먼저 로그인한 사용자 로그아웃
			// 		.expiredSessionStrategy(securitySessionExpiredStrategy)) // 세션 만료 시 전략
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.exceptionHandling()
			.authenticationEntryPoint(new UserAuthenticationEntryPoint())
			.accessDeniedHandler(new UserAccessDeniedHandler())
			.and()
			.apply(new CustomFilterConfigurer())
			.and()
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().permitAll())
			.oauth2Login()
			.authorizationEndpoint() // front -> back
			.baseUri("/oauth2/authorization")
			.and()
			.redirectionEndpoint()
			.baseUri("/*/oauth2/code/*")
			.and()
			.userInfoEndpoint()
			.userService(new CustomOAuth2UserService(userRepository, mailController, passwordEncoder(), customAuthorityUtils))
			.and()
			.successHandler(oAuth2SuccessHandler)
			.failureHandler(oAuth2FailureHandler);

		return http.build();
	}

	public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
		@Override
		public void configure(HttpSecurity builder) throws Exception {
			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

			JwtAuthenticationFilter jwtAuthenticationFilter =
				new JwtAuthenticationFilter(userMapper, jwtTokenizer, authenticationManager, redisTemplate);

			jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");
			jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

			JwtVerificationFilter jwtVerificationFilter =
				new JwtVerificationFilter(jwtTokenizer, customAuthorityUtils, redisTemplate);

			builder
				.addFilter(jwtAuthenticationFilter)
				.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("https://www.getabeer.co.kr");
		configuration.addAllowedOrigin("https://getabeer.co.kr");
		configuration.addAllowedOrigin("https://server.getabeer.co.kr");
		configuration.addAllowedOrigin("http://localhost:3000");
		configuration.addAllowedOrigin("http://localhost:8080");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
		configuration.setMaxAge(3600L);
		configuration.addExposedHeader("Authorization");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
