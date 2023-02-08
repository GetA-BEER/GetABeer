package be.domain.pairing.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class PairingRequestDto {

	@Getter
	@Builder
	public static class Post {
		private Long beerId;
		private Long userId;
		private String content;
		private List<String> image;
		private String category;
	}

	@Getter
	@Builder
	public static class Patch {
		private Long beerId;
		private String content;
		private List<String> image;
		private String category;
	}
}
