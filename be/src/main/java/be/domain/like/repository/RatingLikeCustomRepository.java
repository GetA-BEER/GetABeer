package be.domain.like.repository;

import be.domain.like.entity.RatingLike;

public interface RatingLikeCustomRepository {
	RatingLike findRatingLike(Long ratingId, Long userId);
	int findRatingLikeUser(Long ratingId, Long userId);
}
