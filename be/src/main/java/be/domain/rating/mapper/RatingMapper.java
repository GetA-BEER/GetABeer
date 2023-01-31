package be.domain.rating.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import be.domain.rating.dto.RatingDto;
import be.domain.rating.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
	Rating ratingPostDtoToRating(RatingDto.Post post);

	Rating ratingPatchDtoToRating(RatingDto.Patch patch);

	default RatingDto.Response ratingToRatingResponse(Rating rating) {
		if (rating == null) {
			return null;
		}

		RatingDto.Response response = RatingDto.Response.builder()
			.beerId(rating.getBeer().getId())
			.beerCommentId(rating.getId())
			.nickname(rating.getNickname())
			.content(rating.getContent())
			.star(rating.getStar())
			.likeCount(rating.getLikeCount())
			.recommentCount(rating.getRecommentCount())
			.beerRecommentList(rating.getBeerRecommentList())
			.createdAt(rating.getCreatedAt())
			.modifiedAt(rating.getModifiedAt())
			.build();

		return response;
	}

	List<RatingDto.Response> ratingToRatingResponse(List<Rating> ratingList);
}
