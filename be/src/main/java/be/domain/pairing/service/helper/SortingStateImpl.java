package be.domain.pairing.service.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.like.repository.PairingLikeRepository;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingCategory;
import be.domain.pairing.repository.PairingRepository;
import be.domain.user.entity.User;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;

public class SortingStateImpl implements SortingState {

	@Override
	public Page<PairingResponseDto.Total> sorting(User user, String category, String type, Long beerId,
		Pageable pageable, PairingRepository pairingRepository, PairingLikeRepository pairingLikeRepository) {
		Page<PairingResponseDto.Total> response;

		if (user == null) {
			if (category.equalsIgnoreCase("ALL")) {
				UserState getAll = new GetAll();
				response = getAll.userNull(beerId, type, pageable, pairingRepository);
			} else {
				UserState getCategory = new GetCategory(category);
				response =  getCategory.userNull(beerId, type, pageable, pairingRepository);
			}
			response.forEach(pairing -> pairing.addUserLike(false));
		} else {
			if (category.equalsIgnoreCase("ALL")) {
				UserState getAll = new GetAll();
				response =  getAll.userNotNull(beerId, type, user.getId(), pageable, pairingRepository);
			} else {
				UserState getCategory = new GetCategory(category);
				response = getCategory.userNotNull(beerId, type, user.getId(), pageable, pairingRepository);
			}
			response.forEach(pairing ->
				pairing.addUserLike(getIsUserLikes(pairing.getPairingId(), user.getId(), pairingLikeRepository)));
		}
		response.forEach(pairing -> pairing.addCategory(findCategory(pairing.getPairingId(), pairingRepository)));

		return response;
	}

	/* 카테고리 정보 가져오기 */
	public PairingCategory findCategory(Long pairingId, PairingRepository pairingRepository) {
		Pairing pairing = pairingRepository.findById(pairingId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAIRING_NOT_FOUND));
		return pairing.getPairingCategory();
	}

	private Boolean getIsUserLikes(Long pairingId, Long userId, PairingLikeRepository pairingLikeRepository) {
		int userLikes = pairingLikeRepository.findPairingLikeUser(pairingId, userId);

		return userLikes != 0;
	}
}
