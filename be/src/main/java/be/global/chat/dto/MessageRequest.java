package be.global.chat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageRequest {
	private String content;

	@JsonCreator
	public MessageRequest(@JsonProperty("content") String content) {
		this.content = content;
	}
}
