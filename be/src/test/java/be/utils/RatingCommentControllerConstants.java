package be.utils;

import java.time.LocalDateTime;
import java.util.List;

import be.domain.comment.dto.RatingCommentDto;

public class RatingCommentControllerConstants {

	public static final RatingCommentDto.Post RATING_COMMENT_POST_DTO =
		RatingCommentDto.Post.builder()
			.ratingId(1L)
			.content("코멘트 내용")
			.build();

	public static final RatingCommentDto.Response RATING_COMMENT_POST_RESPONSE_DTO =
		RatingCommentDto.Response.builder()
			.ratingId(1L)
			.ratingCommentId(1L)
			.userId(1L)
			.nickname("닉네임")
			.userImage("프로필 이미지")
			.content("코멘트 내용")
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();

	public static final RatingCommentDto.Patch RATING_COMMENT_PATCH_DTO =
		RatingCommentDto.Patch.builder()
			.content("코멘트 내용")
			.build();

	public static final List<RatingCommentDto.Response> RATING_COMMENT_POST_RESPONSE_LIST =
		List.of(RATING_COMMENT_POST_RESPONSE_DTO, RATING_COMMENT_POST_RESPONSE_DTO);
}
