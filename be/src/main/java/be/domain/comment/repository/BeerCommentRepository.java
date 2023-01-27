package be.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.comment.entity.BeerComment;

@Repository
public interface BeerCommentRepository extends JpaRepository<BeerComment, Long>, BeerCommentCustomRepository {
}
