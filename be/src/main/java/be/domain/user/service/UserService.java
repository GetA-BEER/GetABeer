package be.domain.user.service;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /* 임시 유저 Create */
    @Transactional
    public User createUser(User user) {
        verifyExistEmail(user.getEmail());
        return userRepository.save(user);
    }

    /* 임시 유저 update */
    @Transactional
    public User updateUser(Long id, UserDto.Patch patch) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        Optional.ofNullable(patch.getNickname()).ifPresent(user::edit);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    @Transactional
    public String delete() {
        return null;
    }

    /* 이미 가입한 유저인지 확인 */
    private void verifyExistEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) throw new BusinessLogicException(ExceptionCode.USER_ID_EXISTS);
    }

    /* 존재하는 유저인지 확인 및 ID 반환*/
    private Long findUserId(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        User findUser = user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser.getId();
    }
}
