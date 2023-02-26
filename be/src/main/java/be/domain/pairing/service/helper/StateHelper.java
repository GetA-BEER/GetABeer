package be.domain.pairing.service.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.like.repository.PairingLikeRepository;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.repository.PairingRepository;
import be.domain.user.entity.User;

public class StateHelper {
	private PairingState state; /* 기본을 getAllUserNull*/

	public StateHelper() {
		state = new GetAllUserNull();
	}

	public Page<PairingResponseDto.Total> getPairingResponsePage(User user, String category, String type, Long beerId,
		Pageable pageable, PairingRepository pairingRepository, PairingLikeRepository pairingLikeRepository) {

		if (!userIsNull(user) && !categoryIsAll(category)) { /* 유저가 존재하고, 카테고리가 존재한다면 */
			changeState(new GetCategoryUserNotNull());
		} else if (!userIsNull(user) && categoryIsAll(category)) { /* 유저가 존재하고, 카테고리가 전체라면 */
			changeState(new GetAllUserNotNull());
		} else if (userIsNull(user) && !categoryIsAll(category)){ /* 유저가 존재하지 않고, 카테고리가 존재한다면 */
			changeState(new GetCategoryUserNotNull());
		} else {
		}

		return state
			.getPairingResponse(user, category, type, beerId, pageable, pairingRepository, pairingLikeRepository);
	}

	private void changeState(final PairingState state) {
		this.state = state;
	}

	private boolean userIsNull(User user) {

		return user == null;
	}

	private boolean categoryIsAll(String category) {

		return category.equalsIgnoreCase("ALL");
	}
}
