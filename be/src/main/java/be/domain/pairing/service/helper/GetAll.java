package be.domain.pairing.service.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.repository.PairingRepository;

public class GetAll implements UserState {


	@Override
	public Page<PairingResponseDto.Total> userNull(Long beerId, String type,
		Pageable pageable, PairingRepository pairingRepository) {

		return pairingRepository.findPairingTotalResponseGetALL(beerId, type, pageable);
	}

	@Override
	public Page<PairingResponseDto.Total> userNotNull(Long beerId, String type, Long userId,
		Pageable pageable, PairingRepository pairingRepository) {

		return pairingRepository.findPairingTotalResponseGetALL(beerId, type, userId, pageable);
	}
}
