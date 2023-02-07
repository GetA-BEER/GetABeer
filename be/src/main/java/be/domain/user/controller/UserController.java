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

	/* 유저 정보 입력(연령대, 나이) */
	// @PostMapping("/register/user/info")
	// public ResponseEntity postUserInfo() {
	//
	// }

	@PatchMapping("/user/{id}")
	public ResponseEntity<UserDto.Response> patchUser(@PathVariable Long id, @Valid @RequestBody UserDto.Patch patch) {
		User user = userService.updateUser(id, patch);
		return ResponseEntity.ok(userMapper.userToResponse(user));
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<UserDto.Response> readUser(@PathVariable Long id) {
		User user = userService.getUser(id);
		return ResponseEntity.ok(userMapper.userToResponse(user));
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		return ResponseEntity.ok(userService.delete(id));
	}
}
