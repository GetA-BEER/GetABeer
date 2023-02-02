package be.domain.rating.dto;

import java.time.LocalDateTime;
import java.util.List;

import be.domain.comment.dto.RatingCommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class RatingResponseDto {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Detail {
		public Detail() {
		}

		private Long beerId;
		private Long ratingId;
		private String nickname;
		private String content;
		private List<RatingTagDto.Response> ratingTag;
		private Double star;
		private Integer likeCount;
		private Integer commentCount;
		private List<RatingCommentDto.Response> ratingCommentList;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		public void addTag(List<RatingTagDto.Response> ratingTag) {
			this.ratingTag = ratingTag;
		}

		public void addComment(List<RatingCommentDto.Response> ratingCommentList) {
			this.ratingCommentList = ratingCommentList;
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Total {
		public Total() {
		}

		private Long beerId;
		private Long ratingId;
		private String nickname;
		private String content;
		private List<RatingTagDto.Response> ratingTag;
		private Double star;
		private Integer likeCount;
		private Integer commentCount;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		public void addTag(List<RatingTagDto.Response> ratingTag) {
			this.ratingTag = ratingTag;
		}
	}
}
