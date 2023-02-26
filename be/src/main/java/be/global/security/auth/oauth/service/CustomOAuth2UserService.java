package be.global.security.auth.oauth.service;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.mail.controller.MailController;
import be.domain.mail.dto.MailDto;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.UserStatus;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.security.auth.oauth.strategy.userinfo.GoogleUserInfo;
import be.global.security.auth.oauth.strategy.userinfo.KakaoUserInfo;
import be.global.security.auth.oauth.strategy.userinfo.NaverUserInfo;
import be.global.security.auth.oauth.strategy.userinfo.OAuth2UserInfo;
import be.global.security.auth.session.user.SessionUser;
import be.global.security.auth.userdetails.AuthUser;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private OAuth2UserInfo oAuth2UserInfo;
	private final UserRepository userRepository;
	private final MailController mailController;
	private final PasswordEncoder passwordEncoder;
	private final CustomAuthorityUtils authorityUtils;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		OAuth2UserInfo oauth2UserInfo = getOAuth2UserInfo(userRequest, oauth2User);

		return getPrincipalDetails(oauth2UserInfo);
	}

	private OAuth2UserInfo getOAuth2UserInfo(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
		if (userRequest.getClientRegistration().getRegistrationId().equals("google"))
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());

		if (userRequest.getClientRegistration().getRegistrationId().equals("naver"))
			oAuth2UserInfo = new NaverUserInfo(oauth2User.getAttributes());

		if (userRequest.getClientRegistration().getRegistrationId().equals("kakao"))
			oAuth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());

		return oAuth2UserInfo;
	}

	private AuthUser getPrincipalDetails(OAuth2UserInfo oauth2UserInfo) {

		Optional<User> user = userRepository.findByEmail(oauth2UserInfo.getEmail());

		String providerId = oauth2UserInfo.getProviderId();
		String nickname = oauth2UserInfo.getName();
		String email = oauth2UserInfo.getEmail();
		String uuid = UUID.randomUUID().toString().substring(0, 15);
		String password = passwordEncoder.encode(uuid);
		String imageUrl = oauth2UserInfo.getImageUrl();

		if (user.isEmpty()) {
			log.info("OAuth2 Login");

			User saved = User.builder()
				.email(email)
				.nickname(nickname)
				.status(UserStatus.ACTIVE_USER.getStatus())
				.provider(oauth2UserInfo.getProvider().toUpperCase())
				.imageUrl(imageUrl)
				.roles(authorityUtils.createRoles(email))
				.password(password)
				.build();

			saved.randomProfileImage(saved.getImageUrl());

			MailDto.sendPWMail post = MailDto.sendPWMail.builder()
				.email(email)
				.password(uuid)
				.build();
			mailController.sendOAuth2PasswordEmail(post);

			userRepository.save(saved);

			return new AuthUser(saved.getId(), saved.getAge(), saved.getRoles(), saved.getEmail(), saved.getGender(),
				saved.getNickname(), saved.getPassword(), saved.getProvider(), oauth2UserInfo);
		}

		rejectWithdrawal(user);

		return new AuthUser(user.get().getId(), user.get().getAge(), user.get().getRoles(), user.get().getEmail(),
			user.get().getGender(), user.get().getNickname(), user.get().getPassword(), user.get().getProvider(),
			oauth2UserInfo);
	}

	private void rejectWithdrawal(Optional<User> user) {
		if (user.get().getUserStatus() == UserStatus.QUIT_USER.getStatus())
			throw new BusinessLogicException(ExceptionCode.WITHDRAWN_USER);
	}
}
