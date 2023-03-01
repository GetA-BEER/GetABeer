package be.global.security.auth.oauth.userinfo;

import java.util.Map;
import java.util.UUID;

public class KakaoUserInfo implements OAuth2UserInfo {
	private Map<String, Object> attributes;

	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getProviderId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getEmail() {
		return getAccount().get("email").toString();
	}

	@Override
	public String getNickname() {
		return getProfile().get("nickname").toString() + UUID.randomUUID().toString().substring(0, 5);
	}

	@Override
	public String getImageUrl() {
		return getProfile().get("profile_image_url").toString();
	}

	public Map<String, Object> getAccount(){
		return(Map<String, Object>) attributes.get("kakao_account");
	}

	public Map<String, Object> getProfile() {
		return (Map<String, Object>) getAccount().get("profile");
	}
}
