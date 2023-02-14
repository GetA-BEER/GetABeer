package be.domain.pairing.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

public class PairingRequestDto {

	@Getter
	@Builder
	public static class Post {

		@NotBlank
		private Long beerId;

		@NotBlank
		private Long userId;

		@NotBlank
		private String content;

		@NotBlank
		private String category;
	}

	@Getter
	@Builder
	public static class Patch {

		@NotBlank
		private Long beerId;
		private String content;

		@NotBlank
		private List<String> type;
		private List<Long> url;

		@NotBlank
		private String category;
	}
}
