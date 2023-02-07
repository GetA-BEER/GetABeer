package be.domain.comment.mapper;

import org.mapstruct.Mapper;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.entity.PairingComment;

@Mapper(componentModel = "spring")
public interface PairingCommentMapper {

	PairingComment postPairingCommentDtoToPairingComment(PairingCommentDto.Post post);
	PairingComment patchPairingCommentDtoToPairingComment(PairingCommentDto.Patch patch);

	default PairingCommentDto.Response pairingCommentToPairingResponse(PairingComment pairingComment) {
		if (pairingComment == null) {
			return null;
		}

		return PairingCommentDto.Response.builder()
			.pairingId(pairingComment.getPairing().getId())
			.pairingCommentId(pairingComment.getId())
			.userId(pairingComment.getUser().getId())
			.nickname(pairingComment.getUser().getNickname())
			.content(pairingComment.getContent())
			.createdAt(pairingComment.getCreatedAt())
			.modifiedAt(pairingComment.getModifiedAt())
			.build();
	}
}
