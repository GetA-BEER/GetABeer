package be.domain.user.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import be.domain.notice.repository.EmitterRepository;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.ProfileImage;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.ProviderType;
import be.domain.user.entity.enums.UserStatus;
import be.domain.user.repository.ProfileImageRepository;
import be.domain.user.repository.UserRepository;
import be.domain.user.service.pattern.StateButton;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.image.ImageHandler;
import be.global.security.auth.constant.SessionKey;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final EntityManager em;
	// private final HttpSession httpSession;
	private final ImageHandler imageHandler;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmitterRepository emitterRepository;
	private final CustomAuthorityUtils authorityUtils;
	private final RedisTemplate<String, String> redisTemplate;
	private final UserPreferenceService userPreferenceService;
	private final ProfileImageRepository profileImageRepository;
	private static StateButton stateButton = new StateButton();

	/* 유저 회원가입 */
	@Transactional
	public User registerUser(User user) {
		verifyExistEmail(user.getEmail());
		verifiedEmail(user.getEmail()); // 이메일인증된 유저만 회원가입 가능
		verifyNickname(user.getNickname()); // 닉네임 중복 확인

		User saved = User.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.status(UserStatus.ACTIVE_USER.getStatus())
			.provider(String.valueOf(ProviderType.LOCAL))
			.roles(authorityUtils.createRoles(user.getEmail()))
			.password(passwordEncoder.encode(user.getPassword()))
			.build();

		saved.randomProfileImage(user.getImageUrl());

		redisTemplate.delete(user.getEmail());

		return userRepository.save(saved);
	}

	/* 회원가입 유저 정보 입력 - 연령, 성별, 태그, 카테고리 */
	@Transactional
	public void postUserInfo(User post) {
		User user = userRepository.findById(post.getId())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

		userPreferenceService.setUserBeerTags(post, user);
		userPreferenceService.setUserBeerCategories(post, user);
		user.putUserInfo(post.getAge(), post.getGender());
		em.flush();
	}

	/* 유저 정보 수정 - 연령, 성별, 태그, 카테고리 */
	@Transactional
	public User updateUser(User edit) {
		User user = getLoginUser();

		if (edit.getNickname() != null && !user.getNickname().equals(edit.getNickname())) {
			verifyNickname(edit.getNickname());
		}

		if (edit.getUserBeerCategories() != null) {
			userPreferenceService.setUserBeerCategories(edit, user);
		}

		if (edit.getUserBeerTags() != null) {
			userPreferenceService.setUserBeerTags(edit, user);
		}

		user.edit(edit.getImageUrl(),
			edit.getNickname(),
			edit.getGender(),
			edit.getAge());
		em.flush();

		return userRepository.findById(user.getId()).orElseThrow();
	}

	/* 유저 정보 수정 - 프로필 이미지 */
	@Transactional
	public User updateProfileImage(MultipartFile image) throws IOException {
		User user = getLoginUser();
		ProfileImage saved = stateButton.clickButton(stateButton, new HashMap<>(), image, imageHandler, user);

		profileImageRepository.save(saved);
		user.putImageUrl(saved.getImageUrl());

		return userRepository.save(user);
	}

	/* 비밀번호 수정 - 확인 */
	@Transactional
	public User verifyPassword(UserDto.EditPassword editPassword) {
		User user = getLoginUser();
		if (!passwordEncoder.matches(editPassword.getOldPassword(), user.getPassword())) {
			throw new BusinessLogicException(ExceptionCode.WRONG_PASSWORD);
		}
		if (!editPassword.getNewPassword().equals(editPassword.getNewVerifyPassword())) {
			throw new BusinessLogicException(ExceptionCode.WRONG_PASSWORD);
		}

		user.editPassword(passwordEncoder.encode(editPassword.getNewPassword()));
		em.flush();

		return user;
	}

	/* 유저 정보 조회 */
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		return findVerifiedUser(user.getId());
	}

	/* 유저 탈퇴 */
	@Transactional
	public void withdraw() {
		User user = getLoginUser();
		user.withdraw();
		userRepository.save(user);
	}

	/* 유저 완전 삭제 */
	@Transactional
	public String deleteUser() {
		User user = getLoginUser();
		userRepository.delete(user);
		return "유저 완전 삭제 성공";
	}

	/* 로그인 유저 반환 */
	public User getLoginUser() {
		Authentication authentication = verifiedAuthentication();

		return userRepository.findByEmail(authentication.getName())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
	}

	public User getLoginUserReturnNull() {
		Authentication authentication = verifiedAuthentication();

		return userRepository.findByEmail(authentication.getName())
			.orElse(null);
	}

	public User findLoginUser() {
		Authentication authentication = verifiedAuthentication();

		return userRepository.findByEmail(authentication.getName())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_LOGIN));
	}

	private Authentication verifiedAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}
		return authentication;
	}

	/* 유저 상태 : 활동중, 휴면, 탈퇴유저 구분 */
	public void verifyUserStatus(String status) {
		UserStatus userStatus = Arrays.stream(UserStatus.values())
			.filter(key -> key.getStatus().equals(status))
			.findAny()
			.orElseThrow();

		if (userStatus == UserStatus.QUIT_USER) {
			throw new BusinessLogicException(ExceptionCode.WITHDRAWN_USER);
		}
		if (userStatus == UserStatus.SLEEP_USER) {
			throw new BusinessLogicException(ExceptionCode.SLEEP_USER);
		}
	}

	@Transactional
	public void login() {
		// httpSession.setAttribute(SessionKey.LOGIN_USER_ID, getLoginUser().getEmail());
	}

	/* 로그아웃 */
	public void logout(HttpServletRequest request, User user) {
		redisTemplate.opsForValue()
			.set(request.getHeader("Authorization"),
				"logout",
				30 * 60 * 1000L,
				TimeUnit.MILLISECONDS);
		redisTemplate.delete(user.getEmail());
		emitterRepository.deleteAllStartByWithId(String.valueOf(user.getId()));
		// httpSession.removeAttribute(SessionKey.LOGIN_USER_ID);
	}

	/* 닉네임 확인 */
	public void verifyNickname(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
		}
	}

	/* 이미 가입한 이메일인지 확인 */
	public void verifyExistEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
		}
	}

	/* 존재하는 유저인지 확인 및 ID 반환*/
	public Long findUserEmail(String email) {
		User findUser = userRepository.findByEmail(email)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		return findUser.getId();
	}

	/* 존재하는 유저인지 확인 후 유저 반환 */
	public User findVerifiedUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
	}

	/* 이메일 인증된 유저인지 확인 */
	private void verifiedEmail(String email) {
		if (Boolean.FALSE.equals(redisTemplate.hasKey(email))) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_EMAIL);
		}
		if (!Objects.equals(redisTemplate.opsForValue().get(email), "true")) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_EMAIL);
		}
	}

	public void checkUser(Long userId, Long loginUserId) {
		if (!userId.equals(loginUserId)) {
			throw new BusinessLogicException(ExceptionCode.NOT_CORRECT_USER);
		}
	}
}
