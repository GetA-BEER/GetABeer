package be.domain.pairing.repository;

import static be.domain.pairing.entity.QPairing.*;

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

import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingCategory;
import be.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PairingCustomRepositoryImpl implements PairingCustomRepository {

	private final JPAQueryFactory queryFactory;

	public PairingCustomRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public PairingResponseDto.Detail findPairingDetailResponseDto(Long pairingId) {

		return queryFactory
			.select(Projections.fields(PairingResponseDto.Detail.class,
				pairing.beer.id.as("beerId"),
				pairing.beer.beerDetailsBasic.korName.as("korName"),
				pairing.id.as("pairingId"),
				pairing.user.id.as("userId"),
				pairing.user.nickname.as("nickname"),
				pairing.user.imageUrl.as("userImage"),
				pairing.content,
				pairing.thumbnail,
				pairing.likeCount,
				pairing.commentCount,
				pairing.createdAt,
				pairing.modifiedAt
				)).from(pairing)
			.where(pairing.id.eq(pairingId))
			.fetchFirst();
	}

	/* 로그인 하지 않은 유저 + 카테고리 전체 조회 */
	@Override
	public Page<PairingResponseDto.Total> findPairingTotalResponseGetALL(Long beerId, String type,
		Pageable pageable) {
		var list = getAllOrderByPageable(beerId, type, pageable);
		var total = getAllSize(beerId);

		return PageableExecutionUtils.getPage(list, pageable, () -> total);
	}

	/* 로그인 하지 않은 유저 + 카테고리 별 조회 */
	@Override
	public Page<PairingResponseDto.Total> findPairingTotalResponseGetCategory(Long beerId, String type,
		PairingCategory category, Pageable pageable) {
		var list = getCategoryOrderByPageable(beerId, type, category, pageable);
		var total = getCategorySize(beerId, category);

		return PageableExecutionUtils.getPage(list, pageable, () -> total);
	}

	/* 로그인 한 유저 + 카테고리 전체 조회 */
	@Override
	public Page<PairingResponseDto.Total> findPairingTotalResponseGetALL(Long beerId, String type, Long userId,
		Pageable pageable) {
		List<PairingResponseDto.Total> list;

		/* 작성한 글이 있는 경우 */
		if (isUserWritePairing(beerId, userId)) {
			list = getAllUserPairingFirst(beerId, type, userId, pageable);
		} else { /* 작성한 글이 없는 경우*/
			list = getAllOrderByPageable(beerId, type, pageable);
		}

		var total = getAllSize(beerId);

		return PageableExecutionUtils.getPage(list, pageable, () -> total);
	}

	/* 로그인 한 유저 + 카테고리 별 조회 */
	@Override
	public Page<PairingResponseDto.Total> findPairingTotalResponseGetCategory(Long beerId, String type, Long userId,
		PairingCategory category, Pageable pageable) {
		log.info("************레포지토리 입장 두둔!*****************");
		List<PairingResponseDto.Total> list;

		/* 작성한 글이 있는 경우 */
		if (isUserWritePairing(beerId, userId, category)) { /* 작성한 글이 없는 경우*/
			log.info("************작성글 존재 두둔*****************");
			list = getCategoryUserPairingFirst(beerId, type, userId, category, pageable);
		} else {
			log.info("************작성글 존재 안해 두둔*****************");
			list = getCategoryOrderByPageable(beerId, type, category, pageable);
		}

		var total = getCategorySize(beerId, category);

		log.info("************레포지토리 퇴장 두둔!*****************");

		return PageableExecutionUtils.getPage(list, pageable, () -> total);
	}

	// -------------------------------------------- 조회 관련 메서드 ---------------------------------------------------
	/* 유저가 글을 작성하였는지 확인 */
	private boolean isUserWritePairing(Long beerId, Long userId) {
		var userList = queryFactory
			.selectFrom(pairing).where(pairing.beer.id.eq(beerId).and(pairing.user.id.eq(userId)))
			.fetch();

		return userList.size() != 0;
	}

	private boolean isUserWritePairing(Long beerId, Long userId, PairingCategory pairingCategory) {
		var userList = queryFactory
			.selectFrom(pairing).where(
				pairing.beer.id.eq(beerId)
					.and(pairing.user.id.eq(userId)
						.and(pairing.pairingCategory.eq(pairingCategory)))
			).fetch();

		return userList.size() != 0;
	}

	/* 로그인을 하지 않았거나 해당 유저가 글을 작성하지 않은 경우 */
	private List<PairingResponseDto.Total> getAllOrderByPageable(Long beerId, String type, Pageable pageable) {

		return queryFactory
			.select(Projections.fields(PairingResponseDto.Total.class,
				pairing.beer.id.as("beerId"),
				pairing.beer.beerDetailsBasic.korName.as("korName"),
				pairing.id.as("pairingId"),
				pairing.user.id.as("userId"),
				pairing.user.nickname.as("nickname"),
				pairing.user.imageUrl.as("userImage"),
				pairing.content,
				pairing.thumbnail,
				pairing.likeCount,
				pairing.commentCount,
				pairing.createdAt,
				pairing.modifiedAt
			)).from(pairing)
			.where(pairing.beer.id.eq(beerId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(createOrderSpecifier(type).toArray(OrderSpecifier[]::new))
			.fetch();
	}

	/* 로그인을 하지 않았거나 해당 유저가 글을 작성하지 않은 경우 */
	private List<PairingResponseDto.Total> getCategoryOrderByPageable(Long beerId,
		String type, PairingCategory category, Pageable pageable) {

		return queryFactory
			.select(Projections.fields(PairingResponseDto.Total.class,
				pairing.beer.id.as("beerId"),
				pairing.beer.beerDetailsBasic.korName.as("korName"),
				pairing.id.as("pairingId"),
				pairing.user.id.as("userId"),
				pairing.user.nickname.as("nickname"),
				pairing.user.imageUrl.as("userImage"),
				pairing.content,
				pairing.thumbnail,
				pairing.likeCount,
				pairing.commentCount,
				pairing.createdAt,
				pairing.modifiedAt
			)).from(pairing)
			.where(pairing.beer.id.eq(beerId).and(pairing.pairingCategory.eq(category)))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(createOrderSpecifier(type).toArray(OrderSpecifier[]::new))
			.fetch();
	}

	/* 유저가 글을 작성하였다면, 본인의 글 중 가장 추천이 높은 것 1개를 가장 앞에 위치 */
	private List<PairingResponseDto.Total> getAllUserPairingFirst(Long beerId, String type,
		Long userId, Pageable pageable) {

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
				pairing.beer.beerDetailsBasic.korName.as("korName"),
				pairing.id.as("pairingId"),
				pairing.user.id.as("userId"),
				pairing.user.nickname.as("nickname"),
				pairing.user.imageUrl.as("userImage"),
				pairing.content,
				pairing.thumbnail,
				pairing.likeCount,
				pairing.commentCount,
				pairing.createdAt,
				pairing.modifiedAt
			)).from(pairing)
			.where(pairing.beer.id.eq(beerId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(sorting.asc())
			.orderBy(createOrderSpecifier(type).toArray(OrderSpecifier[]::new))
			.fetch();
	}

	/* 유저가 글을 작성하였다면, 본인의 글 중 가장 추천이 높은 것 1개를 가장 앞에 위치 +  */
	private List<PairingResponseDto.Total> getCategoryUserPairingFirst(Long beerId, String type,
		Long userId, PairingCategory category, Pageable pageable) {

		var first = queryFactory.selectFrom(pairing)
			.where(
				pairing.beer.id.eq(beerId)
				.and(pairing.pairingCategory.eq(category)
					.and(pairing.user.id.eq(userId)))
			)
			.orderBy(pairing.likeCount.desc(), pairing.id.desc())
			.fetchFirst();

		var sorting = new CaseBuilder()
			.when(pairing.user.id.eq(userId).and(pairing.id.eq(first.getId()))).then(1)
			.otherwise(2);

		return queryFactory
			.select(Projections.fields(PairingResponseDto.Total.class,
				pairing.beer.id.as("beerId"),
				pairing.beer.beerDetailsBasic.korName.as("korName"),
				pairing.id.as("pairingId"),
				pairing.user.id.as("userId"),
				pairing.user.nickname.as("nickname"),
				pairing.user.imageUrl.as("userImage"),
				pairing.content,
				pairing.thumbnail,
				pairing.likeCount,
				pairing.commentCount,
				pairing.createdAt,
				pairing.modifiedAt
			)).from(pairing)
			.where(pairing.beer.id.eq(beerId).and(pairing.pairingCategory.eq(category)))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(sorting.asc())
			.orderBy(createOrderSpecifier(type).toArray(OrderSpecifier[]::new))
			.fetch();
	}

	// ---------------------------------------------------------------------------------------------------------------
	private long getAllSize(Long beerId) {

		return queryFactory.selectFrom(pairing)
			.where(pairing.beer.id.eq(beerId))
			.fetch().size();
	}
	private long getCategorySize(Long beerId, PairingCategory category) {

		return queryFactory.selectFrom(pairing)
			.where(pairing.beer.id.eq(beerId).and(pairing.pairingCategory.eq(category)))
			.fetch().size();
	}

	private List<OrderSpecifier> createOrderSpecifier(String type) {
		List<OrderSpecifier> list = new ArrayList<>();
		switch (type) {
			case "recency" : list.add(new OrderSpecifier<>(Order.DESC, pairing.id));
			case "mostlikes" : list.add(new OrderSpecifier<>(Order.DESC, pairing.likeCount));
			case "mostcomments" : list.add(new OrderSpecifier<>(Order.DESC, pairing.commentCount));
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
