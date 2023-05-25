package be.domain.rating.service.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.like.repository.RatingLikeRepository;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.repository.RatingRepository;
import be.domain.user.entity.User;

public class UserNull implements RatingState{
	@Override
	public Page<RatingResponseDto.Total> getRatingResponse(User user, String type, Long beerId, Pageable pageable,
		RatingRepository ratingRepository, RatingLikeRepository ratingLikeRepository) {
		Page<RatingResponseDto.Total> responses =
			ratingRepository.findRatingTotalResponseUserNull(beerId, pageable, type);
		responses.forEach(rating -> rating.addUserLike(false));

		return responses;
	}
}
