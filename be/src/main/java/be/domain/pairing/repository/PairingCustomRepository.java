package be.domain.pairing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.user.entity.User;

public interface PairingCustomRepository {

	PairingResponseDto.Detail findPairingDetailResponseDto(Long pairingId);

	Page<PairingResponseDto.Total> findPairingTotalResponseOrder(Long beerId, Pageable pageable);

	// ------------------------------------- 로그인 한 유저 -------------------------------------------------------------
	Page<PairingResponseDto.Total> findPairingTotalResponseOrder(Long beerId, Long userId, Pageable pageable);

	// ---------------------------------------------------------------------------------------------------------------
	Page<Pairing> findPairingByUser(User user, Pageable pageable);
}
