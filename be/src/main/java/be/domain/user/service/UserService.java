package be.domain.user.service;

import be.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserService {

    // 임시 유저 CRUD
    public User create() {
        return null;
    }

    public User update() {
        return null;
    }

    public User getSingleUser() {
        return null;
    }

    public List<User> getUserList() {
        return null;
    }

    public String delete() {
        return null;
    }
}
