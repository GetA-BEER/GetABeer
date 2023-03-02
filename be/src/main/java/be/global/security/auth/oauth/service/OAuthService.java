// package be.global.security.auth.oauth.service;
//
// import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.oauth2.client.registration.ClientRegistration;
// import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.core.OAuth2AccessToken;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMap;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
//
// import be.domain.mail.controller.MailController;
// import be.domain.user.repository.UserRepository;
// import be.global.security.auth.jwt.JwtTokenizer;
// import be.global.security.auth.oauth.dto.OAuthDto;
// import be.global.security.auth.utils.CustomAuthorityUtils;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class OAuthService {
//
// 	private final JwtTokenizer jwtTokenizer;
// 	private final UserRepository userRepository;
// 	private final MailController mailController;
// 	private final PasswordEncoder passwordEncoder;
// 	private final CustomAuthorityUtils authorityUtils;
// 	private final RedisTemplate<String, String> redisTemplate; // 리프레시 토큰 저장 위함
// 	private final CustomOAuth2UserService customOAuth2UserService;
// 	private final InMemoryClientRegistrationRepository inMemoryRepository; // application-oauth properties 정보를 담고있다
//
// 	/**
// 	 * 로그인 및 첫 회원가입 진행 메서드
// 	 *
// 	 * @param providerType
// 	 * @param code
// 	 * @return
// 	 */
// 	@Transactional
// 	public void login(String providerType, String code) {
//
// 		ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerType);
// 		OAuthDto.TokenDto tokenData = getAuthorizationToken(code, provider);
// 		customOAuth2UserService.loadUser(new OAuth2UserRequest(provider,
// 			new OAuth2AccessToken(tokenData.getToken_type(), tokenData.getAccess_token(), tokenData.getIssue_at(),
// 				tokenData.getExpires_in())));

		// User user = getUser(providerType, tokenData, provider);

		// String act = jwtTokenizer.delegateAccessToken(user.getEmail(), user.getRoles(), user.getProvider());
		// String rft = jwtTokenizer.delegateRefreshToken(user.getEmail());
		//
		// if (Boolean.TRUE.equals(redisTemplate.hasKey(user.getEmail()))) {
		// 	redisTemplate.delete(user.getEmail());
		// }
		// redisTemplate.opsForValue()
		// 	.set(user.getEmail(), rft, 168 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);
		//
		// return OAuthDto.OAuthLoginDto.builder()
		// 	.email(user.getEmail())
		// 	.nickname(user.getNickname())
		// 	.imageUrl(user.getImageUrl())
		// 	.accessToken(act)
		// 	.refreshToken(rft)
		// 	.build();
	// }

	// private OAuthDto.TokenDto getAuthorizationToken(String code, ClientRegistration provider) {
	// 	// 카카오 구글 동일, 네이버는.. 확실치않음,,
	// 	MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
	// 	parameters.add("grant_type", "authorization_code");
	// 	parameters.add("client_id", provider.getClientId());
	// 	parameters.add("redirect_uri", provider.getRedirectUri());
	// 	parameters.add("client_secret", provider.getClientSecret());
	// 	parameters.add("code", code);
	//
	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.set("Content-type", "application/x-www-dorm-urlencoded;charset=utf-8");
	//
	// 	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
	//
	// 	RestTemplate restTemplate = new RestTemplate();
	// 	return restTemplate.postForObject(provider.getProviderDetails().getTokenUri(), request,
	// 		OAuthDto.TokenDto.class);
	// }

	// private User getUser(String providerType, OAuthDto.TokenDto tokenDto, ClientRegistration provider) {
	// 	Map<String, Object> attributes = getAttributes(provider, tokenDto);
	//
	// 	OAuth2UserInfo oAuth2UserInfo;
	// 	if (providerType.equals("naver")) {
	// 		oAuth2UserInfo = new NaverUserInfo(attributes);
	// 	}
	// 	if (providerType.equals("kakao")) {
	// 		oAuth2UserInfo = new KakaoUserInfo(attributes);
	// 	}
	// 	oAuth2UserInfo = new GoogleUserInfo(attributes);
	//
	// 	String providerName = oAuth2UserInfo.getProvider();
	// 	String providerId = oAuth2UserInfo.getProviderId();
	// 	String email = oAuth2UserInfo.getEmail();
	// 	String nickname = oAuth2UserInfo.getNickname();
	// 	String imageUrl = oAuth2UserInfo.getImageUrl();
	// 	List<String> authority = authorityUtils.createAuthorities(email).stream().map(GrantedAuthority::getAuthority)
	// 		.collect(
	// 			Collectors.toList());
	//
	// 	if (userRepository.findByEmail(email).isPresent()) {
	// 		if (userRepository.findByEmailAndProvider(email, providerName).isPresent()) {
	// 			return userRepository.findByEmailAndProvider(email, providerName).get();
	// 		} else {
	// 			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
	// 		}
	// 	} else if (userRepository.existsByNickname(nickname)) {
	// 		throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
	// 	}
	//
	// 	String password = UUID.randomUUID().toString().substring(0, 15);
	//
	// 	User user = User.builder()
	// 		.email(email)
	// 		.nickname(nickname)
	// 		.provider(providerName)
	// 		.password(passwordEncoder.encode(password))
	// 		.roles(authority)
	// 		.status(UserStatus.ACTIVE_USER.getStatus())
	// 		.build();
	//
	// 	user.randomProfileImage(imageUrl);
	// 	userRepository.save(user);
	//
	// 	mailController.sendOAuth2PasswordEmail(email, password);
	//
	// 	return user;
	// }
	//
	// private Map<String, Object> getAttributes(ClientRegistration provider, OAuthDto.TokenDto tokenDto) {
	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.set("Authorization", "Bearer " + tokenDto.getAccess_token());
	//
	// 	HttpEntity<Void> request = new HttpEntity<>(headers);
	// 	RestTemplate restTemplate = new RestTemplate();
	//
	// 	return restTemplate.exchange(
	// 		provider.getProviderDetails().getUserInfoEndpoint().getUri(),
	// 		HttpMethod.GET,
	// 		request,
	// 		new ParameterizedTypeReference<Map<String, Object>>() {
	// 		}).getBody();
	// }
// }
