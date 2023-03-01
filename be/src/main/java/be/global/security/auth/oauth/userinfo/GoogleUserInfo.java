package be.global.security.auth.oauth.userinfo;

import java.util.Map;
import java.util.UUID;

public class GoogleUserInfo implements OAuth2UserInfo {
	private Map<String, Object> attributes;

	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getProviderId() {
		return attributes.get("sub").toString();
	}

	@Override
	public String getProvider() {
		return "google";
	}

	@Override
	public String getEmail() {
		return attributes.get("email").toString();
	}

	@Override
	public String getNickname() {
		return attributes.get("name").toString() + UUID.randomUUID().toString().substring(0, 5);
	}

	@Override
	public String getImageUrl() {
		return attributes.get("picture").toString();
	}
}
