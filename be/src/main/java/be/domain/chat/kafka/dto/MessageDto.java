package be.domain.chat.kafka.dto;

import lombok.Builder;
import lombok.Getter;

public class MessageDto {

	@Getter
	@Builder
	public static class Request {
		private Long roomId;
		private String content;
		// private String type;
	}

	public static class Response {}
}
