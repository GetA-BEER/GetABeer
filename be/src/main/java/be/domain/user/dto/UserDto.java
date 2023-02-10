package be.domain.user.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NotFound;

import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beertag.dto.BeerTagDto;
import be.domain.beertag.entity.BeerTagType;
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
		@NotFound
		private String nickname;

		@NotBlank
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*?\\d)(?=.*?[#?!@$%^&*-]).{8,}$",
			message = "비밀번호는 8자 이상 특수문자와 영어 대소문자, 숫자만 허용됩니다.")
		private String password;
	}

	@Getter
	@Builder
	public static class UserInfoPost {

		@NotNull
		private Gender gender;

		@NotNull
		private Age age;

		@NotNull
		@Size(max = 2, message = "선호 맥주는 최대 2개까지 선택할 수 있습니다.")
		private List<BeerCategoryDto.Response> userBeerCategories;

		@NotNull
		@Size(max = 4, message = "관심 태그는 최대 4개까지 선택할 수 있습니다.")
		private List<BeerTagDto.Response> userBeerTags;
	}

	@Getter
	@Builder
	public static class EditUserInfo {

		private String imageUrl;

		private String nickname;

		private Gender gender;

		private Age age;

		@Size(max = 2, message = "선호 맥주는 최대 2개까지 선택할 수 있습니다.")
		private List<BeerCategoryDto.Response> userBeerCategories;

		@Size(max = 4, message = "관심 태그는 최대 4개까지 선택할 수 있습니다.")
		private List<BeerTagDto.Response> userBeerTags;
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

		private List<BeerCategoryType> userBeerCategories;

		private List<BeerTagType> userBeerTags;
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
