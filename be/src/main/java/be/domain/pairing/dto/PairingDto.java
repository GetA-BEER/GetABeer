package be.domain.pairing.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import be.domain.pairing.entity.PairingImage;
import lombok.Builder;
import lombok.Getter;

public class PairingDto {

	@Getter
	@Builder
	public static class Post {
		private String nickname;
		private String content;

		/*
		이미지를 어떻게 받을지에 따라 달라질 듯
		private String imageUrl1;
		private String imageUrl2;
		private String imageUrl3;
		*/

		private String category;
	}

	@Getter
	@Builder
	public static class Patch {

		private String content;

		/*
		이미지를 어떻게 받을지에 따라 달라질 듯
		private String imageUrl1;
		private String imageUrl2;
		private String imageUrl3;
		*/

		private String category;
	}

	@Getter
	public static class Response {
		private Long pairingId;
		private String nickname;
		private ArrayList<PairingImage> imageList;
		private String category;
		private Integer likeCount;
		private Integer recommentCount;
		private LocalDateTime createdAt;
		private LocalDateTime modifiedAt;

		protected Response() {
		}

		@Builder
		public Response(Long pairingId, String nickname, ArrayList<PairingImage> imageList, String category,
			Integer likeCount,
			Integer recommentCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
			this.pairingId = pairingId;
			this.nickname = nickname;
			this.imageList = imageList;
			this.category = category;
			this.likeCount = likeCount;
			this.recommentCount = recommentCount;
			this.createdAt = createdAt;
			this.modifiedAt = modifiedAt;
		}
	}
}
