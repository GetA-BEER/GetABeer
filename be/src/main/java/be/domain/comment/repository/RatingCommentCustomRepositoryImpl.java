package be.domain.comment.repository;

import static be.domain.comment.entity.QRatingComment.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.comment.dto.QRatingCommentDto_Response;
import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.RatingComment;

public class RatingCommentCustomRepositoryImpl implements RatingCommentCustomRepository {

	private final JPAQueryFactory queryFactory;

	public RatingCommentCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<RatingCommentDto.Response> findRatingCommentList(Long ratingId) {
		var list = queryFactory
			.select(new QRatingCommentDto_Response(
				ratingComment.rating.id,
				ratingComment.id,
				ratingComment.user.id,
				ratingComment.user.nickname,
				ratingComment.content,
				ratingComment.createdAt,
				ratingComment.modifiedAt
			)).from(ratingComment)
			.where(ratingComment.rating.id.eq(ratingId))
			.fetch();

		return list;
	}
}
