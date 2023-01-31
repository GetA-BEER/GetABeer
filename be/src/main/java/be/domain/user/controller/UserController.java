package be.domain.user.controller;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.mapper.UserMapper;
import be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        User user = userService.createUser(userMapper.postToUser(post));
        return ResponseEntity.ok(userMapper.userToResponse(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto.Response> patchUser(@PathVariable Long id,
                                                      @Valid @RequestBody UserDto.Patch patch) {
        User user = userService.updateUser(id, patch);
        return ResponseEntity.ok(userMapper.userToResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> readUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(userMapper.userToResponse(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}
