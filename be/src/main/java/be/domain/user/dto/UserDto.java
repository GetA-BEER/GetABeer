package be.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

	@Getter
	@Builder
	public static class RegisterPost {

		@NotBlank
		private String email;

		@NotBlank
		private String nickname;

		@NotBlank
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*?\\d)(?=.*?[#?!@$%^&*-]).{8,}$",
				 message = "비밀번호는 8자 이상 특수문자와 영어 대소문자, 숫자만 허용됩니다.")
		private String password;
	}

	@Getter
	@Builder
	public static class UserInfoPost {

		@NotBlank
		private String email;

		@NotNull
		private Gender gender;

		@NotNull
		private Age age;

		// @NotEmpty
		// @Size(max = 4, message = "관심 태그는 최대 4개까지 선택할 수 있습니다.")
		// private List<UserBeerTag> userBeerTags;
	}

	@Getter
	@Builder
	public static class Patch {

		@NotBlank
		private String email;

		@NotBlank
		private String nickname;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Response {
		private String email;
		private String nickname;
		private String image;
	}

	@Getter
	public static class Login {

		@NotBlank
		private String email;

		@NotBlank
		private String password;
	}
}
