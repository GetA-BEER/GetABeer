package be.domain.user.service;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.Users;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /* 임시 유저 Create */
    public Users createUser(Users user) {
        verifyExistEmail(user.getEmail());
        return userRepository.save(user);
    }

    /* 임시 유저 update */
    public Users update(String email, UserDto.Patch patch) {
        Users user = userRepository.findById(findUserId(email)).orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        Optional.ofNullable(patch.getNickname()).ifPresent(user::edit);
        return userRepository.save(user);
    }

    public Users getUser() {
        return null;
    }

    public List<Users> getUserList() {
        return null;
    }

    public String delete() {
        return null;
    }

    /* 이미 가입한 유저인지 확인 */
    private void verifyExistEmail(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        if (user.isPresent()) throw new BusinessLogicException(ExceptionCode.USER_ID_EXISTS);
    }

    /* 존재하는 유저인지 확인 및 ID 반환*/
    private Long findUserId(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        Users findUser = user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser.getId();
    }
}
