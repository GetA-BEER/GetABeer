package be.utils;

import java.util.List;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.entity.enums.Age;
import be.domain.user.entity.enums.Gender;

public class UserControllerConstants {

	public static final UserDto.RegisterPost USER_REGISTER_POST_DTO =
		UserDto.RegisterPost.builder()
			.email("e@mail.com")
			.nickname("닉네임")
			.password("password1@")
			.build();

	public static final UserDto.UserInfoPost USER_INFO_POST_DTO =
		UserDto.UserInfoPost.builder()
			.gender(Gender.FEMALE)
			.age(Age.TWENTIES)
			.userBeerCategories(List.of("ALE"))
			.userBeerTags(List.of("SWEET"))
			.build();

	public static final UserDto.UserInfoResponse USER_INFO_RESPONSE_DTO =
		UserDto.UserInfoResponse.builder()
			.imageUrl("이미지 주소")
			.nickname("닉네임")
			.age(Age.TWENTIES)
			.gender(Gender.FEMALE)
			.followingCount(10L)
			.followerCount(10L)
			.userBeerCategories(List.of("ALE"))
			.userBeerTags(List.of("SWEET"))
			.build();

	public static final UserDto.EditUserInfo USER_EDIT_INFO_DTO =
		UserDto.EditUserInfo.builder()
			.imageUrl("이미지 주소")
			.nickname("닉네임")
			.gender(Gender.FEMALE)
			.age(Age.TWENTIES)
			.userBeerCategories(List.of("ALE"))
			.userBeerTags(List.of("SWEET"))
			.build();

	public static final UserDto.Login USER_LOGIN_DTO =
		UserDto.Login.builder()
			.email("e@mail.com")
			.password("password1@")
			.build();

	public static final UserDto.UserInfoResponse USER_INFO_RESPONSE =
		UserDto.UserInfoResponse.builder()
			.imageUrl("프로필 사진")
			.nickname("닉네임")
			.gender(Gender.FEMALE)
			.age(Age.TWENTIES)
			.followerCount(10L)
			.followingCount(10L)
			.userBeerCategories(List.of("ALE"))
			.userBeerTags(List.of("SWEET"))
			.build();

	public static final User USER_FOR_AUTHUSER =
		User.builder()
			.id(1L)
			.age(Age.TWENTIES)
			.gender(Gender.FEMALE)
			.roles(List.of("ROLE_USER"))
			.nickname("닉네임")
			.email("e@mail.com")
			.password("password1@")
			.provider("NONE")
			.build();

	public static final UserDto.EditPassword EDIT_PASSWORD_DTO =
		UserDto.EditPassword.builder()
			.oldPassword("password1@")
			.newPassword("password1@@")
			.newVerifyPassword("password1@@")
			.build();
}
