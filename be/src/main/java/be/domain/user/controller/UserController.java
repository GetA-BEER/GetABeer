package be.domain.user.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import be.domain.comment.mapper.PairingCommentMapper;
import be.domain.comment.mapper.RatingCommentMapper;
import be.domain.like.repository.PairingLikeRepository;
import be.domain.like.repository.RatingLikeRepository;
import be.domain.pairing.mapper.PairingMapper;
import be.domain.rating.mapper.RatingMapper;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserPageService;
import be.domain.user.service.UserService;
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
	public ResponseEntity<UserDto.UserInfoResponse> login(@RequestBody UserDto.Login login) {
		User user = userService.findVerifiedUser(userService.findUserEmail(login.getEmail()));

		// 유저 상태 확인
		// TODO : 상태 별 휴면해제, 재가입 불가 로직 구현
		userService.verifyUserStatus(user.getUserStatus());

		return ResponseEntity.ok(userMapper.userToInfoResponse(user));
	}

	/* 유저 정보 수정(닉네임, 성별, 나이, 카테고리, 태그) */
	@PatchMapping("/mypage/userinfo")
	public ResponseEntity<UserDto.UserInfoResponse> patchUser(@Valid @RequestBody UserDto.EditUserInfo edit) {
		User user = userMapper.editToUser(edit);
		User response = userService.updateUser(user);
		return ResponseEntity.ok(userMapper.userToInfoResponse(response));
	}

	/* 유저 정보 수정(프로필 이미지) */
	// @PatchMapping("/mypage/userinfo/image")
	@RequestMapping(value = "/mypage/userinfo/image", method = {RequestMethod.POST, RequestMethod.PATCH})
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
		User user = userService.getLoginUser();
		userService.logout(request, user);
	}

	/* 유저 탈퇴 */
	@PatchMapping("/user/withdraw")
	public ResponseEntity<String> withDrawUser(HttpServletRequest request) {
		User user = userService.getLoginUser();

		userService.withdraw();
		userService.logout(request, user);

		return ResponseEntity.ok("정상적으로 탈퇴되셨습니다.");
	}

	/* 유저 완전 삭제 */
	@DeleteMapping("/user")
	public ResponseEntity<String> delete() {
		return ResponseEntity.ok(userService.deleteUser());
	}

	/**
	 * 마이페이지 회원정보
	 * My Rating, My Pairing, My Comment 는 UserPageController
	 */
	@GetMapping("/mypage")
	public ResponseEntity<SingleResponseDto<UserDto.UserInfoResponse>> getMyPage() {
		User user = userService.getLoginUser();
		return ResponseEntity.ok(new SingleResponseDto<>(userMapper.userToInfoResponse(user)));
	}

	// /**
	//  *  인덱싱 테스트
	//  */
	// @GetMapping("/index")
	// public void getAllMembers(){
	// 	noIdx();
	// 	useIdx();
	// }
	//
	// private void noIdx() {
	// 	StopWatch stopWatch = new StopWatch();
	// 	stopWatch.start();
	//
	// 	List<String> res = userService.findAll().stream().map(User::getNickname).collect(Collectors.toList());
	//
	// 	stopWatch.stop();
	// 	log.info("------------------------------------------------");
	// 	log.info("실행 시간 = "+ stopWatch.getTotalTimeNanos() + "ns");
	// 	log.info("------------------------------------------------");
	// }
	//
	// private void useIdx() {
	// 	StopWatch stopWatch = new StopWatch();
	// 	stopWatch.start();
	//
	// 	List<Object[]> res = userService.findAllOfIdx();
	//
	// 	stopWatch.stop();
	// 	log.info("------------------------------------------------");
	// 	log.info("실행 시간 = "+ stopWatch.getTotalTimeNanos() + "ns");
	// 	log.info("------------------------------------------------");
	// }
}
