package be.global.security.config;

import static org.springframework.security.config.Customizer.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import be.global.security.auth.filter.JwtAuthenticationFilter;
import be.global.security.auth.filter.JwtVerificationFilter;
import be.global.security.auth.handler.UserAccessDeniedHandler;
import be.global.security.auth.handler.UserAuthenticationEntryPoint;
import be.global.security.auth.handler.UserAuthenticationFailureHandler;
import be.global.security.auth.handler.UserAuthenticationSuccessHandler;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenizer jwtTokenizer;
	private final CustomAuthorityUtils customAuthorityUtils;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.headers().frameOptions().sameOrigin()

			.and()
			.csrf().disable()
			.cors(withDefaults())
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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
				.anyRequest().permitAll());

		return http.build();
	}

	public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

		@Override
		public void configure(HttpSecurity builder) throws Exception {

			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

			JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
			jwtAuthenticationFilter.setFilterProcessesUrl("/login");
			jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

			JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, customAuthorityUtils);

			builder
				.addFilter(jwtAuthenticationFilter)
				.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
		}
	}
}
