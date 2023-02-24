package be.domain.pairing.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import be.domain.pairing.entity.PairingCategory;
import lombok.Builder;
import lombok.Getter;

public class PairingRequestDto {

	@Getter
	@Builder
	public static class Post {

		@NotNull
		private Long beerId;

		@NotNull
		private String content;

		@NotNull
		private PairingCategory category;
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
		private  PairingCategory category;
	}
}
