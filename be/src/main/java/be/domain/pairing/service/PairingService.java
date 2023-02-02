package be.domain.pairing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.pairing.dto.PairingImageDto;
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
	public Pairing create(Pairing pairing, List<String> image, String category, Long beerId) {

		/* 존재하는 맥주인지 확인 */
		Beer beer = beerService.findVerifiedBeer(beerId);

		pairing.updateCategory(findCategory(category));

		/* 이미지 저장하기 */
		if (image.size() > 3) {
			throw new BusinessLogicException(ExceptionCode.IMAGE_SIZE_OVER);
		}

		List<PairingImage> pairingImages = new ArrayList<>();

		for (int i = 0; i < image.size(); i++) {
			PairingImage pairingImage = PairingImage.builder()
				.imageUrl(image.get(i))
				.pairing(pairing)
				.build();
			paringImageRepository.save(pairingImage);
			pairingImages.add(pairingImage);
		}

		/* 페어링 등록하기 */
		pairing.saveDefault(beer, pairingImages, new ArrayList<>(), 0, 0);
		pairingRepository.save(pairing);

		return pairing;
	}

	/* 일대다 이미지 리스트 가져오기 */
	public List<PairingImageDto.Response> getImageDtoList(Long pairingId) {
		return pairingRepository.findPairingImageList(pairingId);
	}

	public List<PairingImage> getImageList(Long pairingId) {
		return pairingRepository.findPairingImage(pairingId);
	}

	/* 페어링 수정 v2 -> 어떻게하면 더 효과적으로 이미지를 바꿀 수 있지? */
	public Pairing update(Pairing pairing, long pairingId, String category, List<String> image) {

		/* 존재하는 페어링인지 확인 및 해당 페어링 정보 가져오기 */
		Pairing findPairing = findVerifiedPairing(pairingId);

		/* 수정할 내용이 존재하면, 해당 정보 수정 후 저장*/
		Optional.ofNullable(pairing.getContent()).ifPresent(findPairing::updateContent);
		if (category != null) {
			findPairing.updateCategory(findCategory(category));
		}

		/* 이미지 수정 */
		List<PairingImage> list = getImageList(pairingId);

		if (image.size() == list.size()) {
			for (int i = 0; i < image.size(); i++) {
				PairingImage pairingImage = list.get(i);
				pairingImage.updateImage(image.get(i));
				paringImageRepository.save(pairingImage);
			}
		} else if (image.size() > list.size()) {
			for (int i = 0; i < list.size(); i++) {
				PairingImage pairingImage = list.get(i);
				pairingImage.updateImage(image.get(i));
				paringImageRepository.save(pairingImage);
			}

			for (int i = list.size(); i <= image.size() - list.size(); i++) {
				PairingImage pairingImage = PairingImage.builder()
					.imageUrl(image.get(i))
					.pairing(findPairing)
					.build();

				paringImageRepository.save(pairingImage);
			}
		} else {
			for (int i = 0; i < image.size(); i++) {
				PairingImage pairingImage = list.get(i);
				pairingImage.updateImage(image.get(i));
				paringImageRepository.save(pairingImage);
			}

			for (int i = image.size(); i <= list.size() - image.size(); i++) {
				PairingImage pairingImage = list.get(i);
				paringImageRepository.delete(pairingImage);
			}
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
