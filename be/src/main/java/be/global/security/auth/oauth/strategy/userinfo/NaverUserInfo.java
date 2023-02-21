package be.global.security.auth.oauth.strategy.userinfo;

import java.io.Serializable;
import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo, Serializable {
	private Map<String, Object> attributes;

	public NaverUserInfo(Map<String, Object> attributes) {
		this.attributes = (Map<String, Object>) attributes.get("response");
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getProviderId() {
		return getAttributes().get("id").toString();
	}

	@Override
	public String getProvider() {
		return "naver";
	}

	@Override
	public String getImageUrl() {
		return getAttributes().get("profile_image").toString();
	}

	@Override
	public String getEmail() {
		return getAttributes().get("email").toString();
	}

	@Override
	public String getName() {
		return getAttributes().get("nickname").toString();
	}
}
