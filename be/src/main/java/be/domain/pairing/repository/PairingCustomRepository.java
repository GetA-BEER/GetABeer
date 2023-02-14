package be.domain.pairing.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;
import be.domain.user.entity.User;

public interface PairingCustomRepository {
	List<PairingImageDto.Response> findPairingImageList(Long pairingId);

	List<PairingImage> findPairingImage(Long pairingId);

	PairingResponseDto.Detail findPairingDetailResponseDto(Long pairingId);
	Page<PairingResponseDto.Total> findPairingTotalResponseOrder(Long beerId, Pageable pageable);

	// ------------------------------------- 로그인 한 유저 -------------------------------------------------------------
	Page<PairingResponseDto.Total> findPairingTotalResponseOrder(Long beerId, Long userId, Pageable pageable);

	// ---------------------------------------------------------------------------------------------------------------
	Page<Pairing> findPairingByUser(User user, Pageable pageable);
}
