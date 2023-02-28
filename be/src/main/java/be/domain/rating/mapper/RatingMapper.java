package be.domain.rating.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.like.repository.RatingLikeRepository;
import be.domain.rating.dto.RatingRequestDto;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
	Rating ratingPostDtoToRating(RatingRequestDto.Post post);

	Rating ratingPatchDtoToRating(RatingRequestDto.Patch patch);

	default Page<RatingResponseDto.MyPageResponse> ratingToRatingResponse(Page<Rating> ratings,
		RatingLikeRepository ratingLikeRepository) {
		return new PageImpl<>(ratings.stream()
			.map(rating ->
				new RatingResponseDto.MyPageResponse(
					rating.getBeer().getId(),
					rating.getId(),
					rating.getUser().getId(),
					rating.getUser().getNickname(),
					rating.getUser().getImageUrl(),
					rating.getContent(),
					rating.getRatingTag().createBeerTagTypeList(),
					rating.getStar(),
					rating.getLikeCount(),
					rating.getCommentCount(),
					ratingLikeRepository.findRatingLikeUser(rating.getId(), rating.getUser().getId()) != 0,
					rating.getCreatedAt(),
					rating.getModifiedAt())
			).collect(Collectors.toList()), ratings.getPageable(), ratings.getTotalElements());
	}
}
