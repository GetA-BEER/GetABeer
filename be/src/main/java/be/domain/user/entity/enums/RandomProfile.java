package be.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RandomProfile {
	ONE("https://getabeer.s3.ap-northeast-2.amazonaws.com/profileImage/randomprofile/1.png"),
	TWO("https://getabeer.s3.ap-northeast-2.amazonaws.com/profileImage/randomprofile/2.png"),
	THREE("https://getabeer.s3.ap-northeast-2.amazonaws.com/profileImage/randomprofile/3.png"),
	FOUR("https://getabeer.s3.ap-northeast-2.amazonaws.com/profileImage/randomprofile/4.png");

	private final String value;

	RandomProfile(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
