package be.domain.pairing.service.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.domain.like.repository.PairingLikeRepository;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.PairingCategory;
import be.domain.pairing.repository.PairingRepository;
import be.domain.user.entity.User;


/* 로그인 안한 유저 카테고리별 조회 */
public class GetCategoryUserNull implements PairingState{
	@Override
	public Page<PairingResponseDto.Total> getPairingResponse(User user, String category, String type, Long beerId,
		Pageable pageable, PairingRepository pairingRepository, PairingLikeRepository pairingLikeRepository) {
		PairingCategory pairingCategory = PairingCategory.to(category.toUpperCase());
		Page<PairingResponseDto.Total> responses =
			pairingRepository.findPairingTotalResponseGetCategory(beerId, type, pairingCategory, pageable);
		responses.forEach(
			pairing -> {
				pairing.addUserLike(false);
				pairing.addCategory(pairingCategory);
			}
		);

		return responses;
	}
}
