package be.domain.pairing.repository;

import java.util.List;

import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;
import be.domain.pairing.entity.PairingImage2;

public interface PairingCustomRepository {
	List<PairingImageDto.Response2> findPairingImageList(Long pairingId);

	List<PairingImage2> findPairingImage(Long pairingId);
}
