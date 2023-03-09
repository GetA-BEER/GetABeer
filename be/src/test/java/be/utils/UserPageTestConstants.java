package be.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.beertag.entity.BeerTagType;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.PairingCategory;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.user.dto.MyPageMultiResponseDto;

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
}
