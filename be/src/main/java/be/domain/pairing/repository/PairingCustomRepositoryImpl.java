package be.domain.pairing.repository;

import static be.domain.pairing.entity.QPairing.*;
import static be.domain.pairing.entity.QPairingImage.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
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
				pairingImage.imageUrl,
				pairingImage.fileName
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
	public PairingResponseDto.Detail findPairingDetailResponseDto(Long pairingId) {
		var result = queryFactory
			.select(Projections.fields(PairingResponseDto.Detail.class,
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
			.where(pairing.id.eq(pairingId))
			.fetchFirst();

		return result;
	}

	/* 로그인 유저가 없는 경우 */
	@Override
	public Page<PairingResponseDto.Total> findPairingTotalResponseOrder(Long beerId, Pageable pageable) {
		var list = orderByPageable(beerId, pageable);

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	/* 로그인 유저가 있는 경우 */
	@Override
	public Page<PairingResponseDto.Total> findPairingTotalResponseOrder(Long beerId, Long userId,
		Pageable pageable) {
		List<PairingResponseDto.Total> list;

		if (isUserWritePairing(beerId, userId)) {
			list = orderByUserPairingFirst(beerId, userId, pageable);
		} else {
			list = orderByPageable(beerId, pageable);
		}

		return PageableExecutionUtils.getPage(list, pageable, list::size);
	}

	// -------------------------------------------- 조회 관련 메서드 ---------------------------------------------------
	/* 유저가 글을 작성하였는지 확인 */
	private boolean isUserWritePairing(Long beerId, Long userId) {
		var userList = queryFactory
			.selectFrom(pairing).where(pairing.beer.id.eq(beerId).and(pairing.user.id.eq(userId)))
			.fetch();

		return userList.size() != 0;
	}

	/* 로그인을 하지 않았거나 해당 유저가 글을 작성하지 않은 경우 */
	private List<PairingResponseDto.Total> orderByPageable(Long beerId, Pageable pageable) {

		return queryFactory
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
			.orderBy(createOrderSpecifier(pageable).toArray(OrderSpecifier[]::new))
			.fetch();
	}

	/* 유저가 글을 작성하였다면, 본인의 글 중 가장 추천이 높은 것 1개를 가장 앞에 위치 */
	private List<PairingResponseDto.Total> orderByUserPairingFirst(Long beerId, Long userId, Pageable pageable) {

		var first = queryFactory.selectFrom(pairing)
			.where(pairing.beer.id.eq(beerId).and(pairing.user.id.eq(userId)))
			.orderBy(pairing.likeCount.desc(), pairing.id.desc())
			.fetchFirst();

		var sorting = new CaseBuilder()
			.when(pairing.user.id.eq(userId).and(pairing.id.eq(first.getId()))).then(1)
			.otherwise(2);

		return queryFactory
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
			.orderBy(sorting.asc())
			.orderBy(createOrderSpecifier(pageable).toArray(OrderSpecifier[]::new))
			.fetch();
	}

	private List<OrderSpecifier> createOrderSpecifier(Pageable pageable) {
		List<OrderSpecifier> list = new ArrayList<>();
		if (!pageable.getSort().isEmpty()) {
			for (Sort.Order o : pageable.getSort()) {
				switch (o.getProperty()) {
					case "pairingId" : list.add(new OrderSpecifier<>(Order.DESC, pairing.id));
					case "likeCount" : list.add(new OrderSpecifier<>(Order.DESC, pairing.likeCount));
					case "commentCount" : list.add(new OrderSpecifier<>(Order.DESC, pairing.commentCount));
				}
			}
		}

		return list;
	}

	// ---------------------------------------------------------------------------------------------------------------
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
