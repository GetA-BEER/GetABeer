package be.domain.rating.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.rating.dto.RatingRequestDto;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.dto.RatingTagDto;
import be.domain.rating.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
	Rating ratingPostDtoToRating(RatingRequestDto.Post post);

	Rating ratingPatchDtoToRating(RatingRequestDto.Patch patch);

	default RatingResponseDto.Detail ratingToRatingResponse(Rating rating, Long beerId) {
		if (rating == null) {
			return null;
		}

		RatingResponseDto.Detail response = RatingResponseDto.Detail.builder()
			.beerId(beerId)
			.ratingId(rating.getId())
			.nickname(rating.getNickname())
			.content(rating.getContent())
			.ratingTag(getRatingTagResponse(rating))
			.star(rating.getStar())
			.likeCount(rating.getLikeCount())
			.commentCount(rating.getCommentCount())
			.ratingCommentList(getRatingCommentResponse(rating))
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

	private List<RatingCommentDto.Response> getRatingCommentResponse(Rating rating) {
		if (rating.getRatingCommentList().size() == 0) {
			return new ArrayList<>();
		}

		List<RatingCommentDto.Response> result = new ArrayList<>();

		for (int i = 0; i <= rating.getRatingCommentList().size(); i++) {
			RatingCommentDto.Response response = RatingCommentDto.Response.builder()
				.ratingId(rating.getId())
				.ratingCommentId(rating.getRatingCommentList().get(i).getId())
				.nickname(rating.getRatingCommentList().get(i).getNickname())
				.content(rating.getRatingCommentList().get(i).getContent())
				.createdAt(rating.getRatingCommentList().get(i).getCreatedAt())
				.modifiedAt(rating.getRatingCommentList().get(i).getModifiedAt())
				.build();

			result.add(response);
		}

		return result;
	}
}
