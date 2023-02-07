package be.domain.comment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.RatingComment;
import be.domain.comment.repository.RatingCommentRepository;
import be.domain.rating.entity.Rating;
import be.domain.rating.service.RatingService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;

@Service
public class RatingCommentService {
	private final RatingCommentRepository ratingCommentRepository;
	private final RatingService ratingService;

	public RatingCommentService(RatingCommentRepository ratingCommentRepository, RatingService ratingService) {
		this.ratingCommentRepository = ratingCommentRepository;
		this.ratingService = ratingService;
	}

	/* 맥주 댓글 등록 */
	public RatingComment create(RatingComment ratingComment, Long ratingId) {
		Rating rating = ratingService.findVerifiedRating(ratingId);

		ratingComment.saveDefault(rating);
		ratingCommentRepository.save(ratingComment);

		return ratingComment;
	}

	/* 맥주 댓글 수정 */
	public RatingComment update(RatingComment ratingComment, Long ratingCommentId) {
		RatingComment findComment = findVerifiedRatingComment(ratingCommentId);

		Optional.ofNullable(ratingComment.getContent()).ifPresent(findComment::updateContent);
		ratingCommentRepository.save(findComment);

		return findComment;
	}

	/* 맥주 댓글 페이지 조회 */
	public List<RatingCommentDto.Response> getRatingComment(Long ratingId) {

		return ratingCommentRepository.findRatingCommentList(ratingId);
	}

	/* 맥주 댓글 삭제 */
	public String delete(Long ratingCommentId) {
		RatingComment findComment = findVerifiedRatingComment(ratingCommentId);
		ratingCommentRepository.delete(findComment);

		return "댓글이 성공적으로 삭제되었습니다.";
	}

	// ------------------------------------------------------------------------------------------------------------

	private RatingComment findVerifiedRatingComment(Long ratingCommentId) {
		return ratingCommentRepository.findById(ratingCommentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
	}
}
