package be.domain.pairing.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class PairingCustomRepositoryImpl implements PairingCustomRepository{

	private final JPAQueryFactory queryFactory;

	public PairingCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
}
