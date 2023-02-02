package be.domain.pairing.repository;

import static be.domain.pairing.entity.QPairingImage.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.QPairingImageDto_Response;
import be.domain.pairing.entity.PairingImage;

public class PairingCustomRepositoryImpl implements PairingCustomRepository {

	private final JPAQueryFactory queryFactory;

	public PairingCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<PairingImageDto.Response> findPairingImageList(Long pairingId) {

		List<PairingImageDto.Response> result = queryFactory
			.select(new QPairingImageDto_Response(
				pairingImage.id,
				pairingImage.imageUrl
			))
			.from(pairingImage)
			.where(pairingImage.pairing.id.eq(pairingId))
			.fetch();

		return result;
	}

	@Override
	public List<PairingImage> findPairingImage(Long pairingId) {

		List<PairingImage> result = queryFactory
			.selectFrom(pairingImage)
			.where(pairingImage.pairing.id.eq(pairingId))
			.fetch();

		return result;
	}
}
