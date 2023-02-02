package be.global.exception;

import lombok.Getter;

public enum ExceptionCode {

	/* Comment 관련 예외 */
	RATING_NOT_FOUND(404, "맥주 평가 정보를 찾을 수 없습니다."),
	PAIRING_NOT_FOUND(404, "페어링 정보를 찾을 수 없습니다."),
	TAG_IS_WRONG(400, "태그 요청이 잘못 되었습니다."),
	IMAGE_SIZE_OVER(400, "이미지는 세 장까지만 등록이 가능합니다."),

	/* USER 관련 예외 */
	USER_NOT_FOUND(404, "User Not Found"),
	USER_ID_EXISTS(409, "User ID Exists"),
	NICKNAME_EXISTS(409, "Nickname Exists"),
	UNAUTHORIZED(401, "Unauthorized"), // 인증이 필요한 상태
	FORBIDDEN(403, "Forbidden"), // 인증은 되었으나 권한이 없는 상태

	/* BEER 관련 예외 */
	BEER_NOT_FOUND(404, "Beer Not Found"),
	BEER_CATEGORY_NOT_FOUND(404, "Beer Category Not Found"),

	/* EMBEDDED REDIS 관련 예외 */
	EMBEDDED_REDIS_EXCEPTION(500, "redis server error"),
	CAN_NOT_EXECUTE_GREP(500, "can not execute grep process command"),
	CAN_NOT_EXECUTE_REDIS_SERVER(500, "can not execute redis server"),
	NOT_FOUND_AVAILABLE_PORT(500, "not found available port");
	@Getter
	private int status;
	@Getter
	private String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
