package be.domain.comment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.RatingComment;
import be.domain.comment.repository.RatingCommentRepository;
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
	private final UserService userService;
	private final RatingService ratingService;
	private final RatingRepository ratingRepository;
	private final RatingCommentRepository ratingCommentRepository;

	/* 맥주 댓글 등록 */
	@Transactional
	public RatingComment create(RatingComment ratingComment, Long ratingId) {

		/* 로그인 유저와 들어오는 정보의 유저가 일치하는지 */
		User user = userService.getLoginUser();

		Rating rating = ratingService.findRating(ratingId);
		ratingComment.saveDefault(rating, user);
		ratingCommentRepository.save(ratingComment);

		rating.calculateComments(rating.getRatingCommentList().size());
		ratingRepository.save(rating);

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
	public String delete(Long ratingCommentId) {
		RatingComment findComment = findVerifiedRatingComment(ratingCommentId);
		Rating rating = findComment.getRating();

		ratingCommentRepository.delete(findComment);
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
