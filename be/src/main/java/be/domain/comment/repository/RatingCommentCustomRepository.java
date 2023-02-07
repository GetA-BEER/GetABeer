package be.domain.comment.repository;

import java.util.List;

import be.domain.comment.dto.RatingCommentDto;

public interface RatingCommentCustomRepository {
	List<RatingCommentDto.Response> findRatingCommentList(Long ratingId);
}
