package be.domain.user.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.service.BeerCategoryService;
import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.service.BeerTagService;
import be.domain.comment.entity.PairingComment;
import be.domain.comment.entity.RatingComment;
import be.domain.comment.repository.PairingCommentRepository;
import be.domain.comment.repository.RatingCommentRepository;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.repository.PairingRepository;
import be.domain.rating.entity.Rating;
import be.domain.rating.repository.RatingRepository;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.ProfileImage;
import be.domain.user.entity.User;
import be.domain.user.entity.UserBeerCategory;
import be.domain.user.entity.UserBeerTag;
import be.domain.user.entity.enums.ProviderType;
import be.domain.user.entity.enums.UserStatus;
import be.domain.user.repository.ProfileImageRepository;
import be.domain.user.repository.UserBeerCategoryQRepository;
import be.domain.user.repository.UserBeerCategoryRepository;
import be.domain.user.repository.UserBeerTagQRepository;
import be.domain.user.repository.UserBeerTagRepository;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.image.PairingImageHandler;
import be.global.security.auth.jwt.JwtTokenizer;
import be.global.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final EntityManager em;
	private final JwtTokenizer jwtTokenizer;
	private final UserRepository userRepository;
	private final BeerTagService beerTagService;
	private final PasswordEncoder passwordEncoder;
	private final RatingRepository ratingRepository;
	private final CustomAuthorityUtils authorityUtils;
	private final PairingRepository pairingRepository;
	private final BeerCategoryService beerCategoryService;
	private final PairingImageHandler pairingImageHandler;
	private final RedisTemplate<String, String> redisTemplate;
	private final UserBeerTagRepository userBeerTagRepository;
	private final UserBeerTagQRepository userBeerTagQRepository;
	private final ProfileImageRepository profileImageRepository;
	private final RatingCommentRepository ratingCommentRepository;
	private final PairingCommentRepository pairingCommentRepository;
	private final UserBeerCategoryRepository userBeerCategoryRepository;
	private final UserBeerCategoryQRepository userBeerCategoryQRepository;

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
			.build();

		saved.randomProfileImage(user.getImageUrl());

		redisTemplate.delete(user.getEmail());

		return userRepository.save(saved);
	}

	/* 회원가입 유저 정보 입력 - 연령, 성별, 태그, 카테고리 */
	@Transactional
	public User postUserInfo(User post) {
		User user = userRepository.findById(post.getId())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

		setUserBeerTags(post, user);
		setUserBeerCategories(post, user);
		user.setUserInfo(post.getAge(), post.getGender());
		em.flush();

		return user;
	}

	/* 유저 정보 수정 - 연령, 성별, 태그, 카테고리 */
	@Transactional
	public User updateUser(User edit) {
		User user = getLoginUser();

		if (edit.getNickname() != null) {
			verifyNickname(edit.getNickname());
		}

		if (edit.getUserBeerCategories() != null) {
			setUserBeerCategories(edit, user);
		}

		if (edit.getUserBeerTags() != null) {
			setUserBeerTags(edit, user);
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
		HashMap<String, Objects> map;
		ProfileImage saved;

		if (verifyProfileImage()) {
			map = pairingImageHandler.createProfileImage(image, "/profileImage");

			saved = ProfileImage.builder()
				.imageUrl(map.get("url").toString())
				.fileKey(map.get("fileKey").toString())
				.user(user)
				.build();
		} else {
			ProfileImage profileImage = user.getProfileImage();

			map = pairingImageHandler.updateProfileImage(image, "/profileImage", profileImage.getFileKey());

			saved = ProfileImage.builder()
				.id(profileImage.getId())
				.imageUrl(map.get("url").toString())
				.fileKey(map.get("fileKey").toString())
				.user(user)
				.build();
		}

		profileImageRepository.save(saved);

		user.setImageUrl(saved.getImageUrl());

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

	/* 닉네임 확인 */
	public Boolean verifyNickname(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			return true;
		} else {
			throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
		}
	}

	/* 유저 정보 조회 */
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		return findVerifiedUser(user.getId());
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

	public User getLoginUserReturnNull() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		return userRepository.findByEmail(authentication.getName())
			.orElse(null);
	}

	/* 접근 혹은 접근하려는 페이지의 유저와 로그인 유저가 일치하는 지 판별 */
	public void checkUser(Long userId, Long loginUserId) {
		if (!userId.equals(loginUserId)) {
			throw new BusinessLogicException(ExceptionCode.NOT_CORRECT_USER);
		}
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
			.set(request.getHeader("Authorization"),
				"logout",
				30 * 60 * 1000L,
				TimeUnit.MILLISECONDS);
		redisTemplate.delete(email);
	}

	/* 이미 가입한 이메일인지 확인 */
	public boolean verifyExistEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.USER_ID_EXISTS);
		}

		return true;
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

	/* set UserBeerTags */
	private void setUserBeerTags(User post, User user) {
		if (user.getUserBeerTags() != null) {
			userBeerTagQRepository.delete(user.getId());
		}

		post.getUserBeerTags().forEach(userBeerTag -> {
			BeerTag beerTag =
				beerTagService.findVerifiedBeerTagByBeerTagType(userBeerTag.getBeerTag().getBeerTagType());
			UserBeerTag saved = UserBeerTag.builder()
				.user(user)
				.beerTag(beerTag)
				.build();
			userBeerTagRepository.save(saved);
		});
	}

	/* set BeerCategories */
	private void setUserBeerCategories(User post, User user) {
		if (user.getUserBeerCategories() != null) {
			userBeerCategoryQRepository.delete(user.getId());
		}

		post.getUserBeerCategories().forEach(userBeerCategory -> {
			BeerCategory beerCategory =
				beerCategoryService.findVerifiedBeerCategory(userBeerCategory.getBeerCategory().getBeerCategoryType());
			UserBeerCategory saved = UserBeerCategory.builder()
				.user(user)
				.beerCategory(beerCategory)
				.build();
			userBeerCategoryRepository.save(saved);
		});
	}

	/* 이미지 유무 확인 */
	public Boolean verifyProfileImage() {
		User user = getLoginUser();

		return user.getProfileImage() == null;
	}

	/**
	 * 마이페이지
	 */

	/* 나의 평가 */
	public Page<Rating> getUserRating(int page) {
		User user = getLoginUser();
		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return ratingRepository.findRatingByUser(user, pageRequest);
	}

	/* 나의 레이팅 코멘트 */
	public Page<RatingComment> getUserRatingComment(int page) {
		User user = getLoginUser();
		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return ratingCommentRepository.findRatingCommentByUser(user, pageRequest);
	}

	/* 나의 페어링 코멘트 */
	public Page<PairingComment> getUserPairingComment(int page) {
		User user = getLoginUser();
		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return pairingCommentRepository.findPairingCommentByUser(user, pageRequest);
	}

	/* 나의 페어링 */
	public Page<Pairing> getUserPairing(int page) {
		User user = getLoginUser();
		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return pairingRepository.findPairingByUser(user, pageRequest);
	}

	/**
	 * 토큰 재발급
	 */
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		User user = getLoginUser();

		String[] cookies = request.getHeader("Set-Cookie").split(";");
		Stream<String> stream = Arrays.stream(cookies)
			.map(cookie -> cookie.replace(" ", ""))
			.filter(c -> c.startsWith("refreshToken"));
		String value = stream.reduce((first, second) -> second)
			.map(v -> v.replace("refreshToken=", ""))
			.orElse(null);

		if (!Objects.equals(redisTemplate.opsForValue().get(user.getEmail()), value)) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		redisTemplate.delete(user.getEmail());

		String accessToken = jwtTokenizer.delegateAccessToken(user.getEmail(), user.getRoles(), user.getProvider());
		String refreshToken = jwtTokenizer.delegateRefreshToken(user.getEmail());

		if (Boolean.TRUE.equals(redisTemplate.hasKey(user.getEmail()))) {
			redisTemplate.delete(user.getEmail());
		}
		redisTemplate.opsForValue()
			.set(user.getEmail(), refreshToken, 168 * 60 * 60 * 1000L, TimeUnit.MILLISECONDS);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.maxAge(7 * 24 * 60 * 60)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();

		response.setHeader("Set-Cookie", cookie.toString());
		response.setHeader("Authorization", "Bearer " + accessToken);
	}
}
