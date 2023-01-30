package be.domain.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import be.domain.comment.entity.BeerComment;
import be.domain.comment.repository.BeerCommentRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;

@Service
public class BeerCommentService {
	private final BeerCommentRepository beerCommentRepository;

	public BeerCommentService(BeerCommentRepository beerCommentRepository) {
		this.beerCommentRepository = beerCommentRepository;
	}

	/* 맥주 코멘트 등록 */
	public BeerComment create(BeerComment beerComment) {

		/* 기본 설정 저장하기 */
		beerComment.saveDefault(0, 0, new ArrayList<>());

		beerCommentRepository.save(beerComment);

		return beerComment;
	}

	/* 맥주 코멘트 수정 */
	public BeerComment update(BeerComment beerComment, long beerCommentId) {

		/* 존재하는 맥주 코멘트인지 확인 및 해당 맥주 코멘트 정보 가져오기 */
		BeerComment findBeerComment = findVerifiedBeerComment(beerCommentId);

		/* 수정할 내용이 존재하면, 해당 정보 수정 후 저장*/
		Optional.ofNullable(beerComment.getContent()).ifPresent(findBeerComment::updateContent);
		Optional.ofNullable(beerComment.getStar()).ifPresent(findBeerComment::updateStar);
		beerCommentRepository.save(findBeerComment);

		return findBeerComment;
	}

	/* 특정 맥주 코멘트 상세 조회 */
	public BeerComment getComment(long beerCommentId) {

		return findVerifiedBeerComment(beerCommentId);
	}

	/* 맥주 코멘트 페이지 조회 -> Query Dsl 사용 예정 */
	public List<BeerComment> getCommentPage(int page, int size) {

		return null;
	}

	/* 맥주 코멘트 삭제 */
	public String delete(long beerCommentId) {
		BeerComment beerComment = findVerifiedBeerComment(beerCommentId);
		beerCommentRepository.delete(beerComment);

		return "해당 맥주 코멘트가 삭제되었습니다.";
	}

	/* 존재하는 맥주 코멘트인지 확인 -> 존재하면 해당 맥주 코멘트 반환 */
	private BeerComment findVerifiedBeerComment(long beerCommentId) {

		return beerCommentRepository.findById(beerCommentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BEER_COMMENT_NOT_FOUND));
	}
}
