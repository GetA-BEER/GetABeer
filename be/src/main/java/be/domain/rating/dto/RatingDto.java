package be.domain.rating.dto;

import java.time.LocalDateTime;
import java.util.List;

import be.domain.comment.entity.RatingComment;
import lombok.Builder;
import lombok.Getter;

public class RatingDto {

	@Getter
	@Builder
	public static class Post {
		private Long beerId;
		private String nickname;
		private String content;
		private Double star;
	}

	@Getter
	@Builder
	public static class Patch {
		private Long beerId;
		private String content;
		private Double star;
	}

	@Getter
	public static class Response {
		private Long beerId;

		private Long ratingId;
		private String nickname;
		private String content;
		private Double star;
		private Integer likeCount;
		private Integer commentCount;
		private List<RatingComment> ratingCommentList;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		protected Response() {
		}

		@Builder
		public Response(Long beerId, Long ratingId, String nickname, String content, Double star,
			Integer likeCount,
			Integer commentCount, List<RatingComment> ratingCommentList, LocalDateTime createdAt,
			LocalDateTime modifiedAt) {
			this.beerId = beerId;
			this.ratingId = ratingId;
			this.nickname = nickname;
			this.content = content;
			this.star = star;
			this.likeCount = likeCount;
			this.commentCount = commentCount;
			this.ratingCommentList = ratingCommentList;
			this.createdAt = createdAt;
			this.modifiedAt = modifiedAt;
		}
	}
}
