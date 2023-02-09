package be.domain.pairing.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;

public class PairingRequestDto {

	@Getter
	@Builder
	public static class Post {
		private Long beerId;
		private Long userId;
		private String content;
		// private List<String> image;
		private List<MultipartFile> files;
		private String category;
	}

	@Getter
	@Builder
	public static class Patch {
		private Long beerId;
		private String content;
		private List<String> imageType;
		private List<String> imageUrl;
		private String category;
	}
}
