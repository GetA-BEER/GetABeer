package be.domain.pairing.repository;

import static be.domain.pairing.entity.QPairing.*;
import static be.domain.pairing.entity.QPairingImage.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.dto.QPairingImageDto_Response;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;
import be.domain.user.entity.User;

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
				// pairingImage.fileName
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

	@Override
	public Page<PairingResponseDto.Total> findPairingTotalResponseOrderByRecent(Long beerId, Pageable pageable) {
		var list = queryFactory
			.select(Projections.fields(PairingResponseDto.Total.class,
				pairing.beer.id.as("beerId"),
				pairing.id.as("pairingId"),
				pairing.user.id.as("userId"),
				pairing.user.nickname.as("nickname"),
				pairing.content,
				pairing.likeCount,
				pairing.commentCount,
				pairing.createdAt,
				pairing.modifiedAt
			)).from(pairing)
			.where(pairing.beer.id.eq(beerId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(pairing.id.desc())
			.fetch();

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	@Override
	public Page<PairingResponseDto.Total> findPairingTotalResponseOrderByLikes(Long beerId, Pageable pageable) {
		var list = queryFactory
			.select(Projections.fields(PairingResponseDto.Total.class,
				pairing.beer.id.as("beerId"),
				pairing.id.as("pairingId"),
				pairing.user.id.as("userId"),
				pairing.user.nickname.as("nickname"),
				pairing.content,
				pairing.likeCount,
				pairing.commentCount,
				pairing.createdAt,
				pairing.modifiedAt
			)).from(pairing)
			.where(pairing.beer.id.eq(beerId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(pairing.likeCount.desc())
			.fetch();

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	@Override
	public Page<PairingResponseDto.Total> findPairingTotalResponseOrderByComments(Long beerId, Pageable pageable) {
		var list = queryFactory
			.select(Projections.fields(PairingResponseDto.Total.class,
				pairing.beer.id.as("beerId"),
				pairing.id.as("pairingId"),
				pairing.user.id.as("userId"),
				pairing.user.nickname.as("nickname"),
				pairing.content,
				pairing.likeCount,
				pairing.commentCount,
				pairing.createdAt,
				pairing.modifiedAt
			)).from(pairing)
			.where(pairing.beer.id.eq(beerId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(pairing.commentCount.desc())
			.fetch();

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	@Override
	public Page<Pairing> findPairingByUser(User user, Pageable pageable) {
		List<Pairing> pairings = queryFactory
			.select(pairing)
			.from(pairing)
			.where(pairing.user.eq(user))
			.orderBy(pairing.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(pairing.count())
			.from(pairing)
			.fetchOne();

		return new PageImpl<>(pairings, pageable, total);
	}
}
