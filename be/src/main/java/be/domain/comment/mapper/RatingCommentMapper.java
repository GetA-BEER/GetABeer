package be.domain.comment.mapper;

import org.mapstruct.Mapper;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.RatingComment;

@Mapper(componentModel = "spring")
public interface RatingCommentMapper {
	RatingComment ratingCommentPostDtoToRatingComment(RatingCommentDto.Post post);
	RatingComment ratingCommentPatchDtoToRatingComment(RatingCommentDto.Patch patch);

	default RatingCommentDto.Response ratingCommentToRatingCommentResponse(RatingComment ratingComment){
		if (ratingComment == null) {
			return null;
		}

		RatingCommentDto.Response response = RatingCommentDto.Response.builder()
			.ratingId(ratingComment.getRating().getId())
			.ratingCommentId(ratingComment.getId())
			.nickname(ratingComment.getNickname())
			.content(ratingComment.getContent())
			.createdAt(ratingComment.getCreatedAt())
			.modifiedAt(ratingComment.getModifiedAt())
			.build();

		return response;
	}
}
