package be.domain.like.repository;

import static be.domain.like.entity.QRatingLike.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.like.entity.RatingLike;

public class RatingLikeCustomRepositoryImpl implements RatingLikeCustomRepository{
	private final JPAQueryFactory queryFactory;

	public RatingLikeCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public RatingLike findRatingLike(Long ratingId, Long userId) {

		return queryFactory.selectFrom(ratingLike)
			.where(ratingLike.rating.id.eq(ratingId).and(ratingLike.user.id.eq(userId)))
			.fetchFirst();
	}

	@Override
	public int findRatingLikeUser(Long ratingId, Long userId) {

		return queryFactory.selectFrom(ratingLike)
			.where(ratingLike.rating.id.eq(ratingId).and(ratingLike.user.id.eq(userId)))
			.fetch().size();
	}
}
