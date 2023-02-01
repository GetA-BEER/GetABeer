package be.domain.pairing.dto;

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
}
