package be.domain.rating.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;
import be.domain.user.entity.User;

public interface RatingCustomRepository {
	RatingResponseDto.Detail findDetailRatingResponse(Long ratingId);

	RatingTag findTagResponse(Long ratingId);

	List<RatingCommentDto.Response> findRatingCommentResponse(Long ratingId);

	Rating findRatingByUserId(Long userId);

	Page<RatingResponseDto.Total> findRatingTotalResponseOrder(Long beerId, Pageable pageable);

	Page<RatingResponseDto.Total> findRatingTotalResponseOrder(Long beerId, Long userId, Pageable pageable);

	Page<Rating> findRatingByUser(User user, Pageable pageable);
}
