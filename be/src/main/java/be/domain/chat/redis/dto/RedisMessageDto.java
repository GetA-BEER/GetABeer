package be.domain.chat.redis.dto;

import lombok.Builder;
import lombok.Getter;

public class RedisMessageDto {

	@Getter
	@Builder
	public static class Request {
		private Long id;
		private String content;
		// private String type;
	}

	@Getter
	@Builder
	public static class Response {
		private Long roomId;
		private Long userId;
		private String userNickname;
		private String content;
		private String type;
	}
}