package be.domain.pairing.repository;

import static be.domain.pairing.entity.QPairingImage.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.QPairingImageDto_Response;
import be.domain.pairing.entity.PairingImage;

public class PairingImageCustomRepositoryImpl implements PairingImageCustomRepository{
	private final JPAQueryFactory queryFactory;

	public PairingImageCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<PairingImageDto.Response> findPairingImageList(Long pairingId) {

		return queryFactory
			.select(new QPairingImageDto_Response(
				pairingImage.id,
				pairingImage.imageUrl,
				pairingImage.fileName
			))
			.from(pairingImage)
			.where(pairingImage.pairing.id.eq(pairingId))
			.orderBy(pairingImage.imagesOrder.asc())
			.fetch();
	}

	@Override
	public List<PairingImage> findPairingImage(Long pairingId) {

		return queryFactory
			.selectFrom(pairingImage)
			.where(pairingImage.pairing.id.eq(pairingId))
			.orderBy(pairingImage.imagesOrder.asc())
			.fetch();
	}
}
