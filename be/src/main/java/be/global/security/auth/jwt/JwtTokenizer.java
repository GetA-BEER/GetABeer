package be.global.security.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import be.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Component
public class JwtTokenizer {

	@Getter
	@Value("${jwt.secret-key}")
	private String secretKey;

	@Getter
	@Value("${jwt.access-token-expiration-minutes}")
	private Integer accessTokenExpirationMinutes;

	@Getter
	@Value("${jwt.refresh-token-expiration-minutes}")
	private Integer refreshTokenExpirationMinutes;

	public String encodeBase64SecretKey(String secretKey) {
		return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(Map<String, Object> claims,
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

	public String generateRefreshToken(String subject,
		Date expiration,
		String base64EncodedSecretKey) {

		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		return Jwts.builder()
			.setSubject(subject)
			.setIssuedAt(Calendar.getInstance().getTime())
			.setExpiration(expiration)
			.signWith(key)
			.compact();
	}

	private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {

		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jws);
	}

	public void verifySignature(String jws, String base64EncodedSecretKey) {

		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jws);
	}

	public Date getTokenExpiration(int expirationMinutes) {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, expirationMinutes);
		return calendar.getTime();
	}

	public Map<String, Object> verifyJws(HttpServletRequest request) {
		String jws = request.getHeader("Authorization").replace("Bearer ", "");
		String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

		return getClaims(jws, base64EncodedSecretKey).getBody();
	}

	public String delegateAccessToken(String email, List<String> authorities, String provider) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", email);
		claims.put("roles", authorities);
		claims.put("provider", provider);

		Date expiration = getTokenExpiration(getAccessTokenExpirationMinutes());

		String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

		return generateAccessToken(claims, email, expiration, base64EncodedSecretKey);
	}

	public String delegateRefreshToken(String email) {
		Date expiration = getTokenExpiration(getRefreshTokenExpirationMinutes());
		String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

		return generateRefreshToken(email, expiration, base64EncodedSecretKey);
	}
}
