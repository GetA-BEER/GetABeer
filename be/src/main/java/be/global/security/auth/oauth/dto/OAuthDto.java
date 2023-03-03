package be.global.security.auth.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OAuthDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TokenDto {
		private String scope;
		private String token_type;
		private String access_token;
		private String id_token;
		private String refresh_token;
		private int expires_in;
		private int refresh_token_expires_in;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class OAuthLoginDto {
		private String email;
		private String nickname;
		private String imageUrl;
		private String accessToken;
		private String refreshToken;
	}
}
