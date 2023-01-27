package be.domain.recomment.controller;

import org.springframework.http.ResponseEntity;

public class BeerRecommentController {

	/* 맥주 대댓글 등록 */
	public ResponseEntity post() {
		return null;
	}

	/* 맥주 대댓글 수정 */
	public ResponseEntity patch() {
		return null;
	}

	/* 맥주 대댓글 조회 */
	public ResponseEntity getPairingPage() {
		return null;
	}

	/* 맥주 대댓글 삭제 */
	public ResponseEntity<String> delete() {
		return ResponseEntity.ok("성공적으로 삭제되었습니다.");
	}
}
