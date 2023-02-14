package be.domain.comment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.RatingComment;
import be.domain.user.entity.User;

public interface RatingCommentCustomRepository {
	List<RatingCommentDto.Response> findRatingCommentList(Long ratingId);

	Page<RatingComment> findRatingCommentByUser(User user, Pageable pageable);
}
