package be.domain.rating.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

public class RatingRequestDto {

	@Getter
	@Builder
	public static class Post {

		@NotBlank
		private Long beerId;

		@NotBlank
		private Long userId;
		private String content;

		@NotBlank
		private Double star;

		@NotBlank
		private String color;

		@NotBlank
		private String taste;

		@NotBlank
		private String flavor;

		@NotBlank
		private String carbonation;
	}

	@Getter
	@Builder
	public static class Patch {

		@NotBlank
		private Long beerId;
		private String content;
		private Double star;

		@NotBlank
		private String color;

		@NotBlank
		private String taste;

		@NotBlank
		private String flavor;

		@NotBlank
		private String carbonation;
	}
}
