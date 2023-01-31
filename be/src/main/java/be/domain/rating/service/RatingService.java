package be.domain.rating.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import be.domain.rating.entity.Rating;
import be.domain.rating.repository.RatingRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;

@Service
public class RatingService {
	private final RatingRepository ratingRepository;

	public RatingService(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	/* 맥주 코멘트 등록 */
	public Rating create(Rating beerComment) {

		/* 기본 설정 저장하기 */
		beerComment.saveDefault(0, 0, new ArrayList<>());

		ratingRepository.save(beerComment);

		return beerComment;
	}

	/* 맥주 코멘트 수정 */
	public Rating update(Rating beerComment, long beerCommentId) {

		/* 존재하는 맥주 코멘트인지 확인 및 해당 맥주 코멘트 정보 가져오기 */
		Rating findBeerComment = findVerifiedBeerComment(beerCommentId);

		/* 수정할 내용이 존재하면, 해당 정보 수정 후 저장*/
		Optional.ofNullable(beerComment.getContent()).ifPresent(findBeerComment::updateContent);
		Optional.ofNullable(beerComment.getStar()).ifPresent(findBeerComment::updateStar);
		ratingRepository.save(findBeerComment);

		return findBeerComment;
	}

	/* 특정 맥주 코멘트 상세 조회 */
	public Rating getComment(long beerCommentId) {

		return findVerifiedBeerComment(beerCommentId);
	}

	/* 맥주 코멘트 페이지 조회 -> Query Dsl 사용 예정 */
	public List<Rating> getCommentPage(int page, int size) {

		return null;
	}

	/* 맥주 코멘트 삭제 */
	public String delete(long beerCommentId) {
		Rating beerComment = findVerifiedBeerComment(beerCommentId);
		ratingRepository.delete(beerComment);

		return "해당 맥주 코멘트가 삭제되었습니다.";
	}

	/* 존재하는 맥주 코멘트인지 확인 -> 존재하면 해당 맥주 코멘트 반환 */
	private Rating findVerifiedBeerComment(long beerCommentId) {

		return ratingRepository.findById(beerCommentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BEER_COMMENT_NOT_FOUND));
	}
}
