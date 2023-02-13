package be.domain.rating.repository;

import static be.domain.comment.entity.QRatingComment.*;
import static be.domain.rating.entity.QRating.*;
import static be.domain.rating.entity.QRatingTag.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.comment.dto.QRatingCommentDto_Response;
import be.domain.comment.dto.RatingCommentDto;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.entity.RatingTag;
import be.domain.user.entity.User;

public class RatingCustomRepositoryImpl implements RatingCustomRepository {
	private final JPAQueryFactory queryFactory;

	public RatingCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public RatingResponseDto.Detail findDetailRatingResponse(Long ratingId) {
		var response = queryFactory
			.select(Projections.fields(RatingResponseDto.Detail.class,
				rating.beer.id.as("beerId"),
				rating.id.as("ratingId"),
				rating.user.id.as("userId"),
				rating.user.nickname.as("nickname"),
				rating.content,
				rating.star,
				rating.likeCount,
				rating.commentCount,
				rating.createdAt,
				rating.modifiedAt
			)).from(rating)
			.where(rating.id.eq(ratingId))
			.fetchFirst();

		return response;
	}

	@Override
	public RatingTag findTagResponse(Long ratingId) {
		var response = queryFactory.selectFrom(ratingTag)
			.where(ratingTag.rating.id.eq(ratingId))
			.fetchFirst();

		return response;
	}

	@Override
	public List<RatingCommentDto.Response> findRatingCommentResponse(Long ratingId) {
		var list = queryFactory
			.select(new QRatingCommentDto_Response(
				ratingComment.rating.id.as("ratingId"),
				ratingComment.id.as("ratingCommentId"),
				ratingComment.user.id.as("userId"),
				ratingComment.user.nickname.as("nickname"),
				ratingComment.content,
				rating.createdAt,
				ratingComment.modifiedAt
			)).from(ratingComment)
			.where(ratingComment.rating.id.eq(ratingId)).fetch();

		return list;
	}

	@Override
	public Page<RatingResponseDto.Total> findRatingTotalResponseOrderByRecent(Long beerId, Pageable pageable) {
		var list = queryFactory
			.select(Projections.fields(RatingResponseDto.Total.class,
				rating.beer.id.as("beerId"),
				rating.id.as("ratingId"),
				rating.user.id.as("userId"),
				rating.user.nickname.as("nickname"),
				rating.content,
				rating.star,
				rating.likeCount,
				rating.commentCount,
				rating.createdAt,
				rating.modifiedAt
			)).from(rating)
			.where(rating.beer.id.eq(beerId))
			.orderBy(rating.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	@Override
	public Page<RatingResponseDto.Total> findRatingTotalResponseOrderByLikes(Long beerId, Pageable pageable) {
		var list = queryFactory
			.select(Projections.fields(RatingResponseDto.Total.class,
				rating.beer.id.as("beerId"),
				rating.id.as("ratingId"),
				rating.user.id.as("userId"),
				rating.user.nickname.as("nickname"),
				rating.content,
				rating.star,
				rating.likeCount,
				rating.commentCount,
				rating.createdAt,
				rating.modifiedAt
			)).from(rating)
			.where(rating.beer.id.eq(beerId))
			.orderBy(rating.likeCount.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	@Override
	public Page<RatingResponseDto.Total> findRatingTotalResponseOrderByComments(Long beerId, Pageable pageable) {
		var list = queryFactory
			.select(Projections.fields(RatingResponseDto.Total.class,
				rating.beer.id.as("beerId"),
				rating.id.as("ratingId"),
				rating.user.id.as("userId"),
				rating.user.nickname.as("nickname"),
				rating.content,
				rating.star,
				rating.likeCount,
				rating.commentCount,
				rating.createdAt,
				rating.modifiedAt
			)).from(rating)
			.where(rating.beer.id.eq(beerId))
			.orderBy(rating.commentCount.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	@Override
	public Page<Rating> findRatingByUser(User user, Pageable pageable) {
		List<Rating> ratings = queryFactory
			.select(rating)
			.from(rating)
			.where(rating.user.eq(user))
			.orderBy(rating.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(rating.count())
			.from(rating)
			.fetchOne();

		return new PageImpl<>(ratings, pageable, total);
	}

}
