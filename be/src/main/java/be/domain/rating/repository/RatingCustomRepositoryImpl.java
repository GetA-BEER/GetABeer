package be.domain.rating.repository;

import static be.domain.comment.entity.QRatingComment.*;
import static be.domain.rating.entity.QRating.*;
import static be.domain.rating.entity.QRatingTag.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
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
	public Rating findRatingByUserId(Long userId) {

		return queryFactory.selectFrom(rating)
			.where(rating.user.id.eq(userId))
			.fetchFirst();
	}

	@Override
	public Page<RatingResponseDto.Total> findRatingTotalResponseOrder(Long beerId, Pageable pageable) {
		var list = orderByPageable(beerId, pageable);

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	@Override
	public Page<RatingResponseDto.Total> findRatingTotalResponseOrder(Long beerId, Long userId, Pageable pageable) {
		List<RatingResponseDto.Total> list;

		if (isUserWritePairing(beerId, userId)) {
			list = orderByUserRatingFirst(beerId, userId, pageable);
		} else {
			list = orderByPageable(beerId, pageable);
		}

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	/* 유저가 글을 작성하였는지 확인 */
	private boolean isUserWritePairing(Long beerId, Long userId) {
		var userList = queryFactory
			.selectFrom(rating).where(rating.beer.id.eq(beerId).and(rating.user.id.eq(userId)))
			.fetch();

		return userList.size() != 0;
	}

	/* 로그인을 하지 않았거나 해당 유저가 글을 작성하지 않은 경우 */
	private List<RatingResponseDto.Total> orderByPageable(Long beerId, Pageable pageable) {

		return queryFactory
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
			.orderBy(createOrderSpecifier(pageable).toArray(OrderSpecifier[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	/* 로그인 유저가 존재하며, 해당 유저가 글을 작성한 경우 */
	private List<RatingResponseDto.Total> orderByUserRatingFirst(Long beerId, Long userId, Pageable pageable) {
		var sorting = new CaseBuilder()
			.when(rating.user.id.eq(userId)).then(1)
			.otherwise(2);

		return queryFactory
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
			.orderBy(sorting.asc())
			.orderBy(createOrderSpecifier(pageable).toArray(OrderSpecifier[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	private List<OrderSpecifier> createOrderSpecifier(Pageable pageable) {
		List<OrderSpecifier> list = new ArrayList<>();
		if (!pageable.getSort().isEmpty()) {
			for (Sort.Order o : pageable.getSort()) {
				switch (o.getProperty()) {
					case "ratingId" : list.add(new OrderSpecifier<>(Order.DESC, rating.id));
					case "likeCount" : list.add(new OrderSpecifier<>(Order.DESC, rating.likeCount));
					case "commentCount" : list.add(new OrderSpecifier<>(Order.DESC, rating.commentCount));
				}
			}
		}

		return list;
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
