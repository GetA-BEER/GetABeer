package be.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PairingCommentController {

	/* 페어링 댓글 등록 */
	public ResponseEntity post() {
		return null;
	}

	/* 페어링 댓글 수정 */
	public ResponseEntity patch() {
		return null;
	}

	/* 페어링 댓글 조회 */
	public ResponseEntity getPairingCommentPage() {
		return null;
	}

	/* 페어링 댓글 삭제 */
	public ResponseEntity<String> delete() {
		return ResponseEntity.ok("성공적으로 삭제되었습니다.");
	}
}
