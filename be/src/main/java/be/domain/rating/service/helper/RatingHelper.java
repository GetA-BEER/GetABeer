package be.domain.rating.service.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.like.repository.RatingLikeRepository;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.repository.RatingRepository;
import be.domain.user.entity.User;

public class RatingHelper {
	private RatingState state;

	public RatingHelper() {
		state = new UserNull();
	}

	public Page<RatingResponseDto.Total> getRatingResponsePage(User user, String type, Long beerId, Pageable pageable,
		RatingRepository ratingRepository, RatingLikeRepository ratingLikeRepository) {
		if (!isUserNull(user)) { /* 유저가 존재한다면 */
			changeState(new UserNotNull());
		} else { /* 유저가 존재하지 않는다면 */
			changeState(new UserNull());
		}

		return state.getRatingResponse(user, type, beerId, pageable, ratingRepository, ratingLikeRepository);
	}

	private void changeState(RatingState state) {
		this.state = state;
	}

	private boolean isUserNull(User user) {

		return user == null;
	}
}
