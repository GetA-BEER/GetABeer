package be.global.security.auth.oauth.strategy.userinfo;

import java.util.Map;

public interface OAuth2UserInfo {
	Map<String, Object> getAttributes();
	String getProviderId();
	String getProvider();
	String getImageUrl();
	String getEmail();
	String getName();
}
