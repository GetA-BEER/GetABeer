package be.domain.pairing.dto;

import java.time.LocalDateTime;
import java.util.List;

import be.domain.comment.dto.PairingCommentDto;
import be.domain.pairing.entity.PairingCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PairingResponseDto {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Detail {
		public Detail() {
		}

		private Long beerId;
		private Long pairingId;
		private Long userId;
		private String nickname;
		private String content;
		private List<PairingImageDto.Response> imageList;
		private List<PairingCommentDto.Response> commentList;
		private PairingCategory category;
		private Integer likeCount;
		private Integer commentCount;
		private Boolean isUserLikes;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		public void addCategory(PairingCategory category) {
			this.category = category;
		}

		public void addUserLike(Boolean isUserLikes) {
			this.isUserLikes = isUserLikes;
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Total {
		public Total() {
		}

		private Long beerId;
		private Long pairingId;
		private Long userId;
		private String nickname;
		private String content;
		private String thumbnail;
		private PairingCategory category;
		private Integer likeCount;
		private Integer commentCount;
		private Boolean isUserLikes;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		public void addCategory(PairingCategory category) {
			this.category = category;
		}

		public void addThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}

		public void addUserLike(Boolean isUserLikes) {
			this.isUserLikes = isUserLikes;
		}
	}
}
