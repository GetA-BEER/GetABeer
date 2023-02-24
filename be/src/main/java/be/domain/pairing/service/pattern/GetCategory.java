package be.domain.pairing.service.pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.PairingCategory;
import be.domain.pairing.repository.PairingRepository;

public class GetCategory implements UserState{
	private final String category;

	public GetCategory(String category) {
		this.category = category;
	}

	@Override
	public Page<PairingResponseDto.Total> userNull(Long beerId, String type,
		Pageable pageable, PairingRepository pairingRepository) {
		PairingCategory pairingCategory = PairingCategory.to(category);

		return pairingRepository.findPairingTotalResponseGetCategory(beerId, type, pairingCategory, pageable);
	}

	@Override
	public Page<PairingResponseDto.Total> userNotNull(Long beerId, String type, Long userId,
		Pageable pageable, PairingRepository pairingRepository) {
		PairingCategory pairingCategory = PairingCategory.to(category);

		return pairingRepository.findPairingTotalResponseGetCategory(beerId, type, userId, pairingCategory, pageable);
	}
}
