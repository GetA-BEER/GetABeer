package be.domain.user.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.ProviderType;
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
	private final PasswordEncoder passwordEncoder;
	private final CustomAuthorityUtils authorityUtils;
	private final RedisTemplate<String, String> redisTemplate;
	private final EntityManager em;

	/* 유저 회원가입 */
	@Transactional
	public User registerUser(User user) {
		verifyExistEmail(user.getEmail());
		verifiedEmail(user.getEmail());

		// String imageUrl =
		String password = passwordEncoder.encode(user.getPassword());
		List<String> roles = authorityUtils.createRoles(user.getEmail());

		User saved = User.builder()
			.id(user.getId())
			.email(user.getEmail())
			.roles(roles)
			.password(password)
			.nickname(user.getNickname())
			.provider(String.valueOf(ProviderType.LOCAL))
			.imageUrl("임시이미지인척하는 스트링")
			.build();

		redisTemplate.delete(user.getEmail());

		return userRepository.save(saved);
	}

	/* 유저 정보 입력 - 연령, 성별 */
	@Transactional
	public void postUserInfo(UserDto.UserInfoPost post) {
		User user = userRepository.findByEmail(post.getEmail())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

		user.setUserInfo(post);
		em.flush();
	}

	/* 임시 유저 update */
	@Transactional
	public User updateUser(Long id, UserDto.Patch patch) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

		Optional.ofNullable(patch.getNickname()).ifPresent(user::edit);
		return userRepository.save(user);
	}

	/* 임시 유저 get */
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return findVerifiedUser(id);
	}

	/* 임시 유저 delete */
	@Transactional
	public String delete(Long id) {
		userRepository.delete(findVerifiedUser(id));
		return "삭제 성공";
	}

	/* 이미 가입한 유저인지 확인 */
	private void verifyExistEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.USER_ID_EXISTS);
		}
	}

	/* 존재하는 유저인지 확인 및 ID 반환*/
	private Long findUserId(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		User findUser = user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		return findUser.getId();
	}

	/* 존재하는 유저인지 확인 후 유저 반환 */
	private User findVerifiedUser(Long id) {
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

	/* 로그인 유저 반환 */
	public User getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		return userRepository.findByEmail(authentication.getName())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
	}
}
