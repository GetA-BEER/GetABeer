package be.domain.pairing.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

public class PairingImageDto {
	@Getter
	public static class Response {
		private Long pairingImageId;
		private String imageUrl;
		private String fileName;

		@Builder
		@QueryProjection
		public Response(Long pairingImageId, String imageUrl, String fileName) {
			this.pairingImageId = pairingImageId;
			this.imageUrl = imageUrl;
			this.fileName = fileName;
		}
	}
}
