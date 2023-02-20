package be.domain.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeResponseDto {
	private Boolean isUserLikes;

	protected LikeResponseDto() {
	}

	@Builder
	public LikeResponseDto(Boolean isUserLikes) {
		this.isUserLikes = isUserLikes;
	}
}
