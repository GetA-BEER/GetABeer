package be.global.security.auth.refreshToken.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
//@RedisHash(value = "refreshToken")
public class RefreshToken {
	private String email;
	@Id
	private String tokenValue;

	@Builder
	public RefreshToken(String email, String tokenValue) {
		this.email = email;
		this.tokenValue = tokenValue;
	}
}
