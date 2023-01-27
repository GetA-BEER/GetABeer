package be.domain.recomment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class PairingRecommentRepositoryImpl implements PairingRecommentCustomRepository{

	private final JPAQueryFactory queryFactory;

	public PairingRecommentRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
}
