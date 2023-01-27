package be.domain.comment.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

public class BeerCommentDto {

	@Getter
	@Builder
	public static class Post {
		private String nickname;
		private String content;
		private Double star;
	}

	@Getter
	@Builder
	public static class Patch {
		private String content;
		private Double star;
	}

	@Getter
	public static class Response {

		private Long beerCommentId;
		private String nickname;
		private String content;
		private Double star;
		private Integer likeCount;
		private Integer recommentCount;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		protected Response() {
		}

		@Builder
		public Response(Long beerCommentId, String nickname, String content, Double star, Integer likeCount,
			Integer recommentCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
			this.beerCommentId = beerCommentId;
			this.nickname = nickname;
			this.content = content;
			this.star = star;
			this.likeCount = likeCount;
			this.recommentCount = recommentCount;
			this.createdAt = createdAt;
			this.modifiedAt = modifiedAt;
		}
	}
}
