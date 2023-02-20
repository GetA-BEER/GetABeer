package be.domain.comment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

public class PairingCommentDto {

	@Getter
	@Builder
	public static class Post {
		private Long pairingId;
		private Long userId;
		private String content;
	}

	@Getter
	@Builder
	public static class Patch {
		private String content;

		@JsonCreator
		public Patch(@JsonProperty("content") String content) {
			this.content = content;
		}
	}

	@Getter
	public static class Response {
		private Long pairingId;
		private Long pairingCommentId;
		private Long userId;
		private String nickname;
		private String userImage;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		protected Response() {
		}

		@Builder
		@QueryProjection
		public Response(Long pairingId, Long pairingCommentId, Long userId, String nickname, String userImage,
			String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
			this.pairingId = pairingId;
			this.pairingCommentId = pairingCommentId;
			this.userId = userId;
			this.nickname = nickname;
			this.userImage = userImage;
			this.content = content;
			this.createdAt = createdAt;
			this.modifiedAt = modifiedAt;
		}
	}
}
