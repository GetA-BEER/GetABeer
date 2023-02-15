package be.domain.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.PairingComment;
import be.domain.comment.entity.RatingComment;
import be.domain.comment.mapper.PairingCommentMapper;
import be.domain.comment.mapper.RatingCommentMapper;
import be.domain.like.repository.PairingLikeRepository;
import be.domain.like.repository.RatingLikeRepository;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.mapper.PairingMapper;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.mapper.RatingMapper;
import be.domain.user.service.UserPageService;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserService;
import be.global.dto.MultiResponseDto;
import be.global.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
	private final UserMapper userMapper;
	private final UserService userService;
	private final RatingMapper ratingMapper;
	private final PairingMapper pairingMapper;
	private final UserPageService userPageService;
	private final RatingCommentMapper ratingCommentMapper;
	private final PairingCommentMapper pairingCommentMapper;
	private final RatingLikeRepository ratingLikeRepository;
	private final PairingLikeRepository pairingLikeRepository;

	/* 회원가입 */
	@PostMapping("/register/user")
	public ResponseEntity<Long> registerUser(@Valid @RequestBody UserDto.RegisterPost post) {
		User user = userService.registerUser(userMapper.postToUser(post));
		return ResponseEntity.ok(user.getId());
	}

	/* 유저 정보 입력(성별, 나이, 카테고리, 태그) */
	@PostMapping("/register/user/{user-id}")
	public ResponseEntity<String> postUserInfo(@PathVariable(name = "user-id") @Positive Long id,
		@Valid @RequestBody UserDto.UserInfoPost infoPost) {
		User user = userMapper.infoPostToUser(id, infoPost);
		userService.postUserInfo(user);
		return ResponseEntity.ok("회원가입을 환영합니다.");
	}

	/* 자체 로그인 */
	@PostMapping("/login")
	public void login(@RequestBody UserDto.Login login) {
		User user = userService.findVerifiedUser(userService.findUserEmail(login.getEmail()));

		// 유저 상태 확인
		userService.verifyUserStatus(user.getUserStatus());
	}

	/* 유저 정보 수정(닉네임, 성별, 나이, 카테고리, 태그) */
	@PatchMapping("/mypage/userinfo")
	public ResponseEntity<UserDto.UserInfoResponse> patchUser(@Valid @RequestBody UserDto.EditUserInfo edit) {
		User user = userMapper.editToUser(edit);
		User response = userService.updateUser(user);
		return ResponseEntity.ok(userMapper.userToInfoResponse(response));
	}

	/* 유저 정보 수정(프로필 이미지) */
	@PatchMapping("/mypage/userinfo/image")
	public ResponseEntity<UserDto.UserInfoResponse> editProfileImage(
		@RequestParam(value = "image") MultipartFile image) throws IOException {
		User user = userService.updateProfileImage(image);
		return ResponseEntity.ok(userMapper.userToInfoResponse(user));
	}

	/* 유저정보 조회 */
	@GetMapping("/user")
	public ResponseEntity<UserDto.UserInfoResponse> readUser() {
		User user = userService.getLoginUser();
		return ResponseEntity.ok(userMapper.userToInfoResponse(user));
	}

	/* 비밀번호 확인 */
	@PatchMapping("/user/password")
	public ResponseEntity<String> editPassword(@RequestBody @Valid UserDto.EditPassword editPassword) {
		User user = userService.verifyPassword(editPassword);
		return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
	}

	/* 유저 로그아웃 */
	@PostMapping("/user/logout")
	public void logoutUser(HttpServletRequest request) {
		String email = userService.getLoginUser().getEmail();
		userService.logout(request, email);
	}

	/* 유저 탈퇴 */
	@PatchMapping("/user/withdraw")
	public ResponseEntity<String> withDrawUser(HttpServletRequest request) {
		String email = userService.getLoginUser().getEmail();

		userService.withdraw();
		userService.logout(request, email);

		return ResponseEntity.ok("정상적으로 탈퇴되셨습니다.");
	}

	/* 유저 완전 삭제 */
	@DeleteMapping("/user")
	public ResponseEntity<String> delete() {
		return ResponseEntity.ok(userService.deleteUser());
	}

	/**
	 * 마이페이지
	 */
	@GetMapping("/mypage")
	public ResponseEntity<SingleResponseDto<UserDto.UserInfoResponse>> getMyPage() {
		User user = userService.getLoginUser();
		return ResponseEntity.ok(new SingleResponseDto<>(userMapper.userToInfoResponse(user)));
	}

	/* 나의 평가 */
	@GetMapping("/mypage/ratings")
	public ResponseEntity<MultiResponseDto<RatingResponseDto.Total>> getMyRatings(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {
		Page<Rating> ratings = userPageService.getUserRating(page);
		Page<RatingResponseDto.Total> userRatingList = ratingMapper.ratingToRatingResponse(ratings.getContent(),
			ratingLikeRepository);

		return ResponseEntity.ok(new MultiResponseDto<>(userRatingList.getContent(), userRatingList));
	}

	/* 나의 페어링 코멘트 */
	@GetMapping("/mypage/comment/pairing")
	public ResponseEntity<MultiResponseDto<PairingCommentDto.Response>> getMyPairingComments(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {
		Page<PairingComment> pairingComments = userPageService.getUserPairingComment(page);
		Page<PairingCommentDto.Response> responses = pairingCommentMapper.pairingCommentsToPageResponse(
			pairingComments.getContent());

		return ResponseEntity.ok(new MultiResponseDto<>(responses.getContent(), pairingComments));
	}

	/* 나의 레이팅 코멘트 */
	@GetMapping("/mypage/comment/rating")
	public ResponseEntity<MultiResponseDto<RatingCommentDto.Response>> getMyRatingComments(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {
		Page<RatingComment> ratingComments = userPageService.getUserRatingComment(page);
		Page<RatingCommentDto.Response> responses = ratingCommentMapper.ratingCommentsToResponsePage(
			ratingComments.getContent());

		return ResponseEntity.ok(new MultiResponseDto<>(responses.getContent(), ratingComments));
	}

	/* 나의 페어링 */
	@GetMapping("/mypage/pairing")
	public ResponseEntity<MultiResponseDto<PairingResponseDto.Total>> getMyPairing(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {
		Page<Pairing> pairings = userPageService.getUserPairing(page);
		Page<PairingResponseDto.Total> userPairingList = pairingMapper.pairingToPairingResponse(pairings.getContent(),
			pairingLikeRepository);

		return ResponseEntity.ok(new MultiResponseDto<>(userPairingList.getContent(), userPairingList));
	}

}
