package be.domain.comment.repository.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.comment.entity.RatingComment;

@Repository
public interface RatingCommentRepository extends JpaRepository<RatingComment, Long>, RatingCommentCustomRepository {
}
