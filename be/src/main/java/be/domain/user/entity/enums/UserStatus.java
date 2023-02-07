package be.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {
	ACTIVE_USER("활동 계정"),
	SLEEP_USER("휴면 계정"),
	QUIT_USER("탈퇴 계정");

	private final String status;

	UserStatus(String status) {
		this.status = status;
	}

	@JsonValue
	public String getStatus() {
		return status;
	}
}
