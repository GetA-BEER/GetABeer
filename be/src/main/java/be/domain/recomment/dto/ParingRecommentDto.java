package be.domain.recomment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

public class ParingRecommentDto {

	@Getter
	@Builder
	public static class Post {
		private String nickname;
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
		private Long pairingRecommentId;
		private String nickname;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		protected Response() {
		}

		@Builder
		public Response(Long pairingId, Long pairingRecommentId, String nickname, String content,
			LocalDateTime createdAt,
			LocalDateTime modifiedAt) {
			this.pairingId = pairingId;
			this.pairingRecommentId = pairingRecommentId;
			this.nickname = nickname;
			this.content = content;
			this.createdAt = createdAt;
			this.modifiedAt = modifiedAt;
		}
	}
}
