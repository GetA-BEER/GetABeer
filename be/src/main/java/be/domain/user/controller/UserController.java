package be.domain.user.controller;

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
import org.springframework.web.bind.annotation.RestController;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserService;
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

	/* 유저 정보 수정(이미지, 닉네임, 성별, 나이) */
	@PatchMapping("/mypage/userinfo")
	public ResponseEntity<UserDto.UserInfoResponse> patchUser(@Valid @RequestBody UserDto.EditUserInfo edit) {
		User user = userService.updateUser(edit);
		return ResponseEntity.ok(userMapper.userToInfoResponse(user));
	}

	@GetMapping("/user")
	public ResponseEntity<UserDto.Response> readUser() {
		User user = userService.getUser();
		return ResponseEntity.ok(userMapper.userToResponse(user));
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		return ResponseEntity.ok(userService.delete(id));
	}
}
