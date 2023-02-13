package be.domain.pairing.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.PairingImage;

public interface PairingCustomRepository {
	List<PairingImageDto.Response> findPairingImageList(Long pairingId);

	List<PairingImage> findPairingImage(Long pairingId);

	PairingResponseDto.Detail findPairingDetailResponseDto(Long pairingId);
	Page<PairingResponseDto.Total> findPairingTotalResponseOrderByRecent(Long beerId, Pageable pageable);

	Page<PairingResponseDto.Total> findPairingTotalResponseOrderByLikes(Long beerId, Pageable pageable);

	Page<PairingResponseDto.Total> findPairingTotalResponseOrderByComments(Long beerId, Pageable pageable);
}
