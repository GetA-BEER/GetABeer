package be.domain.pairing.controller;

import org.springframework.http.ResponseEntity;

public class PairingController {

	/* 페어링 등록 */
	public ResponseEntity post() {
		return null;
	}

	/* 페어링 수정 */
	public ResponseEntity patch() {
		return null;
	}

	/* 특정 페어링 상세 조회 */
	public ResponseEntity getPairing() {
		return null;
	}

	/* 페어링 페이지 조회 */
	public ResponseEntity getPairingPage() {
		return null;
	}

	/* 페어링 삭제 */
	public ResponseEntity<String> delete() {
		return ResponseEntity.ok("성공적으로 삭제되었습니다.");
	}
}
