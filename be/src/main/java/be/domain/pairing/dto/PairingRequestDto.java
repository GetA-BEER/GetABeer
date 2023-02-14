package be.domain.pairing.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

public class PairingRequestDto {

	@Getter
	@Builder
	public static class Post {

		@NotNull
		private Long beerId;

		@NotNull
		private Long userId;

		@NotNull
		private String content;

		@NotNull
		private String category;
	}

	@Getter
	@Builder
	public static class Patch {

		@NotNull
		private Long beerId;
		private String content;

		@NotNull
		private List<String> type;
		private List<Long> url;

		@NotNull
		private String category;
	}
}
