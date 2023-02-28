package be.domain.comment.repository.rating;

import static be.domain.comment.entity.QRatingComment.*;
import static be.domain.pairing.entity.QPairing.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.comment.dto.QRatingCommentDto_Response;
import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.RatingComment;
import be.domain.user.entity.User;

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
				ratingComment.user.imageUrl,
				ratingComment.content,
				ratingComment.createdAt,
				ratingComment.modifiedAt
			)).from(ratingComment)
			.where(ratingComment.rating.id.eq(ratingId))
			.fetch();

		return list;
	}

	@Override
	public Page<RatingComment> findRatingCommentByUser(User user, Pageable pageable) {
		List<RatingComment> ratingComments = queryFactory
			.select(ratingComment)
			.from(ratingComment)
			.where(ratingComment.user.id.eq(user.getId()))
			.orderBy(ratingComment.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(pairing.count())
			.from(pairing)
			.fetchOne();

		return new PageImpl<>(ratingComments, pageable, total);
	}
}
