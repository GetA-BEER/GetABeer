package be.domain.rating.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.rating.dto.RatingRequestDto;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
	Rating ratingPostDtoToRating(RatingRequestDto.Post post);

	Rating ratingPatchDtoToRating(RatingRequestDto.Patch patch);

	default Page<RatingResponseDto.Total> ratingToRatingResponse(List<Rating> ratings) {
		return new PageImpl<>(ratings.stream()
			.map(rating ->
				new RatingResponseDto.Total(
					rating.getBeer().getId(),
					rating.getId(),
					rating.getUser().getId(),
					rating.getUser().getNickname(),
					rating.getContent(),
					rating.getRatingTag().createBeerTagTypeList(),
					rating.getStar(),
					rating.getLikeCount(),
					rating.getCommentCount(),
					false,
					rating.getCreatedAt(),
					rating.getModifiedAt())
			).collect(Collectors.toList()));
	}
}
