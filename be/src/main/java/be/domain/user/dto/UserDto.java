package be.domain.user.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import be.domain.user.entity.UserBeerCategory;
import be.domain.user.entity.UserBeerTag;
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
	public static class UserInfoPost {

		@NotBlank
		private String email;

		@NotNull
		private Gender gender;

		@NotNull
		private Age age;

		// @NotEmpty
		// @Size(max = 2, message = "선호 맥주는 최대 2개까지 선택할 수 있습니다.")
		// private List<UserBeerCategory> userBeerCategories;

		// @NotNull
		// @Size(max = 4, message = "관심 태그는 최대 4개까지 선택할 수 있습니다.")
		// private List<UserBeerTag> userBeerTags;
	}

	@Getter
	@Builder
	public static class EditUserInfo {

		private String imageUrl;

		private String nickname;

		private Gender gender;

		private Age age;

		// @Size(max = 2, message = "선호 맥주는 최대 2개까지 선택할 수 있습니다.")
		// private List<UserBeerCategory> userBeerCategories;

		// @Size(max = 4, message = "관심 태그는 최대 4개까지 선택할 수 있습니다.")
		// private List<UserBeerTag> userBeerTags;
	}

	@Getter
	@Builder
	public static class EditPassword {

		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*?\\d)(?=.*?[#?!@$%^&*-]).{8,}$",
				 message = "비밀번호는 8자 이상 특수문자와 영어 대소문자, 숫자만 허용됩니다.")
		private String oldPassword;

		@NotBlank
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*?\\d)(?=.*?[#?!@$%^&*-]).{8,}$",
				 message = "비밀번호는 8자 이상 특수문자와 영어 대소문자, 숫자만 허용됩니다.")
		private String newPassword;

		@NotBlank
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*?\\d)(?=.*?[#?!@$%^&*-]).{8,}$",
				 message = "비밀번호는 8자 이상 특수문자와 영어 대소문자, 숫자만 허용됩니다.")
		private String newVerifyPassword;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class UserInfoResponse {

		private String imageUrl;

		private String nickname;

		private Gender gender;

		private Age age;

		// @Size(max = 2, message = "선호 맥주는 최대 2개까지 선택할 수 있습니다.")
		// private List<UserBeerCategory> userBeerCategories;

		// @Size(max = 4, message = "관심 태그는 최대 4개까지 선택할 수 있습니다.")
		// private List<UserBeerTag> userBeerTags;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Response {
		private String email;
		private String nickname;
		private String imageUrl;
	}

	@Getter
	public static class Login {

		@NotBlank
		private String email;

		@NotBlank
		private String password;
	}
}
