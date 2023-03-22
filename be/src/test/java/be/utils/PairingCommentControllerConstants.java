package be.utils;

import java.time.LocalDateTime;
import java.util.List;

import be.domain.comment.dto.PairingCommentDto;

public class PairingCommentControllerConstants {

	public static final PairingCommentDto.Post PAIRING_COMMENT_POST_DTO =
		PairingCommentDto.Post.builder()
			.pairingId(1L)
			.content("코멘트 내용")
			.build();

	public static final PairingCommentDto.Response PAIRING_COMMENT_POST_RESPONSE_DTO =
		PairingCommentDto.Response.builder()
			.pairingId(1L)
			.pairingCommentId(1L)
			.userId(1L)
			.nickname("닉네임")
			.userImage("프로필 이미지")
			.content("코멘트 내용")
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.build();

	public static final PairingCommentDto.Patch PAIRING_COMMENT_PATCH_DTO =
		PairingCommentDto.Patch.builder()
			.content("코멘트 내용")
			.build();

	public static final List<PairingCommentDto.Response> PAIRING_COMMENT_POST_RESPONSE_LIST =
		List.of(PAIRING_COMMENT_POST_RESPONSE_DTO, PAIRING_COMMENT_POST_RESPONSE_DTO);
}
