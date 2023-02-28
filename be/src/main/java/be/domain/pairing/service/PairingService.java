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
import be.domain.comment.repository.pairing.PairingCommentRepository;
import be.domain.like.repository.PairingLikeRepository;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;
import be.domain.pairing.repository.image.PairingImageRepository;
import be.domain.pairing.repository.PairingRepository;
import be.domain.pairing.service.helper.StateHelper;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import be.global.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	private StateHelper stateHelper = new StateHelper();

	/* 페어링 등록 */
	@Transactional
	public String create(Pairing pairing, List<MultipartFile> files,
		Long beerId) throws IOException {

		log.info("**************************************************************");
		log.info("서비스 시작 ");
		log.info("매퍼가 내용을 잘 변환했나 : " + pairing.getContent());
		log.info("매퍼가 내용을 잘 변환했나 : " + pairing.getLikeCount());
		log.info("**************************************************************");

		/* 존재하는 회원인지 확인 */
		User user = userService.findLoginUser();

		/* 존재하는 맥주인지 확인 */
		Beer beer = beerService.findVerifiedBeer(beerId);

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

		log.info("**************************************************************");
		log.info("페어링 등록 확인 : " + pairing.getContent());
		log.info("페어링 등록 확인 : " + pairing.getPairingCategory());
		log.info("페어링 등록 확인 : " + pairing.getBeer().getBeerDetailsBasic().getKorName());
		log.info("페어링 유저 확인 : " + pairing.getUser().getNickname());
		log.info("**************************************************************");

		return "맥주에 대한 페어링이 성공적으로 등록되었습니다.";
	}

	/* 페어링 수정 */
	@Transactional
	public String update(Pairing pairing, long pairingId, List<String> type,
		List<Long> url, List<MultipartFile> files) throws IOException {

		/* 존재하는 페어링인지 확인 및 해당 페어링 정보 가져오기 */
		Pairing findPairing = findVerifiedPairing(pairingId);

		/* 로그인 한 유저가 페어링을 작성한 유저가 맞는지 확인 */
		User user = findPairing.getUser();
		User loginUser = userService.findLoginUser();
		userService.checkUser(user.getId(), loginUser.getId());

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
	@Transactional(readOnly = true)
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
		response.addCategory(findVerifiedPairing(pairing.getId()).getPairingCategory());
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
	public Page<PairingResponseDto.Total> getPairingPageOrderBy(
		Long beerId, String type, String category, Integer page, Integer size) {

		User user = userService.getLoginUserReturnNull();

		return stateHelper
			.getPairingResponsePage(user, category, type, beerId,
				PageRequest.of(page - 1, size), pairingRepository, pairingLikeRepository);
	}

	// ---------------------------------------------------------------------------------------------------------------

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
