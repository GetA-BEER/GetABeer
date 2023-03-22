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

/* 로그인 유저 전체 조회 */
public class GetAllUserNotNull implements PairingState{
	@Override
	public Page<PairingResponseDto.Total> getPairingResponse(User user, String category, String type, Long beerId,
		Pageable pageable, PairingRepository pairingRepository, PairingLikeRepository pairingLikeRepository) {
		Page<PairingResponseDto.Total> responses =
			pairingRepository.findPairingTotalResponseGetALL(beerId, type, user.getId(), pageable);
		responses.forEach(
			pairing -> {
				pairing.addUserLike(getIsUserLikes(pairing.getPairingId(), user.getId(), pairingLikeRepository));
				pairing.addCategory(findCategory(pairing.getPairingId(), pairingRepository));
			}
		);

		return responses;
	}

	private Boolean getIsUserLikes(Long pairingId, Long userId, PairingLikeRepository pairingLikeRepository) {
		int userLikes = pairingLikeRepository.findPairingLikeUser(pairingId, userId);

		return userLikes != 0;
	}

	public PairingCategory findCategory(Long pairingId, PairingRepository pairingRepository) {
		Pairing pairing = pairingRepository.findById(pairingId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAIRING_NOT_FOUND));
		return pairing.getPairingCategory();
	}
}
