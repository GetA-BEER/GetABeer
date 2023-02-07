package be.domain.rating.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.RatingTag;

public interface RatingCustomRepository {
	RatingResponseDto.Detail findDetailRatingResponse(Long ratingId);

	RatingTag findTagResponse(Long ratingId);

	List<RatingCommentDto.Response> findRatingCommentResponse(Long ratingId);

	Page<RatingResponseDto.Total> findRatingTotalResponseOrderByRecent(Long beerId, Pageable pageable);

	Page<RatingResponseDto.Total> findRatingTotalResponseOrderByLikes(Long beerId, Pageable pageable);

	Page<RatingResponseDto.Total> findRatingTotalResponseOrderByComments(Long beerId, Pageable pageable);
}
