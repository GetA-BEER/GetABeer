package be.domain.like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.like.entity.LikeStatus;
import be.domain.like.entity.RatingLike;
import be.domain.like.repository.RatingLikeRepository;
import be.domain.rating.entity.Rating;
import be.domain.rating.repository.RatingRepository;
import be.domain.rating.service.RatingService;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingLikeService {
	private final UserService userService;
	private final RatingService ratingService;
	private final RatingRepository ratingRepository;
	private final RatingLikeRepository ratingLikeRepository;

	public String clickLike(Long ratingId) {
		User user = userService.getLoginUser();
		Rating rating = ratingService.findRating(ratingId);

		isWriterUser(rating.getUser().getId(), user.getId());

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

			return "추천되었습니다.";
		} else {
			RatingLike ratingLike = findRatingLike(rating.getId(), user.getId());
			ratingLikeRepository.delete(ratingLike);

			rating.calculateLikes(rating.getRatingLikeList().size());
			ratingRepository.save(rating);

			return "추천이 취소되었습니다.";
		}
	}

	// ---------------------------------------------------------------------------------------------------------------
	public int isUserLikedRating(Long ratingId, Long userId) {

		return ratingLikeRepository.findRatingLikeUser(ratingId, userId);
	}

	private RatingLike findRatingLike(Long ratingId, Long userId) {

		return ratingLikeRepository.findRatingLike(ratingId, userId);
	}
	private void isWriterUser(Long ratingUserId, Long userId) {
		if (ratingUserId.equals(userId)) {
			throw new BusinessLogicException(ExceptionCode.NOT_LIKE_WRITER);
		}
	}
}
