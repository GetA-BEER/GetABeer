package be.domain.like.repository;

import static be.domain.like.entity.QPairingLike.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.like.entity.PairingLike;

public class PairingLikeCustomRepositoryImpl implements PairingLikeCustomRepository{

	private final JPAQueryFactory queryFactory;

	public PairingLikeCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public PairingLike findPairingLike(Long pairingId, Long userId) {
		var result = queryFactory
			.selectFrom(pairingLike)
			.where(pairingLike.pairing.id.eq(pairingId).and(pairingLike.user.id.eq(userId)))
			.fetchFirst();

		return result;
	}

	@Override
	public int findPairingLikeUser(Long pairingId, Long userId) {

		return queryFactory
			.selectFrom(pairingLike)
			.where(pairingLike.pairing.id.eq(pairingId).and(pairingLike.user.id.eq(userId)))
			.fetch().size();
	}
}
