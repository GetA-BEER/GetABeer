package be.domain.comment.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.entity.PairingComment;
import be.domain.comment.repository.PairingCommentRepository;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.service.PairingService;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;

@Service
public class PairingCommentService {
	private final PairingCommentRepository pairingCommentRepository;
	private final PairingService pairingService;
	private final UserService userService;

	public PairingCommentService(PairingCommentRepository pairingCommentRepository, PairingService pairingService,
		UserService userService) {
		this.pairingCommentRepository = pairingCommentRepository;
		this.pairingService = pairingService;
		this.userService = userService;
	}

	/* 페어링 댓글 등록 */
	public PairingComment create(PairingComment pairingComment, Long pairingId, Long userId) {

		/* 로그인 유저랑 댓글을 달려고 하는 유저가 같은지 확인 */
		// User loginUser = userService.getLoginUser();
		// checkUser(userId, loginUser.getId());

		User user = userService.getUser(userId);
		Pairing pairing = pairingService.getPairing(pairingId);
		pairingComment.saveDefault(user, pairing);
		pairingCommentRepository.save(pairingComment);

		return pairingComment;
	}

	/* 페어링 댓글 수정 */
	public PairingComment update(PairingComment pairingComment, Long commentId) {
		/* 페어링 코멘트가 존재하는지 확인 */
		PairingComment findComment = findVerifiedPairingComment(commentId);

		/* 로그인 유저랑 댓글 주인이랑 아이디 같은지 확인 */
		// User loginUser = userService.getLoginUser();
		// User user = findComment.getUser();
		// checkUser(user.getId(), loginUser.getId());

		Optional.ofNullable(pairingComment.getContent()).ifPresent(findComment::updateContent);

		pairingCommentRepository.save(findComment);

		return findComment;
	}

	/* 페어링 댓글 리스트 조회 : 응답 객체 */
	public List<PairingCommentDto.Response> getPairingComment(Long pairingId) {

		return pairingCommentRepository.findPairingCommentList(pairingId);
	}

	/* 페어링 댓글 삭제 */
	public String delete(Long commentId) {
		PairingComment pairingComment = findVerifiedPairingComment(commentId);
		pairingCommentRepository.delete(pairingComment);

		return "댓글이 성공적으로 삭제 되었습니다.";
	}

	private PairingComment findVerifiedPairingComment(Long commentId) {
		return pairingCommentRepository.findById(commentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
	}

	private void checkUser(Long userId, Long loginUserId) {
		if (!userId.equals(loginUserId)) {
			throw new BusinessLogicException(ExceptionCode.NOT_CORRECT_USER);
		}
	}
}
