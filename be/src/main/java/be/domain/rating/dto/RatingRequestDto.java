package be.domain.rating.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

public class RatingRequestDto {

	@Getter
	@Builder
	public static class Post {

		@NotNull
		private Long beerId;

		@NotNull
		private Long userId;
		private String content;

		@NotNull
		private Double star;

		@NotNull
		private String color;

		@NotNull
		private String taste;

		@NotNull
		private String flavor;

		@NotNull
		private String carbonation;
	}

	@Getter
	@Builder
	public static class Patch {

		@NotNull
		private Long beerId;
		private String content;
		private Double star;

		@NotNull
		private String color;

		@NotNull
		private String taste;

		@NotNull
		private String flavor;

		@NotNull
		private String carbonation;
	}
}
