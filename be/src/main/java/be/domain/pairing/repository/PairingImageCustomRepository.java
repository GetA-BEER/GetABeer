package be.domain.pairing.repository;

import java.util.List;

import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.entity.PairingImage;

public interface PairingImageCustomRepository {
	List<PairingImageDto.Response> findPairingImageList(Long pairingId);

	List<PairingImage> findPairingImage(Long pairingId);
}
