package be.domain.mail.service;

import java.util.Optional;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import be.domain.user.entity.User;
import be.domain.user.repository.UserRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender javaMailSender;
	private final UserRepository userRepository;

	private MimeMessage createMessage(String code, String email) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();

		message.addRecipients(Message.RecipientType.TO, email);
		message.setSubject("Get A Beer 이메일 인증 번호입니다.");
		message.setText("이메일 인증코드: " + code);

		message.setFrom(new InternetAddress("getabeer0310@gmail.com"));

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
	}

	public String sendCertificationMail(String email) throws BusinessLogicException {
		// if(nullUser(email).isPresent()) {
		// 	throw new BusinessLogicException(ExceptionCode.USER_ID_EXISTS);
		// }

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
}
