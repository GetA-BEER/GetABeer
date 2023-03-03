package be.global.security.auth.oauth.userinfo;

import java.util.Map;
import java.util.UUID;

public class NaverUserInfo implements OAuth2UserInfo {
	private Map<String, Object> attributes;

	public NaverUserInfo(Map<String, Object> attributes) {
		this.attributes = (Map<String, Object>)attributes.get("response");
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getProviderId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getProvider() {
		return "naver";
	}

	@Override
	public String getEmail() {
		return attributes.get("email").toString();
	}

	@Override
	public String getNickname() {
		return attributes.get("nickname").toString() + UUID.randomUUID().toString().substring(0, 5);
	}

	@Override
	public String getImageUrl() {
		return attributes.get("profile_image").toString();
	}
}
