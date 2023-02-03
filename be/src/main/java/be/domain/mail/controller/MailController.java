package be.domain.mail.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.domain.mail.dto.MailDto;
import be.domain.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MailController {

	private final MailService mailService;
	// private final RedisTemplate<String, String> redisTemplate;

	@PostMapping("/register/mail")
	public ResponseEntity<String> sendEmail(@RequestBody MailDto.postEmail mailDto) {
		String code = mailService.sendCertificationMail(mailDto.getEmail());
		// redisTemplate.opsForValue()
		// 	.set(code, mailDto.getEmail(), 60 * 1000L, TimeUnit.MILLISECONDS);

		return ResponseEntity.ok("메세지가 전송되었습니다.");
	}

	// @PostMapping("/mail/check")
	// public ResponseEntity<String> checkEmail(@RequestBody MailDto.checkMail checkMail) {
	// 	if(!Boolean.TRUE.equals(redisTemplate.hasKey(checkMail.getCode()))){
	// 		throw new BusinessLogicException(ExceptionCode.WRONG_CODE);
	// 	}
	// 	if(!Objects.equals(redisTemplate.opsForValue().get(checkMail.getCode()), checkMail.getEmail())){
	// 		throw new BusinessLogicException(ExceptionCode.WRONG_CODE);
	// 	}
	// 	return ResponseEntity.ok("인증되었습니다.");
	// }
}
