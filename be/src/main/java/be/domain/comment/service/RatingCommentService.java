package be.domain.comment.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.RatingComment;
import be.domain.comment.repository.rating.RatingCommentRepository;
import be.domain.notice.entity.NotificationType;
import be.domain.notice.service.NotificationService;
import be.domain.rating.entity.Rating;
import be.domain.rating.repository.RatingRepository;
import be.domain.rating.service.RatingService;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingCommentService {
	private final EntityManager em;
	private final UserService userService;
	private final RatingService ratingService;
	private final RatingRepository ratingRepository;
	private final NotificationService notificationService;
	private final RatingCommentRepository ratingCommentRepository;

	/* 맥주 댓글 등록 */
	@Transactional
	public RatingComment create(RatingComment ratingComment, Long ratingId) {

		User user = userService.getLoginUser();

		Rating rating = ratingService.findRating(ratingId);
		ratingComment.saveDefault(rating, user);
		ratingCommentRepository.save(ratingComment);

		em.flush();

		rating.calculateComments(rating.getRatingCommentList().size());
		ratingRepository.save(rating);

		if (!user.getId().equals(rating.getUser().getId())) {
			String title = user.getNickname() + "님이 회원님의 게시글에 댓글을 남겼습니다.";
			String content = "\"" + ratingComment.getContent() + "\"";
			notificationService.send(rating.getUser(), rating.getId(), title, content, user.getImageUrl(),
				NotificationType.RATING);
		}

		return ratingComment;
	}

	/* 맥주 댓글 수정 */
	@Transactional
	public RatingComment update(RatingComment ratingComment, Long ratingCommentId) {
		RatingComment findComment = findVerifiedRatingComment(ratingCommentId);

		User loginUser = userService.getLoginUser();
		User user = findComment.getUser();
		userService.checkUser(user.getId(), loginUser.getId());

		Optional.ofNullable(ratingComment.getContent()).ifPresent(findComment::updateContent);
		ratingCommentRepository.save(findComment);

		return findComment;
	}

	/* 맥주 댓글 페이지 조회 */
	@Transactional(readOnly = true)
	public List<RatingCommentDto.Response> getRatingComment(Long ratingId) {

		return ratingCommentRepository.findRatingCommentList(ratingId);
	}

	/* 맥주 댓글 삭제 */
	@Transactional
	public String delete(Long ratingCommentId) {
		RatingComment findComment = findVerifiedRatingComment(ratingCommentId);

		/* 로그인 한 유저와 삭제하려는 유저가 같은지 확인 */
		User loginUser = userService.getLoginUser();
		User user = findComment.getUser();
		userService.checkUser(user.getId(), loginUser.getId());

		Rating rating = findComment.getRating();

		ratingCommentRepository.delete(findComment);

		em.flush();

		rating.calculateComments(rating.getRatingCommentList().size());
		ratingRepository.save(rating);

		return "댓글이 성공적으로 삭제되었습니다.";
	}

	// ------------------------------------------------------------------------------------------------------------

	private RatingComment findVerifiedRatingComment(Long ratingCommentId) {
		return ratingCommentRepository.findById(ratingCommentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
	}
}
