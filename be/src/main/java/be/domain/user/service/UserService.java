package be.domain.user.service;

import be.domain.user.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserService {

    // 임시 유저 CRUD
    public Users create() {
        return null;
    }

    public Users update() {
        return null;
    }

    public Users getSingleUser() {
        return null;
    }

    public List<Users> getUserList() {
        return null;
    }

    public String delete() {
        return null;
    }
}
