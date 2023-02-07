package be.domain.rating.dto;

import lombok.Builder;
import lombok.Getter;

public class RatingRequestDto {

	@Getter
	@Builder
	public static class Post {
		private Long beerId;
		private Long userId;
		private String content;
		private Double star;
		private String color;
		private String taste;
		private String flavor;
		private String carbonation;
	}

	@Getter
	@Builder
	public static class Patch {
		private Long beerId;
		private String content;
		private Double star;
		private String color;
		private String taste;
		private String flavor;
		private String carbonation;
	}
}
