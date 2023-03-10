package be.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.follow.dto.FollowDto;

public class FollowControllerConstants {

	public static final FollowDto.FollowerResponse FOLLOWER_RESPONSE_DTO =
		FollowDto.FollowerResponse.builder()
			.userId(1L)
			.nickname("닉네임")
			.imageUrl("프로필 사진")
			.isFollowing(false)
			.build();

	public static final PageImpl<FollowDto.FollowerResponse> FOLLOWER_RESPONSE_PAGE =
		new PageImpl<>(List.of(FOLLOWER_RESPONSE_DTO, FOLLOWER_RESPONSE_DTO));

	public static final FollowDto.FollowingResponse FOLLOWING_RESPONSE_DTO =
		FollowDto.FollowingResponse.builder()
			.userId(1L)
			.nickname("닉네임")
			.imageUrl("프로필 사진")
			.isFollowing(false)
			.build();

	public static final PageImpl<FollowDto.FollowingResponse> FOLLOWING_RESPONSE_PAGE =
		new PageImpl<>(List.of(FOLLOWING_RESPONSE_DTO, FOLLOWING_RESPONSE_DTO));

}
