package be.global.exception;

import lombok.Getter;

public enum ExceptionCode {

	/* 이미지 업로드 관련 예외 */
	NOT_IMAGE_EXTENSION(400, "업로드 할 수 없는 확장자입니다."),
	CHECK_IMAGE_NAME(400, "이미지는 공백 제외 1글자 이상의 문자열이어야 합니다."),
	TOO_BIG_SIZE(400, "이미지 사이즈가 너무 큽니다."),

	/* 평가, 페어링 관련 예외 */
	RATING_USER_EXISTS(409, "작성한 평가가 존재합니다."),
	RATING_NOT_FOUND(404, "맥주 평가 정보를 찾을 수 없습니다."),
	PAIRING_NOT_FOUND(404, "페어링 정보를 찾을 수 없습니다."),
	TAG_IS_WRONG(400, "태그 요청이 잘못 되었습니다."),
	IMAGE_SIZE_OVER(400, "이미지는 세 장까지만 등록이 가능합니다."),
	COMMENT_NOT_FOUND(404, "댓글이 존재하지 않습니다."),
	NOT_CORRECT_USER(403, "유저 정보가 일치하지 않습니다."),
	NOT_LIKE_WRITER(400, "본인이 작성한 글에 추천을 누를 수 없습니다."),
	WRONG_URI(400, "잘못된 요청 주소입니다."),
	ZERO_STAR(400, "평점에 0점은 줄 수 없습니다."),
	NOT_FOUND_CATEGORY(404, "카테고리가 존재하지 않습니다."),

	/* USER 관련 예외 */
	USER_NOT_FOUND(404, "User Not Found"),
	EMAIL_EXIST(409, "이미 가입된 이메일입니다. 로그인을 진행해주세요."),
	NICKNAME_EXISTS(409, "Nickname Exists"),
	UNAUTHORIZED(401, "Unauthorized"), // 인증이 필요한 상태
	FORBIDDEN(403, "Forbidden"), // 인증은 되었으나 권한이 없는 상태
	WRONG_CODE(400, "Code is wrong"),
	UNAUTHORIZED_EMAIL(401, "인증되지 않은 이메일입니다."),
	WITHDRAWN_USER(400, "탈퇴한 회원입니다."),
	SLEEP_USER(403, "휴면 계정입니다. 휴면 해제가 필요합니다."),
	WRONG_PASSWORD(404, "비밀번호가 일치하지 않습니다."),

	/* BEER 관련 예외 */
	BEER_NOT_FOUND(404, "Beer Not Found"),
	BEER_CATEGORY_NOT_FOUND(404, "Beer Category Not Found"),
	WISH_LISTED(409, "Already Listed"),
	UN_WISH_LISTED(409, "Already UnListed"),

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
