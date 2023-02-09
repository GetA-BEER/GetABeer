package be.domain.user.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import be.domain.beercategory.dto.BeerCategoryDto;
import be.domain.beercategory.entity.BeerCategory;
import be.domain.beercategory.entity.BeerCategoryType;
import be.domain.beertag.dto.BeerTagDto;
import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.entity.BeerTagType;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.entity.UserBeerCategory;
import be.domain.user.entity.UserBeerTag;

@Mapper(componentModel = "spring")
public interface UserMapper {
	default User postToUser(UserDto.RegisterPost post) {
		return User.builder()
			.email(post.getEmail())
			.password(post.getPassword())
			.nickname(post.getNickname())
			.build();
	}

	default UserDto.UserInfoResponse userToInfoResponse(User user) {
		List<BeerTagType> userBeerTags = user.getUserBeerTags().stream().map(
			userBeerTag -> userBeerTag.getBeerTag().getBeerTagType()
		).collect(Collectors.toList());

		List<BeerCategoryType> userBeerCategories = user.getUserBeerCategories().stream().map(
			userBeerCategory -> userBeerCategory.getBeerCategory().getBeerCategoryType()
		).collect(Collectors.toList());

		return UserDto.UserInfoResponse.builder()
			.imageUrl(user.getImageUrl())
			.nickname(user.getNickname())
			.age(user.getAge())
			.gender(user.getGender())
			.userBeerTags(userBeerTags)
			.userBeerCategories(userBeerCategories)
			.build();
	}

	default User infoPostToUser(Long id, UserDto.UserInfoPost postInfo) {
		User user = new User();
		user.setId(id);
		user.setUserInfo(postInfo.getAge(), postInfo.getGender());

		List<UserBeerTag> userBeerTags = getUserBeerTag(postInfo.getUserBeerTags());
		user.setUserBeerTags(userBeerTags);

		List<UserBeerCategory> userBeerCategories = getUserBeerCategory(postInfo.getUserBeerCategories());
		user.setUserBeerCategories(userBeerCategories);

		return user;
	}

	default User editToUser(UserDto.EditUserInfo edit) {
		User user = new User();
		user.edit(edit.getImageUrl(), edit.getNickname(), edit.getGender(), edit.getAge());

		if (edit.getUserBeerTags() != null) {
			List<UserBeerTag> userBeerTags = getUserBeerTag(edit.getUserBeerTags());
			user.setUserBeerTags(userBeerTags);
		}

		if (edit.getUserBeerCategories() != null) {
			List<UserBeerCategory> userBeerCategories = getUserBeerCategory(edit.getUserBeerCategories());
			user.setUserBeerCategories(userBeerCategories);
		}

		return user;
	}

	private static List<UserBeerTag> getUserBeerTag(List<BeerTagDto.Response> responses) {
		return responses.stream().map(
			response -> UserBeerTag.builder()
				.beerTag(BeerTag.builder()
							 .id(response.getBeerTagId())
							 .beerTagType(response.getBeerTagType())
							 .build())
				.build()).collect(Collectors.toList());
	}

	private static List<UserBeerCategory> getUserBeerCategory(List<BeerCategoryDto.Response> responses) {
		return responses.stream().map(
			response -> UserBeerCategory.builder()
				.beerCategory(BeerCategory.builder()
								  .id(response.getBeerCategoryId())
								  .beerCategoryType(response.getBeerCategoryType())
								  .build())
				.build()).collect(Collectors.toList());
	}
}
