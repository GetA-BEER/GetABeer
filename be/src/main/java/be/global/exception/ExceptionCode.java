package be.global.exception;

import lombok.Getter;

public enum ExceptionCode {

	/* Comment 관련 예외 */
	RATING_NOT_FOUND(404, "맥주 평가 정보를 찾을 수 없습니다."),
	PAIRING_NOT_FOUND(404, "페어링 정보를 찾을 수 없습니다."),

	/* USER 관련 예외 */
	USER_NOT_FOUND(404, "User Not Found"),
	USER_ID_EXISTS(409, "User ID Exists"),
	NICKNAME_EXISTS(409, "Nickname Exists"),
	UNAUTHORIZED(401, "Unauthorized"), // 인증이 필요한 상태
	FORBIDDEN(403, "Forbidden"), // 인증은 되었으나 권한이 없는
	WRONG_CODE(400, "Code is wrong"),
	UNAUTHORIZED_EMAIL(401, "인증되지 않은 이메일입니다."),

	/* BEER 관련 예외 */
	BEER_NOT_FOUND(404, "Beer Not Found"),
	BEER_CATEGORY_NOT_FOUND(404, "Beer Category Not Found");

	@Getter
	private int status;
	@Getter
	private String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
