package be.domain.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping
public class UserController {

    public ResponseEntity post() {
        return null;
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
