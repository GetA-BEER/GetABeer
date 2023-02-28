package be.domain.comment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.RatingComment;

@Mapper(componentModel = "spring")
public interface RatingCommentMapper {
	RatingComment ratingCommentPostDtoToRatingComment(RatingCommentDto.Post post);

	RatingComment ratingCommentPatchDtoToRatingComment(RatingCommentDto.Patch patch);

	default RatingCommentDto.Response ratingCommentToRatingCommentResponse(RatingComment ratingComment) {
		if (ratingComment == null) {
			return null;
		}

		RatingCommentDto.Response response = RatingCommentDto.Response.builder()
			.ratingId(ratingComment.getRating().getId())
			.ratingCommentId(ratingComment.getId())
			.userId(ratingComment.getUser().getId())
			.nickname(ratingComment.getUser().getNickname())
			.userImage(ratingComment.getUser().getImageUrl())
			.content(ratingComment.getContent())
			.createdAt(ratingComment.getCreatedAt())
			.modifiedAt(ratingComment.getModifiedAt())
			.build();

		return response;
	}

	default Page<RatingCommentDto.Response> ratingCommentsToResponsePage(Page<RatingComment> ratingComments) {
		return new PageImpl<>(ratingComments.stream()
			.map(ratingComment ->
				new RatingCommentDto.Response(
					ratingComment.getRating().getId(),
					ratingComment.getId(),
					ratingComment.getUser().getId(),
					ratingComment.getUser().getNickname(),
					ratingComment.getUser().getImageUrl(),
					ratingComment.getContent(),
					ratingComment.getCreatedAt(),
					ratingComment.getModifiedAt()
				)).collect(Collectors.toList()), ratingComments.getPageable(), ratingComments.getTotalElements());
	}
}
