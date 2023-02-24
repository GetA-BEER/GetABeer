package be.domain.pairing.service.pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.repository.PairingRepository;

public interface UserState {
	Page<PairingResponseDto.Total> userNull(Long beerId, String type, Pageable pageable,
		PairingRepository pairingRepository);
	Page<PairingResponseDto.Total> userNotNull(Long beerId, String type, Long userId, Pageable pageable,
		PairingRepository pairingRepository);
}
