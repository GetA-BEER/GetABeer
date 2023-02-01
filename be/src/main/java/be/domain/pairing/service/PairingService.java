package be.domain.pairing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingCategory;
import be.domain.pairing.entity.PairingImage;
import be.domain.pairing.repository.PairingImageRepository;
import be.domain.pairing.repository.PairingRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;

@Service
public class PairingService {
	private final PairingRepository pairingRepository;
	private final PairingImageRepository paringImageRepository;
	private final BeerService beerService;

	public PairingService(PairingRepository pairingRepository, PairingImageRepository paringImageRepository,
		BeerService beerService) {
		this.pairingRepository = pairingRepository;
		this.paringImageRepository = paringImageRepository;
		this.beerService = beerService;
	}

	/* 페어링 등록 */
	@Transactional
	public Pairing create(Pairing pairing, PairingImage pairingImage, String category, Long beerId) {
		/* 존재하는 맥주인지 확인 */
		Beer beer = beerService.findVerifiedBeer(beerId);

		pairing.updateCategory(findCategory(category));

		/* 이미지 저장하기 */
		paringImageRepository.save(pairingImage);

		/* 페어링 등록하기 */
		pairing.saveDefault(beer, pairingImage, new ArrayList<>(), 0, 0);
		pairingRepository.save(pairing);

		return pairing;
	}

	/* 페어링 수정 */
	@Transactional
	public Pairing update(Pairing pairing, PairingImage pairingImage, long pairingId, String category) {

		/* 존재하는 페어링인지 확인 및 해당 페어링 정보 가져오기 */
		Pairing findPairing = findVerifiedPairing(pairingId);

		/* 수정할 내용이 존재하면, 해당 정보 수정 후 저장*/
		Optional.ofNullable(pairing.getContent()).ifPresent(findPairing::updateContent);
		if (category != null) {
			findPairing.updateCategory(findCategory(category));
		}

		Optional<PairingImage> optional = paringImageRepository.findById(findPairing.getPairingImage().getId());

		if (optional.isEmpty()) {
			paringImageRepository.save(pairingImage);
			findPairing.saveImage(pairingImage);
		} else {
			PairingImage findImage = findPairing.getPairingImage();
			findImage.updateImage(pairingImage);
			paringImageRepository.save(findImage);
			findPairing.saveImage(findImage);
		}

		pairingRepository.save(findPairing);

		return findPairing;
	}

	/* 특정 페어링 상세 조회 */
	public Pairing getPairing(long pairingId) {

		return findVerifiedPairing(pairingId);
	}

	/* 페어링 페이지 조회 */
	public List<Pairing> getPairingPage() {
		return null;
	}

	/* 페어링 삭제 */
	public String delete(long pairingId) {
		Pairing pairing = findVerifiedPairing(pairingId);
		pairingRepository.delete(pairing);

		return "해당 페어링이 성공적으로 삭제되었습니다.";
	}

	/* 존재하는 페어링인지 확인 -> 존재하면 해당 페어링 반환 */
	private Pairing findVerifiedPairing(long pairingId) {

		return pairingRepository.findById(pairingId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAIRING_NOT_FOUND));
	}

	/* 카테고리 불러오기 */
	private PairingCategory findCategory(String category) {

		return PairingCategory.valueOf(category);
	}
}
