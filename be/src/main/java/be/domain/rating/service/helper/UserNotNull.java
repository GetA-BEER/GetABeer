package be.domain.rating.service.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.like.repository.RatingLikeRepository;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.repository.RatingRepository;
import be.domain.user.entity.User;

public class UserNotNull implements RatingState{
	@Override
	public Page<RatingResponseDto.Total> getRatingResponse(User user, String type, Long beerId, Pageable pageable,
		RatingRepository ratingRepository, RatingLikeRepository ratingLikeRepository) {
		Page<RatingResponseDto.Total> responses =
			ratingRepository.findRatingTotalResponseUserNotNull(beerId, user.getId(), pageable, type);
		responses.forEach(rating ->
			rating.addUserLike(getIsUserLikes(rating.getRatingId(), user.getId(), ratingLikeRepository)));

		return responses;
	}

	/* 추천 여부 가져오기 */
	private Boolean getIsUserLikes(Long ratingId, Long userId, RatingLikeRepository ratingLikeRepository) {
		int userLikes = ratingLikeRepository.findRatingLikeUser(ratingId, userId);

		return userLikes != 0;
	}
}