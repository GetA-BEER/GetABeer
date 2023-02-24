package be.domain.mail.controller;

import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.domain.mail.dto.MailDto;
import be.domain.mail.service.MailService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MailController {

	private final MailService mailService;
	private final RedisTemplate<String, String> redisTemplate;

	@PostMapping("/mail")
	public ResponseEntity<String> sendEmail(@RequestBody MailDto.postEmail mailDto) {
		mailService.verifyEmail(mailDto.getEmail());
		mailService.sendCertificationMail(mailDto.getEmail());

		return ResponseEntity.ok("인증 코드가 전송되었습니다.");
	}

	@PostMapping("/mail/password")
	public void sendOAuth2PasswordEmail(@RequestBody MailDto.sendPWMail post) {
		mailService.verifyEmail(post.getEmail());
		mailService.sendPasswordMail(post.getEmail(), post.getPassword());

		ResponseEntity.ok("임시 비밀번호가 전송되었습니다.");
	}

	@PostMapping("/mail/check")
	public ResponseEntity<String> checkEmail(@RequestBody MailDto.checkMail checkMail) {
		if (!Boolean.TRUE.equals(redisTemplate.hasKey(checkMail.getEmail()))) {
			throw new BusinessLogicException(ExceptionCode.WRONG_CODE);
		}
		if (!Objects.equals(redisTemplate.opsForValue().get(checkMail.getEmail()), checkMail.getCode())) {
			throw new BusinessLogicException(ExceptionCode.WRONG_CODE);
		}

		mailService.setVerifiedEmail(checkMail.getEmail());

		return ResponseEntity.ok(checkMail.getEmail());
	}
}
