package be.domain.pairing.service.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.like.repository.PairingLikeRepository;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.repository.PairingRepository;
import be.domain.user.entity.User;

public interface SortingState {
	Page<PairingResponseDto.Total> sorting(User user, String category, String type, Long beerId,
		Pageable pageable, PairingRepository pairingRepository, PairingLikeRepository pairingLikeRepository);
}
