package be.domain.pairing.repository;

import static be.domain.pairing.entity.QPairingImage2.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.QPairingImageDto_Response2;
import be.domain.pairing.entity.PairingImage2;

public class PairingCustomRepositoryImpl implements PairingCustomRepository {

	private final JPAQueryFactory queryFactory;

	public PairingCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<PairingImageDto.Response2> findPairingImageList(Long pairingId) {

		List<PairingImageDto.Response2> result = queryFactory
			.select(new QPairingImageDto_Response2(
				pairingImage2.id,
				pairingImage2.imageUrl
			))
			.from(pairingImage2)
			.where(pairingImage2.pairing.id.eq(pairingId))
			.fetch();

		return result;
	}

	@Override
	public List<PairingImage2> findPairingImage(Long pairingId) {

		List<PairingImage2> result = queryFactory
			.selectFrom(pairingImage2)
			.where(pairingImage2.pairing.id.eq(pairingId))
			.fetch();

		return result;
	}
}
