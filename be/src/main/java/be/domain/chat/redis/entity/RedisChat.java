package be.domain.chat.redis.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RedisChat {
	private Long roomId;
	private Long senderId;
	private String content;

	private ChatType type;
	// @JsonSerialize(using = LocalDateTimeSerializer.class)
	// @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	// private LocalDateTime createdAt;

	public RedisChat(Long roomId, Long senderId, String content, String type) {
		this.roomId = roomId;
		this.senderId = senderId;
		this.content = content;
		this.type = ChatType.to(type);
	}
}
