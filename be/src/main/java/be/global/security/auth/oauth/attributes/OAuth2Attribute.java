package be.global.security.auth.oauth.attributes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import be.domain.mail.controller.MailController;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.ProviderType;
import be.domain.user.entity.enums.UserStatus;
import be.domain.user.repository.UserRepository;
import be.domain.user.service.UserService;
import be.global.security.auth.oauth.userinfo.GoogleUserInfo;
import be.global.security.auth.oauth.userinfo.KakaoUserInfo;
import be.global.security.auth.oauth.userinfo.NaverUserInfo;
import be.global.security.auth.oauth.userinfo.OAuth2UserInfo;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class OAuth2Attribute {

	private String usernameAttributeName; // OAuth2 진행 시 키값
	private OAuth2UserInfo oAuth2UserInfo; // 소셜 별 유저 정보

	@Builder
	public OAuth2Attribute(String usernameAttributeName, OAuth2UserInfo oAuth2UserInfo) {
		this.usernameAttributeName = usernameAttributeName;
		this.oAuth2UserInfo = oAuth2UserInfo;
	}

	/**
	 * provider 에 따른 OAuth2Attribute 객체 리턴
	 */
	public static OAuth2Attribute of(ProviderType providerType,
		String usernameAttributeName, Map<String, Object> attributes) {

		if (providerType == ProviderType.KAKAO) {
			return ofKakao(usernameAttributeName, attributes);
		}
		if (providerType == ProviderType.NAVER) {
			return ofNaver(usernameAttributeName, attributes);
		}

		return ofGoogle(usernameAttributeName, attributes);
	}

	private static OAuth2Attribute ofKakao(String usernameAttributeName, Map<String, Object> attributes) {
		return OAuth2Attribute.builder()
			.usernameAttributeName(usernameAttributeName)
			.oAuth2UserInfo(new KakaoUserInfo(attributes))
			.build();
	}

	private static OAuth2Attribute ofNaver(String usernameAttributeName, Map<String, Object> attributes) {
		return OAuth2Attribute.builder()
			.usernameAttributeName(usernameAttributeName)
			.oAuth2UserInfo(new NaverUserInfo(attributes))
			.build();
	}

	private static OAuth2Attribute ofGoogle(String usernameAttributeName, Map<String, Object> attributes) {
		return OAuth2Attribute.builder()
			.usernameAttributeName(usernameAttributeName)
			.oAuth2UserInfo(new GoogleUserInfo(attributes))
			.build();
	}

	/**
	 * of 메서드로 OAuth2Attributes 객체 생성 -> OAuth2UserInfo에 provider 별 유저정보 담고있음
	 */
	public User toEntity(ProviderType providerType, OAuth2UserInfo oAuth2UserInfo, String password, List<String> roles) {

		String email = oAuth2UserInfo.getEmail();

		 User user =User.builder()
			.email(email)
			.nickname(oAuth2UserInfo.getNickname())
			.password(password)
			.imageUrl(oAuth2UserInfo.getImageUrl())
			.roles(roles)
			.status(UserStatus.ACTIVE_USER.getStatus())
			.provider(String.valueOf(providerType))
			.build();

		user.randomProfileImage(user.getImageUrl());
		return user;
	}
}
