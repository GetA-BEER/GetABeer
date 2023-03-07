package be.global.security.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import be.domain.user.entity.User;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.security.auth.cookieManager.CookieManager;
import be.global.security.auth.refreshToken.entity.RefreshToken;
import be.global.security.auth.refreshToken.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenizer {
	@Getter
	@Value("${jwt.secret-key}")
	private String secretKey;

	@Getter
	@Value("${jwt.access-token-expiration-minutes}")
	private int accessTokenExpirationMinutes;

	@Getter
	@Value("${jwt.refresh-token-expiration-minutes}")
	private int refreshTokenExpirationMinutes;

	private final RefreshTokenRepository repository;
	private final CookieManager cookieManager;
	private final RedisTemplate redisTemplate;

	public String delegateAccessToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", user.getEmail());
		claims.put("role", user.getRoles());

		String subject = user.getEmail();
		Date expiration = getTokenExpiration(getAccessTokenExpirationMinutes());
		String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

		String accessToken = generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

		return accessToken;
	}

	public String delegateRefreshToken(User user) {
		String subject = user.getEmail();
		Date expiration = getTokenExpiration(getRefreshTokenExpirationMinutes());
		String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

		String refreshToken = generateRefreshToken(subject, expiration, base64EncodedSecretKey);

		return refreshToken;
	}

	public Map<String, Object> verifyJws(HttpServletRequest request) {
		String jws = request.getHeader("Authorization").replace("Bearer ", "");
		String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());
		Map<String, Object> claims = getClaims(jws, base64EncodedSecretKey).getBody();
		return claims;
	}

	public Object getRefreshToken(String email) {
		return redisTemplate.opsForValue().get(email);
	}

	// public RefreshToken getRefreshToken(String tokenValue) {
	// 	return repository.findByTokenValue(tokenValue)
	// 		.orElse(null);
	// }

	public void addRefreshToken(String email, String refreshToken) {

		if (Boolean.TRUE.equals(redisTemplate.hasKey(email))) {
			redisTemplate.delete(email);
		}
		redisTemplate.opsForValue().set(email, refreshToken, 168 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);

		// repository.save(RefreshToken.builder()
		// 	.email(email)
		// 	.tokenValue(jws)
		// 	.build());
	}

	@Transactional
	public void removeRefreshToken(String email) {
		redisTemplate.delete(email);
		// repository.deleteByTokenValue(tokenValue);
	}

	public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		Jws<Claims> claims = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jws);
		return claims;
	}

	public void verifySignature(String jws) {
		String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());
		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jws);
	}

	private String encodeBase64SecretKey(String secretKey) {
		return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	private String generateAccessToken(Map<String, Object> claims,
		String subject,
		Date expiration,
		String base64EncodedSecretKey) {
		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		return Jwts.builder()
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(Calendar.getInstance().getTime())
			.setExpiration(expiration)
			.signWith(key)
			.compact();
	}

	private String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		return Jwts.builder()
			.setSubject(subject)
			.setIssuedAt(Calendar.getInstance().getTime())
			.setExpiration(expiration)
			.signWith(key)
			.compact();
	}

	private Date getTokenExpiration(int expirationMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, expirationMinutes);
		Date expiration = calendar.getTime();

		return expiration;
	}

	private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
		Key key = Keys.hmacShaKeyFor(keyBytes);

		return key;
	}

	public Boolean checkUserWithToken(HttpServletRequest request, String auth) {
		if (request.getHeader("Cookie") == null)
			return false;

		String refreshToken = cookieManager.outCookie(request, "refreshToken");
		if (refreshToken == null)
			return false;

		try {
			verifySignature(refreshToken);
		} catch (ExpiredJwtException ee) {
			removeRefreshToken(refreshToken);
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED); // 토큰 만료
		}

		if (getRefreshToken(refreshToken) == null || auth == null)
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED); // 쿠키나 auth가 없는 경우

		return true;
	}
}