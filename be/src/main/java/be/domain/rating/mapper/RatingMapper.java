package be.domain.rating.mapper;

import org.mapstruct.Mapper;

import be.domain.rating.dto.RatingRequestDto;
import be.domain.rating.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
	Rating ratingPostDtoToRating(RatingRequestDto.Post post);

	Rating ratingPatchDtoToRating(RatingRequestDto.Patch patch);
}
