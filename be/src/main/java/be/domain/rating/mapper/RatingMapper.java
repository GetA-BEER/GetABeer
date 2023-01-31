package be.domain.rating.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import be.domain.rating.dto.RatingDto;
import be.domain.rating.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
	Rating beerCommentPostDtoToBeerComment(RatingDto.Post post);

	Rating beerCommentPatchDtoToBeerComment(RatingDto.Patch patch);

	default RatingDto.Response beerCommentToBeerCommentResponse(Rating beerComment) {
		if (beerComment == null) {
			return null;
		}

		RatingDto.Response response = RatingDto.Response.builder()
			.beerCommentId(beerComment.getId())
			.nickname(beerComment.getNickname())
			.content(beerComment.getContent())
			.star(beerComment.getStar())
			.likeCount(beerComment.getLikeCount())
			.recommentCount(beerComment.getRecommentCount())
			.beerRecommentList(beerComment.getBeerRecommentList())
			.createdAt(beerComment.getCreatedAt())
			.modifiedAt(beerComment.getModifiedAt())
			.build();

		return response;
	}

	List<RatingDto.Response> beerCommentToBeerCommentResponse(List<Rating> beerCommentList);
}
