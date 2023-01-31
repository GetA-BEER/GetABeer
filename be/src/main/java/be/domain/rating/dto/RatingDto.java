package be.domain.rating.dto;

import java.time.LocalDateTime;
import java.util.List;

import be.domain.recomment.entity.BeerRecomment;
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

		private Long beerCommentId;
		private String nickname;
		private String content;
		private Double star;
		private Integer likeCount;
		private Integer recommentCount;
		private List<BeerRecomment> beerRecommentList;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		protected Response() {
		}

		@Builder
		public Response(Long beerId, Long beerCommentId, String nickname, String content, Double star,
			Integer likeCount,
			Integer recommentCount, List<BeerRecomment> beerRecommentList, LocalDateTime createdAt,
			LocalDateTime modifiedAt) {
			this.beerId = beerId;
			this.beerCommentId = beerCommentId;
			this.nickname = nickname;
			this.content = content;
			this.star = star;
			this.likeCount = likeCount;
			this.recommentCount = recommentCount;
			this.beerRecommentList = beerRecommentList;
			this.createdAt = createdAt;
			this.modifiedAt = modifiedAt;
		}
	}
}
