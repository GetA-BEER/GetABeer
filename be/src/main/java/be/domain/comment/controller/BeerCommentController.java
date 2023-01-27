package be.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerCommentController {

	/* 맥주 코멘트 등록 */
	public ResponseEntity post() {
		return null;
	}

	/* 맥주 코멘트 수정 */
	public ResponseEntity patch() {
		return null;
	}

	/* 특정 맥주 코멘트 상세 조회 */
	public ResponseEntity getComment() {
		return null;
	}

	/* 맥주 코멘트 페이지 조회 */
	public ResponseEntity getCommentPage() {
		return null;
	}

	/* 맥주 코멘트 삭제 */
	public ResponseEntity<String> delete() {
		return ResponseEntity.ok("성공적으로 삭제되었습니다.");
	}
}
