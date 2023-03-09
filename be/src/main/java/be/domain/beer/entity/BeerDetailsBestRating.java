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

	private Long bestRatingId;
	private String bestNickname;
	private String profileImage;
	private Double bestStar;
	private String bestContent;

	@Builder
	public BeerDetailsBestRating(Long bestRatingId, String bestNickname, String profileImage, Double bestStar,
		String bestContent) {
		this.bestRatingId = bestRatingId;
		this.bestNickname = bestNickname;
		this.profileImage = profileImage;
		this.bestStar = bestStar;
		this.bestContent = bestContent;
	}

	public Rating createRating() {
		return Rating.builder()
			.id(bestRatingId)
			// .nickname(bestNickname)
			.star(bestStar)
			.content(bestContent)
			.build();
	}

}
