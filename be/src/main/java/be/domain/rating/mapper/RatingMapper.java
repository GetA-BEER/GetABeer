package be.domain.rating.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import be.domain.beertag.entity.BeerTagType;
import be.domain.rating.dto.RatingDto;
import be.domain.rating.dto.RatingTagDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;

@Mapper(componentModel = "spring")
public interface RatingMapper {
	Rating ratingPostDtoToRating(RatingDto.Post post);

	default RatingTag ratingPostDtoToRatingTag(RatingDto.Post post) {
		if (post == null) {
			return null;
		}

		RatingTag ratingTag = RatingTag.builder()
			.color(BeerTagType.valueOf(post.getColor()))
			.taste(BeerTagType.valueOf(post.getTaste()))
			.flavor(BeerTagType.valueOf(post.getFlavor()))
			.carbonation(BeerTagType.valueOf(post.getCarbonation()))
			.build();

		return ratingTag;
	}

	Rating ratingPatchDtoToRating(RatingDto.Patch patch);

	default RatingDto.Response ratingToRatingResponse(Rating rating, Long beerId) {
		if (rating == null) {
			return null;
		}

		RatingDto.Response response = RatingDto.Response.builder()
			.beerId(beerId)
			.ratingId(rating.getId())
			.nickname(rating.getNickname())
			.content(rating.getContent())
			.ratingTag(getRatingTagResponse(rating))
			.star(rating.getStar())
			.likeCount(rating.getLikeCount())
			.commentCount(rating.getCommentCount())
			.ratingCommentList(rating.getRatingCommentList())
			.createdAt(rating.getCreatedAt())
			.modifiedAt(rating.getModifiedAt())
			.build();

		return response;
	}

	private List<RatingTagDto.Response> getRatingTagResponse(Rating rating) {
		if (rating.getRatingTag() == null) {
			return null;
		}

		RatingTagDto.Response response = RatingTagDto.Response.builder()
			.color(rating.getRatingTag().getColor())
			.taste(rating.getRatingTag().getTaste())
			.flavor(rating.getRatingTag().getFlavor())
			.carbonation(rating.getRatingTag().getCarbonation())
			.build();

		return List.of(response);
	}

	// List<RatingDto.Response> ratingToRatingResponse(List<Rating> ratingList);
}
