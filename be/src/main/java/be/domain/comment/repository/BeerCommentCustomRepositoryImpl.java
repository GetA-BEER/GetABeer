package be.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class BeerCommentCustomRepositoryImpl implements BeerCommentCustomRepository {
	private final JPAQueryFactory queryFactory;

	public BeerCommentCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
}
