package be.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.beertag.entity.BeerTagType;
import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.dto.RatingCommentDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.PairingCategory;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.user.dto.MyPageMultiResponseDto;
import be.domain.user.dto.UserDto;

public class UserPageTestConstants {

	public static final RatingResponseDto.MyPageResponse MY_RATING_RESPONSE_DTO =
		RatingResponseDto.MyPageResponse.builder()
			.beerId(1L)
			.ratingId(1L)
			.userId(1L)
			.nickname("닉네임")
			.userImage("프로필 사진")
			.content("내용")
			.ratingTag(List.of(BeerTagType.STRAW, BeerTagType.SWEET, BeerTagType.FRUITY, BeerTagType.WEAK))
			.star(4.3)
			.likeCount(10)
			.commentCount(10)
			.isUserLikes(false)
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();

	public static final Page<RatingResponseDto.MyPageResponse> MY_RATING_RESPONSE_PAGE =
		new PageImpl<>(List.of(MY_RATING_RESPONSE_DTO, MY_RATING_RESPONSE_DTO));

	public static final PairingResponseDto.Total MY_PAIRING_RESPONSE_DTO =
		PairingResponseDto.Total.builder()
			.beerId(1L)
			.korName("한글 이름")
			.pairingId(1L)
			.userId(1L)
			.nickname("닉네임")
			.userImage("프로필 사진")
			.content("내용")
			.thumbnail("페어링 사진")
			.category(PairingCategory.FRIED)
			.likeCount(10)
			.commentCount(10)
			.isUserLikes(false)
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();

	public static final Page<PairingResponseDto.Total> MY_PAIRING_RESPONSE_PAGE =
		new PageImpl<>(List.of(MY_PAIRING_RESPONSE_DTO, MY_PAIRING_RESPONSE_DTO));

	public static final RatingCommentDto.Response MY_RATING_COMMENT_RESPONSE_DTO =
		RatingCommentDto.Response.builder()
			.ratingId(1L)
			.ratingCommentId(1L)
			.userId(1L)
			.nickname("닉네임")
			.content("내용")
			.userImage("프로필 사진")
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();

	public static final Page<RatingCommentDto.Response> MY_RATING_COMMENT_RESPONSE_PAGE =
		new PageImpl<>(List.of(MY_RATING_COMMENT_RESPONSE_DTO, MY_RATING_COMMENT_RESPONSE_DTO));

	public static final PairingCommentDto.Response MY_PAIRING_COMMENT_RESPONSE_DTO =
		PairingCommentDto.Response.builder()
			.pairingId(1L)
			.pairingCommentId(1L)
			.userId(1L)
			.nickname("닉네임")
			.content("내용")
			.userImage("프로필 사진")
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();

	public static final Page<PairingCommentDto.Response> MY_PAIRING_COMMENT_RESPONSE_PAGE =
		new PageImpl<>(List.of(MY_PAIRING_COMMENT_RESPONSE_DTO, MY_PAIRING_COMMENT_RESPONSE_DTO));

	public static final UserDto.UserPageResponse USER_PAGE_RESPONSE_DTO =
		UserDto.UserPageResponse.builder()
			.id(1L)
			.nickname("닉네임")
			.imgUrl("프로필 이미지")
			.isFollowing(false)
			.followerCount(10L)
			.followingCount(10L)
			.ratingCount(10L)
			.pairingCount(10L)
			.commentCount(10L)
			.build();

	public static final RatingResponseDto.UserPageResponse USER_RATING_PAGE_RESPONSE_DTO =
		RatingResponseDto.UserPageResponse.builder()
			.beerId(1L)
			.ratingId(1L)
			.userId(1L)
			.nickname("닉네임")
			.userImage("프로필 사진")
			.content("내용")
			.ratingTag(List.of(BeerTagType.STRAW, BeerTagType.SWEET, BeerTagType.FRUITY, BeerTagType.WEAK))
			.star(4.3)
			.likeCount(10)
			.commentCount(10)
			.isUserLikes(false)
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();

	public static final PageImpl<RatingResponseDto.UserPageResponse> USER_RATING_PAGE_RESPONSE_PAGE =
		new PageImpl<>(List.of(USER_RATING_PAGE_RESPONSE_DTO, USER_RATING_PAGE_RESPONSE_DTO));

}
