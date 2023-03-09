package be.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import be.domain.beertag.entity.BeerTagType;
import be.domain.rating.dto.RatingRequestDto;
import be.domain.rating.dto.RatingResponseDto;

public class RatingControllerConstants {

	public static final RatingRequestDto.Post RATING_POST_DTO =
		RatingRequestDto.Post.builder()
			.beerId(1L)
			.content("평가 내용")
			.star(4.5)
			.color("GOLD")
			.taste("BITTER")
			.flavor("MALTY")
			.carbonation("MIDDLE")
			.build();

	public static final RatingRequestDto.Patch RATING_PATCH_DTO =
		RatingRequestDto.Patch.builder()
			.beerId(1L)
			.content("평가 내용")
			.star(4.5)
			.color("GOLD")
			.taste("BITTER")
			.flavor("MALTY")
			.carbonation("MIDDLE")
			.build();

	public static final RatingResponseDto.Detail RATING_DETAIL_DTO =
		RatingResponseDto.Detail.builder()
			.beerId(1L)
			.korName("한글 이름")
			.ratingId(1L)
			.userId(1L)
			.nickname("닉네임")
			.userImage("프로필 이미지")
			.content("평가 내용")
			.ratingTag(List.of(BeerTagType.STRAW))
			.star(4.5)
			.likeCount(10)
			.commentCount(10)
			.ratingCommentList(new ArrayList<>())
			.isUserLikes(false)
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();
}
