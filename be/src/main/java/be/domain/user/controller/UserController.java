package be.domain.user.controller;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.Users;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto.Response> postUser(@Valid @RequestBody UserDto.Post post) {
        Users user = userService.createUser(userMapper.postToUser(post));
        return ResponseEntity.ok(userMapper.userToResponse(user));
    }

    public ResponseEntity patch() {
        return null;
    }

    public ResponseEntity read() {
        return null;
    }

    public ResponseEntity readAll() {
        return null;
    }

    public ResponseEntity<String> delete() {
        return null;
    }
}
