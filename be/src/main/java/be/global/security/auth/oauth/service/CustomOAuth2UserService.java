package be.global.security.auth.oauth.service;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import be.domain.mail.controller.MailController;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.ProviderType;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.auth.oauth.CustomOAuth2User;
import be.global.security.auth.oauth.attributes.OAuth2Attribute;
import be.global.security.auth.oauth.dto.OAuthDto;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final UserRepository userRepository;
	private final MailController mailController;
	private final PasswordEncoder passwordEncoder;
	private final CustomAuthorityUtils authorityUtils;
	private final InMemoryClientRegistrationRepository inMemoryRepository; // application-oauth properties 정보를 담고있다

	public OAuth2User login(String providerName, String code) throws OAuth2AuthenticationException {

		log.info("# 프론트에서 코드 받아가꼬 소셜로그인 요청 드가자~");

		// yml 에 저장된 데이터를 가져와 소셜 서버로 데이터를 가져옴
		ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
		OAuthDto.TokenDto tokenDto = getAuthorization(code, provider); // 인증코드 -> 사용자 정보 교환

		/**
		 * yml 추출한 clientRsgistration 에서 registrationId 로 provider type 저장
		 */
		String registrationId = provider.getRegistrationId();
		ProviderType providerType = getProviderType(registrationId);
		String userNameAttributeName = provider.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		Map<String, Object> attributes = getUserAttributes(provider, tokenDto);

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

	/**
	 * 서버에 토큰을 받아오는 메서드
	 */
	private OAuthDto.TokenDto getAuthorization(String code, ClientRegistration provider) {

		// 소셜 서버 요청 파라미터
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("code", code);
		parameters.add("client_id", provider.getClientId());
		parameters.add("client_secret", provider.getClientSecret());
		parameters.add("redirect_uri", provider.getRedirectUri());
		parameters.add("grant_type", "authorization_code");

		// 소셜 서버 요청 헤더
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

		RestTemplate restTemplate = new RestTemplate();
		// log.info(provider.getRedirectUri());
		OAuthDto.TokenDto tokenDto = restTemplate.postForObject(provider.getProviderDetails().getTokenUri(),
			requestEntity, OAuthDto.TokenDto.class);

		return tokenDto;
	}

	/**
	 * attributes 메서드
	 */
	private Map<String, Object> getUserAttributes(ClientRegistration provider, OAuthDto.TokenDto tokenDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + tokenDto.getAccess_token());

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		Map<String, Object> body = restTemplate.exchange(provider.getProviderDetails().getUserInfoEndpoint().getUri(),
			HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
			}).getBody();

		return body;
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

		if (userRepository.findByEmail(attributes.getOAuth2UserInfo().getEmail()).isPresent()) {
			if (findUser == null) {
				throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
			}
		} else {
			if (findUser == null) {
				return saveUser(attributes, providerType);
			}
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

	// loadUser -> 인증코드로 사용자 정보 받아오는 과정
	// @Override
	// public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	// 	log.info("# 소셜로그인 요청 드가자~");
	//
	// 	/**
	// 	 * DefaultOAuth2UserService 객체 생성 -> loadUser(userRequest) -> DefaultOAuth2User 객체를 생성 후 반환
	// 	 * loadUser() : 소셜 로그인 사용자 정보 제공 URI로 요청 -> 사용자 정보 get -> DefaultOAuth2User 생성 후 반환
	// 	 */
	// 	OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
	// 	OAuth2User oAuth2User = delegate.loadUser(userRequest);
	//
	// 	/**
	// 	 * userRequest 추출한 registrationId 로 provider type 저장
	// 	 */
	// 	String registrationId = userRequest.getClientRegistration().getRegistrationId();
	// 	ProviderType providerType = getProviderType(registrationId);
	// 	String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
	// 		.getUserNameAttributeName();
	// 	Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인 API 제공 userInfo
	//
	// 	OAuth2Attribute extractAttribute = OAuth2Attribute.of(providerType, userNameAttributeName, attributes);
	//
	// 	User createdUser = getUser(extractAttribute, providerType);
	//
	// 	// DefaultOAuth2User 구현한 CustomOAuth2User 객체 생성 후 반환
	// 	return new CustomOAuth2User(
	// 		Collections.singleton(new SimpleGrantedAuthority(createdUser.getRoles().get(0))),
	// 		attributes,
	// 		extractAttribute.getUsernameAttributeName(),
	// 		createdUser.getEmail(),
	// 		createdUser.getRoles().get(0)
	// 	);
	// }
}