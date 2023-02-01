package be.domain.pairing.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

public class PairingImageDto {

	@Getter
	public static class Response {
		private String imageUrl1;
		private String imageUrl2;
		private String imageUrl3;

		@Builder
		public Response(String imageUrl1, String imageUrl2, String imageUrl3) {
			this.imageUrl1 = imageUrl1;
			this.imageUrl2 = imageUrl2;
			this.imageUrl3 = imageUrl3;
		}
	}

	@Getter
	public static class Response2 {
		private Long pairingImageId;
		private String imageUrl;

		@Builder
		@QueryProjection
		public Response2(Long pairingImageId, String imageUrl) {
			this.pairingImageId = pairingImageId;
			this.imageUrl = imageUrl;
		}
	}
}
