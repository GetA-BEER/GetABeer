package be.domain.comment.repository.pairing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.comment.entity.PairingComment;

@Repository
public interface PairingCommentRepository extends JpaRepository<PairingComment, Long>, PairingCommentCustomRepository {
}
