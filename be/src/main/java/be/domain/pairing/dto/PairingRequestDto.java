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
		// private List<MultipartFile> files;
		private String category;
	}

	@Getter
	@Builder
	public static class Patch {
		private Long beerId;
		private String content;
		private List<String> type;
		private List<Long> url;
		// private List<MultipartFile> newFile;
		private String category;
	}
}
