package be.global.security.auth.oauth.service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.mail.controller.MailController;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.ProviderType;
import be.domain.user.repository.UserRepository;
import be.global.security.auth.oauth.CustomOAuth2User;
import be.global.security.auth.oauth.attributes.OAuth2Attribute;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserRepository userRepository;
	private final MailController mailController;
	private final PasswordEncoder passwordEncoder;
	private final CustomAuthorityUtils authorityUtils;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("# 소셜로그인 요청 드가자~");

		/**
		 * DefaultOAuth2UserService 객체 생성 -> loadUser(userRequest) -> DefaultOAuth2User 객체를 생성 후 반환
		 * loadUser() : 소셜 로그인 사용자 정보 제공 URI로 요청 -> 사용자 정보 get -> DefaultOAuth2User 생성 후 반환
		 */
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		/**
		 * userRequest 추출한 registrationId 로 provider type 저장
		 */
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		ProviderType providerType = getProviderType(registrationId);
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
			.getUserNameAttributeName();
		Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인 API 제공 userInfo

		OAuth2Attribute extractAttribute = OAuth2Attribute.of(providerType, userNameAttributeName, attributes);

		User createdUser = getUser(extractAttribute, providerType);

		// DefaultOAuth2User 구현한 CustomOAuth2User 객체 생성 후 반환
		return new CustomOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(createdUser.getRoles().get(0))),
			attributes,
			extractAttribute.getUsernameAttributeName(),
			createdUser.getEmail(),
			createdUser.getRoles().get(0)
		);
	}

	private ProviderType getProviderType(String registrationId) {
		if ("naver".equals(registrationId)) {
			return ProviderType.NAVER;
		}
		if ("kakao".equals(registrationId)) {
			return ProviderType.KAKAO;
		}
		return ProviderType.GOOGLE;
	}

	private User getUser(OAuth2Attribute attributes, ProviderType providerType) {
		User findUser = userRepository.findByEmailAndProvider(attributes.getOAuth2UserInfo().getEmail(),
			providerType.toString()).orElse(null);

		if (findUser == null) {
			return saveUser(attributes, providerType);
		}
		return findUser;
	}

	private User saveUser(OAuth2Attribute attribute, ProviderType providerType) {

		String password = UUID.randomUUID().toString().substring(0, 15);
		mailController.sendOAuth2PasswordEmail(attribute.getOAuth2UserInfo().getEmail(), password);

		User createdUser = attribute.toEntity(providerType, attribute.getOAuth2UserInfo(),
			passwordEncoder.encode(password), authorityUtils.createRoles(attribute.getOAuth2UserInfo().getEmail()));

		return userRepository.save(createdUser);
	}
}