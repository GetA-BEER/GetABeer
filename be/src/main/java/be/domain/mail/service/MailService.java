package be.domain.mail.service;

import java.util.Optional;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import be.domain.user.entity.User;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final RedisUtil redisUtil;
	private final JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	private final RedisTemplate<String, String> redisTemplate;

	private MimeMessage createMessage(String code, String email) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

		helper.setTo(email);
		helper.setSubject("Get A Beer 이메일 인증 코드입니다.");
		helper.setText("이메일 인증 코드: " + code, true);
		helper.setFrom("getabeer0310@gmail.com", "Get A Beer");

		return message;
	}

	public void sendMail(String code, String email) throws Exception {
		try {
			MimeMessage mimeMessage = createMessage(code, email);
			javaMailSender.send(mimeMessage);
		} catch (MailException mailException) {
			mailException.printStackTrace();
			throw new IllegalAccessException();
		}

		redisUtil.setDataExpire(email, code, 60 * 5L); // 인증번호 5분간 유효
	}

	@Async("threadPoolTaskExecutor-Mail")
	public String sendCertificationMail(String email) {

		try {
			String code = UUID.randomUUID().toString().substring(0, 6);
			verifyHasCode(email);
			sendMail(code, email);
			return code;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
		}
	}

	/* 소셜 로그인 시 임시 비밀번호 전송 */
	public MimeMessage createPasswordMail(String password, String email) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

		helper.setTo(email);
		helper.setSubject("Get A Beer 소셜로그인 비밀번호 입니다.");
		helper.setText("임시 비밀번호를 절대 유출하지 마세요.\n 임시 비밀번호: " + password, true);
		helper.setFrom("getabeer0310@gmail.com", "Get A Beer");

		return message;
	}

	public void sendPWMail(String password, String email) throws Exception {
		try {
			MimeMessage mimeMessage = createPasswordMail(password, email);
			javaMailSender.send(mimeMessage);
		} catch (MailException mailException) {
			mailException.printStackTrace();
			throw new IllegalAccessException();
		}
	}

	@Async("threadPoolTaskExecutor-Mail")
	public void sendPasswordMail(String email, String password) {

		try {
			sendPWMail(password, email);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
		}
	}

	/* 인증된 이메일 레디스 저장 */
	public void setVerifiedEmail(String email) {
		redisUtil.deleteData(email);
		redisUtil.setData(email, "true");
	}

	/* 인증 번호는 존재하지만 인증 안된 이메일인 경우 */
	private void verifyHasCode(String email) {
		if (Boolean.TRUE.equals(redisTemplate.hasKey(email))) {
			redisTemplate.delete(email);
		}
	}

	public void verifyEmail(String email) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
		}
	}
}
