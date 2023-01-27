package be.domain.recomment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class BeerRecommentCustomRepositoryImpl implements BeerRecommentCustomRepository{

	private final JPAQueryFactory queryFactory;

	public BeerRecommentCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
}
