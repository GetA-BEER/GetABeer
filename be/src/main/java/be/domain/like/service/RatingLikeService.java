package be.domain.like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.like.dto.LikeResponseDto;
import be.domain.like.entity.LikeStatus;
import be.domain.like.entity.RatingLike;
import be.domain.like.repository.RatingLikeRepository;
import be.domain.rating.entity.Rating;
import be.domain.rating.repository.RatingRepository;
import be.domain.rating.service.RatingService;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingLikeService {
	private final UserService userService;
	private final RatingService ratingService;
	private final RatingRepository ratingRepository;
	private final RatingLikeRepository ratingLikeRepository;

	@Transactional
	public LikeResponseDto clickLike(Long ratingId) {
		User user = userService.getLoginUser();
		Rating rating = ratingService.findRating(ratingId);

		int isUserLiked = isUserLikedRating(rating.getId(), user.getId());

		if (isUserLiked == 0) {
			RatingLike ratingLike = RatingLike.builder()
				.ratingLikeStatus(LikeStatus.LIKE)
				.user(user)
				.rating(rating)
				.build();

			ratingLikeRepository.save(ratingLike);
			rating.calculateLikes(rating.getRatingLikeList().size());
			ratingRepository.save(rating);

			return LikeResponseDto.builder()
				.isUserLikes(true)
				.build();
		} else {
			RatingLike ratingLike = findRatingLike(rating.getId(), user.getId());
			ratingLikeRepository.delete(ratingLike);

			rating.calculateLikes(rating.getRatingLikeList().size());
			ratingRepository.save(rating);

			return LikeResponseDto.builder()
				.isUserLikes(false)
				.build();
		}
	}

	// ---------------------------------------------------------------------------------------------------------------
	public int isUserLikedRating(Long ratingId, Long userId) {

		return ratingLikeRepository.findRatingLikeUser(ratingId, userId);
	}

	private RatingLike findRatingLike(Long ratingId, Long userId) {

		return ratingLikeRepository.findRatingLike(ratingId, userId);
	}
}
