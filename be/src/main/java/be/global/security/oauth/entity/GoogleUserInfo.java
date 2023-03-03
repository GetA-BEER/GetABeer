package be.global.security.oauth.entity;

import java.io.Serializable;
import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo, Serializable {
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
	public String getImageUrl() {
		return attributes.get("picture").toString();
	}

	@Override
	public String getEmail() {
		return attributes.get("email").toString();
	}

	@Override
	public String getName() {
		return attributes.get("name").toString();
	}
}
