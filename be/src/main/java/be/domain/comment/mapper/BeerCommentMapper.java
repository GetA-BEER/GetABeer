package be.domain.comment.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import be.domain.comment.dto.BeerCommentDto;
import be.domain.comment.entity.BeerComment;

@Mapper(componentModel = "spring")
public interface BeerCommentMapper {
	BeerComment beerCommentPostDtoToBeerComment(BeerCommentDto.Post post);

	BeerComment beerCommentPatchDtoToBeerComment(BeerCommentDto.Patch patch);

	default BeerCommentDto.Response beerCommentToBeerCommentResponse(BeerComment beerComment) {
		if (beerComment == null)
			return null;

		BeerCommentDto.Response response = BeerCommentDto.Response.builder()
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

	List<BeerCommentDto.Response> beerCommentToBeerCommentResponse(List<BeerComment> beerCommentList);
}
