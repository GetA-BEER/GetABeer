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
	private Long bestUserId;
	private String bestNickname;
	private String profileImage;
	private Double bestStar;
	private String bestContent;
	private Integer bestLikeCount;

	@Builder
	public BeerDetailsBestRating(Long bestRatingId, Long bestUserId, String bestNickname, String profileImage,
		Double bestStar, String bestContent, Integer bestLikeCount) {
		this.bestRatingId = bestRatingId;
		this.bestUserId = bestUserId;
		this.bestNickname = bestNickname;
		this.profileImage = profileImage;
		this.bestStar = bestStar;
		this.bestContent = bestContent;
		this.bestLikeCount = bestLikeCount;
	}

	public Rating createRating() {
		return Rating.builder()
			.id(bestRatingId)
			.nickname(bestNickname)
			.star(bestStar)
			.content(bestContent)
			.build();
	}

}
