package be.domain.comment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.entity.PairingComment;
import be.domain.user.entity.User;

public interface PairingCommentCustomRepository {

	List<PairingCommentDto.Response> findPairingCommentList(Long pairingId);

	Page<PairingComment> findPairingCommentByUser(User user, Pageable pageable);
}
