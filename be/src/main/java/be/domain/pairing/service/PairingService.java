package be.domain.pairing.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.comment.repository.PairingCommentRepository;
import be.domain.like.repository.PairingLikeRepository;
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
import be.global.image.ImageHandler;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PairingService {
	private final BeerService beerService;
	private final UserService userService;
	private final ImageHandler imageHandler;
	private final PairingRepository pairingRepository;
	private final PairingLikeRepository pairingLikeRepository;
	private final PairingImageRepository pairingImageRepository;
	private final PairingCommentRepository pairingCommentRepository;

	/* 페어링 등록 */
	@Transactional
	public String create(Pairing pairing, List<MultipartFile> files, Long beerId, Long userId) throws IOException {

		/* 존재하는 맥주인지 확인 */
		Beer beer = beerService.findVerifiedBeer(beerId);

		/* 존재하는 회원인지 확인 */
		User user = userService.getUser(userId);

		/* 이미지 저장하기 */
		List<PairingImage> pairingImages;
		if (files == null) {
			pairingImages = new ArrayList<>();
		} else {
			if (files.size() > 3) {
				throw new BusinessLogicException(ExceptionCode.IMAGE_SIZE_OVER);
			}

			pairingImages = imageHandler.createPairingImage(pairing, files, user.getId(), beer);
		}

		String thumbnail = "";
		if (pairingImages.size() != 0) {
			if (pairingImages.get(0) != null) {
				thumbnail = pairingImages.get(0).getImageUrl();
			}
		}

		/* 페어링 등록하기 */
		pairing.saveDefault(beer, user, thumbnail, pairingImages);
		pairingRepository.save(pairing);

		return "맥주에 대한 페어링이 성공적으로 등록되었습니다.";
	}

	public List<PairingImage> getImageList(Long pairingId) {

		return pairingImageRepository.findPairingImage(pairingId);
	}

	/* 페어링 수정 */
	@Transactional
	public String update(Pairing pairing, long pairingId, List<String> type,
		List<Long> url, List<MultipartFile> files) throws IOException {

		/* 존재하는 페어링인지 확인 및 해당 페어링 정보 가져오기 */
		Pairing findPairing = findVerifiedPairing(pairingId);

		/* 수정할 내용이 존재하면, 해당 정보 수정 후 저장*/
		Optional.ofNullable(pairing.getContent()).ifPresent(findPairing::updateContent);
		Optional.ofNullable(pairing.getPairingCategory()).ifPresent(findPairing::updateCategory);

		/* 이미지 수정 */
		if (type.size() > 3) {
			throw new BusinessLogicException(ExceptionCode.IMAGE_SIZE_OVER);
		}

		List<PairingImage> pairingImages = imageHandler.updatePairingImage(findPairing, type, url, files);

		String thumbnail = "";
		if (pairingImages.size() != 0) {
			if (pairingImages.get(0) != null) {
				thumbnail = pairingImages.get(0).getImageUrl();
			}
		}

		findPairing.updateImages(thumbnail, pairingImages);

		/* 수정 내용 저장 */
		pairingRepository.save(findPairing);

		return "맥주에 대한 페어링이 성공적으로 수정되었습니다.";
	}

	/* 특정 페어링 상세 조회 : 평가 조회 */
	public Pairing findPairing(Long pairingId) {

		return findVerifiedPairing(pairingId);
	}

	/* 특정 페어링 상세 조회 : 응답 값 */
	public PairingResponseDto.Detail getPairing(Long pairingId) {

		/* 존재하는 페어링인지 확인 */
		Pairing pairing = findVerifiedPairing(pairingId);

		PairingResponseDto.Detail response = pairingRepository.findPairingDetailResponseDto(pairing.getId());
		User user = userService.getLoginUserReturnNull();

		if (user != null) {
			response.addUserLike(getIsUserLikes(pairingId, user.getId()));
		} else {
			response.addUserLike(false);
		}
		response.addCategory(findCategory(pairing.getId()));
		response.addCommentList(pairingCommentRepository.findPairingCommentList(pairingId));
		response.addImageList(pairingImageRepository.findPairingImageList(pairingId));

		return response;
	}

	/* 페어링 삭제 */
	public String delete(long pairingId) {
		Pairing pairing = findVerifiedPairing(pairingId);
		pairingRepository.delete(pairing);

		return "해당 페어링이 성공적으로 삭제되었습니다.";
	}

	/* 페어링 페이지 조회*/
	public Page<PairingResponseDto.Total> getPairingPageOrderByRecent(
		Long beerId, Integer page, Integer size, String type) {

		Page<PairingResponseDto.Total> responses;
		User user = userService.getLoginUserReturnNull();

		/* 로그인 유저가 없는 경우 */
		if (user == null) {
			switch (type) {
				case "recency":
					responses = pairingRepository.findPairingTotalResponseOrder(beerId,
						PageRequest.of(page - 1, size, Sort.by("pairingId")));
					break;
				case "mostlikes":
					responses = pairingRepository.findPairingTotalResponseOrder(beerId,
						PageRequest.of(page - 1, size, Sort.by("likeCount")));
					break;
				case "mostcomments":
					responses = pairingRepository.findPairingTotalResponseOrder(beerId,
						PageRequest.of(page - 1, size, Sort.by("commentCount")));
					break;
				default:
					throw new BusinessLogicException(ExceptionCode.WRONG_URI);
			}

			responses.forEach(pairing -> pairing.addUserLike(false));
		} else { /* 로그인 유저가 있는 경우 */
			switch (type) {
				case "recency":
					responses = pairingRepository.findPairingTotalResponseOrder(beerId, user.getId(),
						PageRequest.of(page - 1, size, Sort.by("pairingId")));
					break;
				case "mostlikes":
					responses = pairingRepository.findPairingTotalResponseOrder(beerId, user.getId(),
						PageRequest.of(page - 1, size, Sort.by("likeCount")));
					break;
				case "mostcomments":
					responses = pairingRepository.findPairingTotalResponseOrder(beerId, user.getId(),
						PageRequest.of(page - 1, size, Sort.by("commentCount")));
					break;
				default:
					throw new BusinessLogicException(ExceptionCode.WRONG_URI);
			}

			responses.forEach(pairing -> pairing.addUserLike(getIsUserLikes(pairing.getPairingId(), user.getId())));
		}
		responses.forEach(pairing -> pairing.addCategory(findCategory(pairing.getPairingId())));

		return responses;
	}

	// ---------------------------------------------------------------------------------------------------------------

	/* 일대다 이미지 리스트 가져오기 */
	public List<PairingImageDto.Response> getImageDtoList(Long pairingId) {
		return pairingImageRepository.findPairingImageList(pairingId);
	}

	/* 카테고리 정보 가져오기 */
	public PairingCategory findCategory(Long pairingId) {
		Pairing pairing = findVerifiedPairing(pairingId);

		return pairing.getPairingCategory();
	}

	private Boolean getIsUserLikes(Long pairingId, Long userId) {
		int userLikes = pairingLikeRepository.findPairingLikeUser(pairingId, userId);

		return userLikes != 0;
	}

	/* 존재하는 페어링인지 확인 -> 존재하면 해당 페어링 반환 */
	private Pairing findVerifiedPairing(Long pairingId) {

		return pairingRepository.findById(pairingId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAIRING_NOT_FOUND));
	}
}
