package be.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class PairingCommentRepositoryImpl implements PairingCommentCustomRepository {

	private final JPAQueryFactory queryFactory;

	public PairingCommentRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
}
