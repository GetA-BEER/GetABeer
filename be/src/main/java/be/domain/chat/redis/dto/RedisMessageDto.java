package be.domain.chat.redis.dto;

import java.time.LocalDateTime;
import java.util.List;

import be.domain.user.entity.User;
import be.domain.user.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;

public class RedisMessageDto {

	@Getter
	@Builder
	public static class Request {
		// private Long id;
		private String content;
		private String type;
	}

	@Getter
	@Builder
	public static class Response {
		private Long roomId;
		private Long messageId;
		private Long userId;
		private String userNickname;
		private String userRole;
		private LocalDateTime createdAt;
		private String content;
		private String type;

		public void addRole(User user) {
			if (user.getRoles().contains("ROLE_ADMIN")) {
				this.userRole = Role.ROLE_ADMIN.toString();
			} else {
				this.userRole = Role.ROLE_USER.toString();
			}
		}
	}
}
