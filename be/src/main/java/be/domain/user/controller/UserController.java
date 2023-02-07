package be.domain.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserService;
import be.global.image.S3UploadServiceImpl;
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
	private final S3UploadServiceImpl s3UploadService;

	/* 회원가입 */
	@PostMapping("/register/user")
	public ResponseEntity<UserDto.Response> registerUser(@Valid @RequestBody UserDto.RegisterPost post) {
		User user = userService.registerUser(userMapper.postToUser(post));
		return ResponseEntity.ok(userMapper.userToResponse(user));
	}

	/* 유저 정보 입력(성별, 나이) */
	@PostMapping("/register/user/info")
	public ResponseEntity<String> postUserInfo(@Valid @RequestBody UserDto.UserInfoPost infoPost) {
		userService.postUserInfo(infoPost);
		return ResponseEntity.ok("회원가입을 환영합니다.");
	}

	/* 자체 로그인 */
	@PostMapping("/login")
	public void login(@RequestBody UserDto.Login login) {
		User user = userService.findVerifiedUser(userService.findUserEmail(login.getEmail()));

		// 유저 상태 확인
		userService.verifyUserStatus(user.getUserStatus());
	}

	/* 유저 정보 수정(닉네임, 성별, 나이) */
	@PatchMapping("/mypage/userinfo")
	public ResponseEntity<UserDto.UserInfoResponse> patchUser(@Valid @RequestBody UserDto.EditUserInfo edit) {
		User user = userService.updateUser(edit);
		return ResponseEntity.ok(userMapper.userToInfoResponse(user));
	}

	/* 유저 정보 수정(프로필 이미지) */
	// @PostMapping("/mypage/userinfo/profile")
	// public ResponseEntity editProfileImage(@RequestParam(value = "image") MultipartFile image) {
	// 	User user = userService.getLoginUser();
	// 	String oldImageUrl = user.getImageUrl();
	//
	// }

	/* 유저정보 조회 */
	@GetMapping("/user")
	public ResponseEntity<UserDto.Response> readUser() {
		User user = userService.getUser();
		return ResponseEntity.ok(userMapper.userToResponse(user));
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
}
