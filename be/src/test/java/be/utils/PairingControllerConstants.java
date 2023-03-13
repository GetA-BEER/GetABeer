package be.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.PairingRequestDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.PairingCategory;

public class PairingControllerConstants {

	public static final PairingRequestDto.Post PAIRING_POST_DTO =
		PairingRequestDto.Post.builder()
			.beerId(1L)
			.content("페어링 내용")
			.category("FRIED")
			.build();

	public static final PairingRequestDto.Patch PAIRING_PATCH_DTO =
		PairingRequestDto.Patch.builder()
			.beerId(1L)
			.content("페어링 내용")
			.type(List.of("요청 타입"))
			.url(List.of(1L))
			.category("FRIED")
			.build();

	public static final PairingResponseDto.Detail PAIRING_DETAIL_RESPONSE_DTO =
		PairingResponseDto.Detail.builder()
			.beerId(1L)
			.korName("한글 이름")
			.pairingId(1L)
			.userId(1L)
			.nickname("닉네임")
			.userImage("프로필 사진")
			.content("페어링 내용")
			.thumbnail("섬네일")
			.imageList(new ArrayList<>())
			.commentList(new ArrayList<>())
			.category(PairingCategory.FRIED)
			.likeCount(10)
			.commentCount(10)
			.isUserLikes(false)
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();

	public static final PairingResponseDto.Total PAIRING_TOTAL_DTO =
		PairingResponseDto.Total.builder()
			.beerId(1L)
			.korName("한글 이름")
			.pairingId(1L)
			.userId(1L)
			.nickname("닉네임")
			.userImage("프로필 사진")
			.content("페어링 내용")
			.thumbnail("섬네일")
			.category(PairingCategory.FRIED)
			.likeCount(10)
			.commentCount(10)
			.isUserLikes(false)
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();

	public static final Page<PairingResponseDto.Total> PAIRING_TOTAL_PAGE =
		new PageImpl<>(List.of(PAIRING_TOTAL_DTO, PAIRING_TOTAL_DTO));

}
