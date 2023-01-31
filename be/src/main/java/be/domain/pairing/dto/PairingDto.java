package be.domain.pairing.dto;

import java.time.LocalDateTime;
import java.util.List;

import be.domain.pairing.entity.PairingCategory;
import lombok.Builder;
import lombok.Getter;

public class PairingDto {

	@Getter
	@Builder
	public static class Post {
		private Long beerId;
		private String nickname;
		private String content;
		private String imageUrl1;
		private String imageUrl2;
		private String imageUrl3;
		private String category;
	}

	@Getter
	@Builder
	public static class Patch {
		private Long beerId;
		private String content;
		private String imageUrl1;
		private String imageUrl2;
		private String imageUrl3;
		private String category;
	}

	@Getter
	public static class Response {
		private Long beerId;
		private Long pairingId;
		private String nickname;
		private String content;
		private List<PairingImageDto.Response> imageList;
		private PairingCategory category;
		private Integer likeCount;
		private Integer commentCount;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		protected Response() {
		}

		@Builder
		public Response(Long beerId, Long pairingId, String nickname, String content,
			List<PairingImageDto.Response> imageList,
			PairingCategory category, Integer likeCount, Integer commentCount, LocalDateTime createdAt,
			LocalDateTime modifiedAt) {
			this.beerId = beerId;
			this.pairingId = pairingId;
			this.nickname = nickname;
			this.content = content;
			this.imageList = imageList;
			this.category = category;
			this.likeCount = likeCount;
			this.commentCount = commentCount;
			this.createdAt = createdAt;
			this.modifiedAt = modifiedAt;
		}
	}
}
