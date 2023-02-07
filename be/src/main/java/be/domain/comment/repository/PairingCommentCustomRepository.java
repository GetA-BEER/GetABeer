package be.domain.comment.repository;

import java.util.List;

import be.domain.comment.dto.PairingCommentDto;

public interface PairingCommentCustomRepository {

	List<PairingCommentDto.Response> findPairingCommentList(Long pairingId);
}
