package be.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.comment.entity.PairingComment;

@Repository
public interface PairinCommentRepository extends JpaRepository<PairingComment, Long> {
}
