package be.domain.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
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
	private final BCryptPasswordEncoder passwordEncoder;
	private final CustomAuthorityUtils authorityUtils;

	/* 2차 유저 Create */
	@Transactional
	public User registerUser(User user) {
		verifyExistEmail(user.getEmail());

		String encryptPW = passwordEncoder.encode(user.getPassword());
		List<String> roles = authorityUtils.createRoles(user.getEmail());

		User saved = User.builder()
			.id(user.getId())
			.password(encryptPW)
			.nickname(user.getNickname())
			.roles(roles)
			.build();

		return userRepository.save(saved);
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
}
