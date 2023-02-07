package be.domain.pairing.dto;

import java.time.LocalDateTime;
import java.util.List;

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
		private String nickname;
		private String content;
		private List<PairingImageDto.Response> imageList;
		/* 페어링 댓글 들어갈 예정 */
		private PairingCategory category;
		private Integer likeCount;
		private Integer commentCount;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Total {
		public Total() {
		}

		private Long beerId;
		private Long pairingId;
		private String nickname;
		private String content;
		private String thumbnail;
		private PairingCategory category;
		private Integer likeCount;
		private Integer commentCount;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		public void addCategory(PairingCategory category) {
			this.category = category;
		}

		public void addThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
	}
}
