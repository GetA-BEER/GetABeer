package be.global.security.oauth.entity;

import java.io.Serializable;
import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo, Serializable {
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
	public String getImageUrl() {
		return getProfile().get("profile_image_url").toString();
	}

	@Override
	public String getEmail() {
		return getAccount().get("email").toString();
	}

	@Override
	public String getName() {
		return getProfile().get("nickname").toString();
	}

	public Map<String, Object> getAccount() {
		return (Map<String, Object>)attributes.get("kakao_account");
	}

	public Map<String, Object> getProfile() {
		return (Map<String, Object>)getAccount().get("profile");
	}
}
