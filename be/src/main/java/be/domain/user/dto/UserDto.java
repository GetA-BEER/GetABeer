package be.domain.user.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NotFound;

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
		@Size(min = 2, max = 10, message = "2자에서 10자 이내로 가능합니다.")
		private String nickname;

		@NotBlank
		@Pattern(regexp = "^(?=.*?\\d{1,50})(?=.*?[~`!@#$%^&()-+=]{1,50})(?=.*?[a-zA-Z]{2,50}).{8,16}$",
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
		private List<String> userBeerCategories;

		@NotNull
		@Size(max = 4, message = "관심 태그는 최대 4개까지 선택할 수 있습니다.")
		private List<String> userBeerTags;
	}

	@Getter
	@Builder
	public static class EditUserInfo {

		private String imageUrl;

		@Size(min = 2, max = 10, message = "2자에서 10자 이내로 가능합니다.")
		private String nickname;

		private Gender gender;

		private Age age;

		@Size(max = 2, message = "선호 맥주는 최대 2개까지 선택할 수 있습니다.")
		private List<String> userBeerCategories;

		@Size(max = 4, message = "관심 태그는 최대 4개까지 선택할 수 있습니다.")
		private List<String> userBeerTags;
	}

	@Getter
	@Builder
	public static class EditPassword {

		@Pattern(regexp = "^(?=.*?\\d{1,50})(?=.*?[~`!@#$%^&()-+=]{1,50})(?=.*?[a-zA-Z]{2,50}).{8,16}$",
			message = "비밀번호는 8자 이상 특수문자와 영어 대소문자, 숫자만 허용됩니다.")
		private String oldPassword;

		@NotBlank
		@Pattern(regexp = "^(?=.*?\\d{1,50})(?=.*?[~`!@#$%^&()-+=]{1,50})(?=.*?[a-zA-Z]{2,50}).{8,16}$",
			message = "비밀번호는 8자 이상 특수문자와 영어 대소문자, 숫자만 허용됩니다.")
		private String newPassword;

		@NotBlank
		@Pattern(regexp = "^(?=.*?\\d{1,50})(?=.*?[~`!@#$%^&()-+=]{1,50})(?=.*?[a-zA-Z]{2,50}).{8,16}$",
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

		private Long followerCount;

		private Long followingCount;

		private List<String> userBeerCategories;

		private List<String> userBeerTags;
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
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Login {

		@NotBlank
		private String email;

		@NotBlank
		private String password;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class LoginResponse {
		private Long id;
		private String email;
		private String nickname;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class UserPageResponse {
		private Long id;
		private String nickname;
		private String imgUrl;
		private Boolean isFollowing;
		private Long followerCount;
		private Long followingCount;
		private Long ratingCount;
		private Long pairingCount;
		private Long commentCount;

		public void addIsFollowing(Boolean isFollowing) {
			this.isFollowing = isFollowing;
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class UserSearchResponse {
		private Long userId;
		private String nickname;
		private String imageUrl;
		private Boolean isFollowing;

		public void addIsFollowing(Boolean isFollowing) {
			this.isFollowing = isFollowing;
		}
	}

}
