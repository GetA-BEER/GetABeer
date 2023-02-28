package be.domain.comment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.entity.PairingComment;
import be.domain.pairing.dto.PairingResponseDto;

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
			.userImage(pairingComment.getUser().getImageUrl())
			.nickname(pairingComment.getUser().getNickname())
			.content(pairingComment.getContent())
			.createdAt(pairingComment.getCreatedAt())
			.modifiedAt(pairingComment.getModifiedAt())
			.build();
	}

	default Page<PairingCommentDto.Response> pairingCommentsToPageResponse(Page<PairingComment> pairingComments) {
		return new PageImpl<>(pairingComments.stream()
			.map(pairingComment ->
				new PairingCommentDto.Response(
					pairingComment.getPairing().getId(),
					pairingComment.getId(),
					pairingComment.getUser().getId(),
					pairingComment.getUser().getNickname(),
					pairingComment.getUser().getImageUrl(),
					pairingComment.getContent(),
					pairingComment.getCreatedAt(),
					pairingComment.getModifiedAt()
				)).collect(Collectors.toList()), pairingComments.getPageable(), pairingComments.getTotalElements());
	}
}
