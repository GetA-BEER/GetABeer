package be.domain.user.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.service.BeerTagService;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.entity.UserBeerTag;
import be.domain.user.entity.enums.ProviderType;
import be.domain.user.entity.enums.RandomProfile;
import be.domain.user.entity.enums.UserStatus;
import be.domain.user.repository.UserBeerTagRepository;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserBeerTagRepository userBeerTagRepository;
	private final BeerTagService beerTagService;
	private final PasswordEncoder passwordEncoder;
	private final CustomAuthorityUtils authorityUtils;
	private final RedisTemplate<String, String> redisTemplate;
	private final EntityManager em;

	/* 유저 회원가입 */
	@Transactional
	public User registerUser(User user) {
		verifyExistEmail(user.getEmail());
		// verifiedEmail(user.getEmail()); // 이메일인증된 유저만 회원가입 가능
		// verifyNickname(user.getNickname()); // 닉네임 중복 확인

		User saved = User.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.status(UserStatus.ACTIVE_USER.getStatus())
			.provider(String.valueOf(ProviderType.LOCAL))
			.roles(authorityUtils.createRoles(user.getEmail()))
			.password(passwordEncoder.encode(user.getPassword()))
			.imageUrl(RandomProfile.values()[(int)(Math.random() * 4)].getValue())
			.build();

		redisTemplate.delete(user.getEmail());

		return userRepository.save(saved);
	}

	/* 회원가입 유저 정보 입력 - 연령, 성별, 태그 */
	@Transactional
	public User postUserInfo(UserDto.UserInfoPost post) {
		User user = userRepository.findByEmail(post.getEmail())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

		// setUserBeerTags(user, post);

		user.setUserInfo(post.getAge(), post.getGender());
		em.flush();

		return user;
	}

	/* 유저 정보 수정 */
	@Transactional
	public User updateUser(UserDto.EditUserInfo edit) {
		User user = getLoginUser();

		// if (edit.getNickname() != null) {
		// 	verifyNickname(edit.getNickname());
		// }

		user.edit(edit);
		em.flush();

		return userRepository.findById(user.getId()).orElseThrow();
	}

	/* 비밀번호 확인 */
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

	/* 닉네임 확인 */
	public Boolean verifyNickname(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			return true;
		} else {
			throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
		}
	}

	/* 유저 조회 */
	@Transactional(readOnly = true)
	public User getUser() {
		User user = getLoginUser();
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		return userRepository.findByEmail(authentication.getName())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
	}
  
  /* 접근 혹은 접근하려는 페이지의 유저와 로그인 유저가 일치하는 지 판별*/
	public void checkUser(Long userId, Long loginUserId) {
		if (!userId.equals(loginUserId)) {
			throw new BusinessLogicException(ExceptionCode.NOT_CORRECT_USER);
		}
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

	/* 로그아웃 */
	public void logout(HttpServletRequest request, String email) {
		redisTemplate.opsForValue()
			.set(request.getHeader("Authorization"), "logout", 30 * 60 * 1000L, TimeUnit.MILLISECONDS);
		redisTemplate.delete(email);
	}

	/* setBeerTags */
	// private void setUserBeerTags(User user, UserDto.UserInfoPost post) {
	// 	post.getUserBeerTags()
	// 		.forEach(userBeerTag -> {
	// 			BeerTag beerTag = beerTagService.findVerifiedBeerTag(userBeerTag.getBeerTag().getId());
	// 			UserBeerTag saved = UserBeerTag.builder()
	// 				.user(user)
	// 				.beerTag(beerTag)
	// 				.build();
	// 			userBeerTagRepository.save(saved);
	// 		});
	// }

	/* 이미 가입한 유저인지 확인 */
	private void verifyExistEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.USER_ID_EXISTS);
		}
	}

	/* 존재하는 유저인지 확인 및 ID 반환*/
	public Long findUserEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		User findUser = user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
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
		if (Objects.equals(redisTemplate.opsForValue().get(email), "false")) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_EMAIL);
		}
	}
}
