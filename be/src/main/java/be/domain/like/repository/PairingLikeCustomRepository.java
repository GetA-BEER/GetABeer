package be.domain.like.repository;

import be.domain.like.entity.PairingLike;

public interface PairingLikeCustomRepository {
	PairingLike findPairingLike(Long pairingId, Long userId);
	int findPairingLikeUser(Long pairingId, Long userId);
}
