package be.global.security.config;

import static org.springframework.security.config.Customizer.*;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
import be.global.security.auth.oauth.RedirectController;
import be.global.security.auth.oauth.handler.OAuth2SuccessHandler;
import be.global.security.auth.oauth.service.CustomOAuth2UserService;
import be.global.security.auth.session.SecuritySessionExpiredStrategy;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
	private final UserMapper userMapper;
	private final HttpSession httpSession;
	private final JwtTokenizer jwtTokenizer;
	private final UserRepository userRepository;
	private final MailController mailController;
	private final RedirectController redirectController;
	private final CustomAuthorityUtils customAuthorityUtils;
	private final RedisTemplate<String, String> redisTemplate;
	// private final SecuritySessionExpiredStrategy securitySessionExpiredStrategy;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.headers().frameOptions().sameOrigin()

			.and()
			.csrf().disable()
			.cors(withDefaults())
			// .and()
			// .sessionManagement(s ->
			// 	s.maximumSessions(1) // 동일 세션 개수 제한 (중복 로그인 방지)
			// 		.maxSessionsPreventsLogin(false) // 중복 로그인 시 먼저 로그인한 사용자 로그아웃
			// 		.expiredSessionStrategy(securitySessionExpiredStrategy)) // 세션 만료 시 전략
			// .and()
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
			.oauth2Login(oauth2 -> {
				oauth2.authorizationEndpoint().baseUri("/oauth2/authorization");
				oauth2.successHandler(
					new OAuth2SuccessHandler(httpSession, jwtTokenizer, userRepository, redirectController, redisTemplate));
				oauth2.userInfoEndpoint().userService(
					new CustomOAuth2UserService(userRepository, mailController, passwordEncoder(),
						customAuthorityUtils));
			});

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
}
