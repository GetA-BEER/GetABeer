package be.domain.mail.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MailDto {

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class postEmail {

		@NotBlank
		@Email(message = "올바른 이메일 형식이 아닙니다.")
		private String email;
	}

	@Getter
	@Builder
	public static class checkMail {

		@NotBlank
		private String code;
		private String email;
	}
}
