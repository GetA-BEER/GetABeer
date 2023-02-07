package be.domain.mail.service;

import java.util.Optional;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

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
	private final JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	private final RedisUtil redisUtil;

	private MimeMessage createMessage(String code, String email) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

		// message.addRecipients(Message.RecipientType.TO, email);
		// message.setSubject("Get A Beer 이메일 인증 코드입니다.");
		// message.setText("이메일 인증코드: " + code);
		//
		// message.setFrom(new InternetAddress("getabeer0310@gmail.com"));

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

		redisUtil.setDataExpire(code, email, 60 * 5L);
	}

	@Async("threadPoolTaskExecutor-Mail")
	public String sendCertificationMail(String email) throws BusinessLogicException {
		if (nullUser(email).isPresent()) {
			throw new BusinessLogicException(ExceptionCode.USER_ID_EXISTS);
		}

		redisUtil.setData(email, "false");

		try {
			String code = UUID.randomUUID().toString().substring(0, 6);
			sendMail(code, email);
			return code;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new BusinessLogicException(ExceptionCode.USER_ID_EXISTS);
		}
	}

	public Optional<User> nullUser(String email) {
		return userRepository.findByEmail(email);
	}

	/* 인증된 이메일 레디스 저장 */
	public void setVerifiedEmail(String email) {
		redisUtil.deleteData(email);
		redisUtil.setDataExpire(email, "true", 60 * 30L);
	}
}
