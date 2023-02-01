package be.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class RatingCommentCustomRepositoryImpl implements RatingCommentCustomRepository {

	private final JPAQueryFactory queryFactory;

	public RatingCommentCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
}
