package be.utils;

import be.domain.like.dto.LikeResponseDto;

public class LikeControllerConstants {

	public static final LikeResponseDto LIKE_RESPONSE_DTO =
		LikeResponseDto.builder()
			.isUserLikes(true)
			.build();
}
