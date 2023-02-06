package be.global.security.auth.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

	private final JwtTokenizer jwtTokenizer;
	private final CustomAuthorityUtils customAuthorityUtils;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		try {
			Map<String, Object> claims = jwtTokenizer.verifyJws(request);
			setAuthenticationToConText(claims);
		} catch (SignatureException se) {
			request.setAttribute("exception", se);
		} catch (ExpiredJwtException ee) {
			request.setAttribute("exception", ee);
			response.sendError(401, "Access Token Expiration");
		} catch (Exception e) {
			request.setAttribute("exception", e);
		}

		String logoutToken = request.getHeader("Authorization");
		if (null != redisTemplate.opsForValue().get(logoutToken)) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String authorization = request.getHeader("Authorization");

		return authorization == null || !authorization.startsWith("Bearer");
	}

	private void setAuthenticationToConText(Map<String, Object> claims) {
		String email = (String)claims.get("email");
		List<GrantedAuthority> authorities = customAuthorityUtils.createAuthorities((List)claims.get("roles"));
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
