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
import be.domain.pairing.entity.Pairing2;
import be.domain.pairing.entity.PairingCategory;
import be.domain.pairing.entity.PairingImage;
import be.domain.pairing.entity.PairingImage2;
import be.domain.pairing.repository.PairingImageRepository;
import be.domain.pairing.repository.PairingImageRepository2;
import be.domain.pairing.repository.PairingRepository;
import be.domain.pairing.repository.PairingRepository2;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PairingService2 {
	private final BeerService beerService;

	/* 다대일 매핑의 경우 */
	private final PairingRepository2 pairingRepository;
	private final PairingImageRepository2 imageRepository;

	/* 페어링 등록 v2 */
	@Transactional
	public Pairing2 create(Pairing2 pairing, List<String> image, String category) {
		pairing.updateCategory(findCategory(category));

		/* 이미지 저장하기 */
		if (image.size() > 3) {
			throw new BusinessLogicException(ExceptionCode.IMAGE_SIZE_OVER);
		}

		for (int i = 0; i < image.size(); i++) {
			PairingImage2 pairingImage = PairingImage2.builder()
				.imageUrl(image.get(i))
				.pairing(pairing)
				.build();
			imageRepository.save(pairingImage);
		}

		/* 페어링 등록하기 */
		pairing.saveDefault(new ArrayList<>(), 0, 0);
		pairingRepository.save(pairing);

		return pairing;
	}

	/* 일대다 이미지 리스트 가져오기 */
	public List<PairingImageDto.Response2> getImageDtoList(Long pairingId) {
		return pairingRepository.findPairingImageList(pairingId);
	}

	public List<PairingImage2> getImageList(Long pairingId) {
		return pairingRepository.findPairingImage(pairingId);
	}

	/* 페어링 수정 v2 -> 어떻게하면 더 효과적으로 이미지를 바꿀 수 있지? */
	public Pairing2 update(Pairing2 pairing, long pairingId, String category, List<String> image) {

		/* 존재하는 페어링인지 확인 및 해당 페어링 정보 가져오기 */
		Pairing2 findPairing = findVerifiedPairing(pairingId);

		/* 수정할 내용이 존재하면, 해당 정보 수정 후 저장*/
		Optional.ofNullable(pairing.getContent()).ifPresent(findPairing::updateContent);
		if (category != null) {
			findPairing.updateCategory(findCategory(category));
		}

		/* 이미지 수정 */
		List<PairingImage2> list = getImageList(pairingId);

		if (image.size() == list.size()) {
			for (int i = 0; i < image.size(); i++) {
				PairingImage2 image2 = list.get(i);
				image2.updateImage(image.get(i));
				imageRepository.save(image2);
			}
		} else if (image.size() > list.size()) {
			for (int i = 0; i < list.size(); i++) {
				PairingImage2 image2 = list.get(i);
				image2.updateImage(image.get(i));
				imageRepository.save(image2);
			}

			for (int i = 0; i < image.size() - list.size(); i++) {
				PairingImage2 image2 = PairingImage2.builder()
					.imageUrl(image.get(image.size() - 1 - i))
					.pairing(findPairing)
					.build();

				imageRepository.save(image2);
			}
		} else {
			for (int i = 0; i < image.size(); i++) {
				PairingImage2 image2 = list.get(i);
				image2.updateImage(image.get(i));
				imageRepository.save(image2);
			}

			for (int i = 0; i < list.size() - image.size(); i++) {
				PairingImage2 image2 = list.get(list.size() - 1 - i);
				imageRepository.delete(image2);
			}
		}

		pairingRepository.save(findPairing);

		return findPairing;
	}

	/* 특정 페어링 상세 조회 */
	public Pairing2 getPairing(long pairingId) {

		return findVerifiedPairing(pairingId);
	}

	/* 페어링 삭제 */
	public String delete(long pairingId) {
		Pairing2 pairing = findVerifiedPairing(pairingId);
		pairingRepository.delete(pairing);

		return "해당 페어링이 성공적으로 삭제되었습니다.";
	}

	/* 존재하는 페어링인지 확인 -> 존재하면 해당 페어링 반환 */
	private Pairing2 findVerifiedPairing(long pairingId) {

		return pairingRepository.findById(pairingId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAIRING_NOT_FOUND));
	}

	/* 카테고리 불러오기 */
	private PairingCategory findCategory(String category) {

		return PairingCategory.valueOf(category);
	}
}
