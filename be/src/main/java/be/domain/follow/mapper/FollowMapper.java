package be.domain.follow.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.follow.dto.FollowDto;
import be.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface FollowMapper {

	default PageImpl<FollowDto.FollowerResponse> followersToFollowerResponses(
		Page<User> findFollowers, List<User> findFollowingsList) {

		return new PageImpl<>(findFollowers.stream()
			.map(user -> {

				FollowDto.FollowerResponse.FollowerResponseBuilder followerResponseBuilder = FollowDto.FollowerResponse.builder();

				followerResponseBuilder.userId(user.getId());
				followerResponseBuilder.nickname(user.getNickname());
				followerResponseBuilder.imageUrl(user.getImageUrl());

				if (findFollowingsList.contains(user)) {
					followerResponseBuilder.isFollowing(true);
				} else {
					followerResponseBuilder.isFollowing(false);
				}

				return followerResponseBuilder.build();

			}).collect(Collectors.toList()));
	}

	default PageImpl<FollowDto.FollowingResponse> followingsToFollowingResponses(
		Page<User> findFollowers, List<User> findFollowingsList) {

		return new PageImpl<>(findFollowers.stream()
			.map(user -> {

				FollowDto.FollowingResponse.FollowingResponseBuilder followingResponseBuilder = FollowDto.FollowingResponse.builder();

				followingResponseBuilder.userId(user.getId());
				followingResponseBuilder.nickname(user.getNickname());
				followingResponseBuilder.imageUrl(user.getImageUrl());

				if (findFollowingsList.contains(user)) {
					followingResponseBuilder.isFollowing(true);
				} else {
					followingResponseBuilder.isFollowing(false);
				}

				return followingResponseBuilder.build();

			}).collect(Collectors.toList()));
	}
}
