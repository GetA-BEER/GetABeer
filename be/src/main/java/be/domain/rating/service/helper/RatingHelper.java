package be.domain.rating.service.helper;

import be.domain.user.entity.User;

public class RatingHelper {
	private RatingState state;

	private void changeState(RatingState state) {
		this.state = state;
	}

	private boolean isUserNull(User user) {
		return user == null;
	}
}
