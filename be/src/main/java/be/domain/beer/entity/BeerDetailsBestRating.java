package be.domain.beer.entity;

import javax.persistence.Embeddable;

import be.domain.rating.entity.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class BeerDetailsBestRating {

	private Long ratingId;
	private String nickname;
	private Double star;
	private String content;

	@Builder
	public BeerDetailsBestRating(Long ratingId, String nickname, Double star, String content) {
		this.ratingId = ratingId;
		this.nickname = nickname;
		this.star = star;
		this.content = content;
	}

	public Rating createRating() {
		return Rating.builder()
			.id(ratingId)
			.nickname(nickname)
			.star(star)
			.content(content)
			.build();
	}
}
