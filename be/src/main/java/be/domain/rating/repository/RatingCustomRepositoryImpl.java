package be.domain.rating.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class RatingCustomRepositoryImpl implements RatingCustomRepository {
	private final JPAQueryFactory queryFactory;

	public RatingCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
}
