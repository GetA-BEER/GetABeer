package be.global.security.auth.oauth.userinfo;

import java.util.Map;

public interface OAuth2UserInfo {
	Map<String, Object> getAttributes();

	String getProviderId(); // 소셜 식별값 구글 : sub / 카카오,네이버 : id

	String getProvider();

	String getEmail();

	String getNickname(); // 중복을 사전에 막아버릴꺼에여,,, 아무도 날 말리지모태,,

	String getImageUrl();
}
