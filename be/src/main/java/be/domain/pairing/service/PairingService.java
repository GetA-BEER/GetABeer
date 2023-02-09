package be.domain.pairing.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingCategory;
import be.domain.pairing.entity.PairingImage;
import be.domain.pairing.repository.PairingImageRepository;
import be.domain.pairing.repository.PairingRepository;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.image.PairingImageHandler;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PairingService {
	private final PairingRepository pairingRepository;
	private final PairingImageRepository pairingImageRepository;
	private final BeerService beerService;
	private final PairingImageHandler pairingImageHandler;
	private final UserService userService;

	/* 페어링 등록 */
	@Transactional
	public Pairing create(Pairing pairing, List<MultipartFile> files, String category,
		Long beerId, Long userId) throws IOException {

		/* 존재하는 맥주인지 확인 */
		Beer beer = beerService.findVerifiedBeer(beerId);

		/* 존재하는 회원인지 확인 */
		User user = userService.getUser(userId);

		pairing.updateCategory(findCategory(category));

		/* 이미지 저장하기 */
		if (files.size() > 3) {
			throw new BusinessLogicException(ExceptionCode.IMAGE_SIZE_OVER);
		}

		List<PairingImage> pairingImages = pairingImageHandler.createPairingImage(pairing, files, user.getId(), beer);

		/* 페어링 등록하기 */
		pairing.saveDefault(beer, user, pairingImages, new ArrayList<>(), 0, 0);
		pairingRepository.save(pairing);

		return pairing;
	}

	public List<PairingImage> getImageList(Long pairingId) {
		return pairingRepository.findPairingImage(pairingId);
	}

	/* 페어링 수정 */
	@Transactional
	public Pairing update(Pairing pairing, long pairingId, String category, List<String> image) {

		/* 존재하는 페어링인지 확인 및 해당 페어링 정보 가져오기 */
		Pairing findPairing = findVerifiedPairing(pairingId);

		/* 수정할 내용이 존재하면, 해당 정보 수정 후 저장*/
		Optional.ofNullable(pairing.getContent()).ifPresent(findPairing::updateContent);
		if (category != null) {
			findPairing.updateCategory(findCategory(category));
		}

		/* 이미지 수정 */
		if (image.size() > 3) {
			throw new BusinessLogicException(ExceptionCode.IMAGE_SIZE_OVER);
		}
		List<PairingImage> result = updateImage(findPairing, findPairing.getPairingImageList(), image);
		findPairing.updateImageList(result);

		/* 수정 내용 저장 */
		pairingRepository.save(findPairing);

		return findPairing;
	}

	/* 특정 페어링 상세 조회 */
	public Pairing getPairing(Long pairingId) {

		return findVerifiedPairing(pairingId);
	}

	/* 페어링 삭제 */
	public String delete(long pairingId) {
		Pairing pairing = findVerifiedPairing(pairingId);
		pairingRepository.delete(pairing);

		return "해당 페어링이 성공적으로 삭제되었습니다.";
	}

	/* 페어링 페이지 조회 : 최신순 */
	public Page<PairingResponseDto.Total> getPairingPageOrderByRecent(Long beerId, Integer page, Integer size) {
		Page<PairingResponseDto.Total> responses =
			pairingRepository.findPairingTotalResponseOrderByRecent(beerId, PageRequest.of(page - 1, size));

		responses.forEach(pairing -> pairing.addCategory(findCategory(pairing.getPairingId())));
		responses.forEach(pairing -> pairing.addThumbnail(getImageList(pairing.getPairingId()).get(0).getImageUrl()));

		return responses;
	}

	/* 페어링 페이지 조회 : 추천 순 */
	public Page<PairingResponseDto.Total> getPairingPageOrderByLikes(Long beerId, Integer page, Integer size) {
		Page<PairingResponseDto.Total> responses =
			pairingRepository.findPairingTotalResponseOrderByLikes(beerId, PageRequest.of(page - 1, size));

		responses.forEach(pairing -> pairing.addCategory(findCategory(pairing.getPairingId())));
		responses.forEach(pairing -> pairing.addThumbnail(getImageList(pairing.getPairingId()).get(0).getImageUrl()));

		return responses;
	}

	/* 페어링 페이지 조회 : 댓글 많은 순 */
	public Page<PairingResponseDto.Total> getPairingPageOrderByComments(Long beerId, Integer page, Integer size) {
		Page<PairingResponseDto.Total> responses =
			pairingRepository.findPairingTotalResponseOrderByComments(beerId, PageRequest.of(page - 1, size));

		responses.forEach(pairing -> pairing.addCategory(findCategory(pairing.getPairingId())));
		responses.forEach(pairing -> pairing.addThumbnail(getImageList(pairing.getPairingId()).get(0).getImageUrl()));

		return responses;
	}

	// ---------------------------------------------------------------------------------------------------------------

	/* 일대다 이미지 리스트 가져오기 */
	public List<PairingImageDto.Response> getImageDtoList(Long pairingId) {
		return pairingRepository.findPairingImageList(pairingId);
	}

	/* 카테고리 정보 가져오기 */
	public PairingCategory findCategory(Long pairingId) {
		Pairing pairing = findVerifiedPairing(pairingId);

		return pairing.getPairingCategory();
	}

	/* 존재하는 페어링인지 확인 -> 존재하면 해당 페어링 반환 */
	private Pairing findVerifiedPairing(Long pairingId) {

		return pairingRepository.findById(pairingId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAIRING_NOT_FOUND));
	}

	/* 문자열로 카테고리 불러오기 */
	private PairingCategory findCategory(String category) {

		return PairingCategory.valueOf(category);
	}

	/* 이미지 수정 -> 어떻게하면 더 효과적으로 이미지를 바꿀 수 있지? */
	@Transactional
	private List<PairingImage> updateImage(Pairing pairing, List<PairingImage> list, List<String> image) {
		List<PairingImage> result = new ArrayList<>();

		if (list.size() == image.size()) {
			for (int i = 0; i < list.size(); i++) {
				PairingImage pairingImage = list.get(i);
				pairingImage.updateImage(image.get(i));
				pairingImageRepository.save(pairingImage);
				result.add(pairingImage);
			}
		} else if (list.size() < image.size()) {
			if (list.size() == 0) {
				for (int i = 0; i < image.size(); i++) {
					PairingImage pairingImage = PairingImage.builder()
						.imageUrl(image.get(i))
						.pairing(pairing)
						.build();
					pairingImageRepository.save(pairingImage);
					result.add(pairingImage);
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					PairingImage pairingImage = list.get(i);
					pairingImage.updateImage(image.get(i));
					pairingImageRepository.save(pairingImage);
					result.add(pairingImage);
				}

				for (int j = list.size(); j < image.size(); j++) {
					PairingImage pairingImage = PairingImage.builder()
						.imageUrl(image.get(j))
						.pairing(pairing)
						.build();
					pairingImageRepository.save(pairingImage);
					result.add(pairingImage);
				}
			}
		} else {
			if (image.size() == 0) {
				for (int i = 0; i < list.size(); i++) {
					PairingImage pairingImage = list.get(i);
					pairingImageRepository.delete(pairingImage);
				}
			} else {
				for (int i = 0; i < image.size(); i++) {
					PairingImage pairingImage = list.get(i);
					pairingImage.updateImage(image.get(i));
					pairingImageRepository.save(pairingImage);
					result.add(pairingImage);
				}

				for (int j = image.size(); j < list.size(); j++) {
					PairingImage pairingImage = list.get(j);
					pairingImageRepository.delete(pairingImage);
				}
			}
		}

		return result;
	}
}
