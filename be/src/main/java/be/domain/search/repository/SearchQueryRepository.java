package be.domain.search.repository;

import static be.domain.beer.entity.QBeer.*;
import static be.domain.beer.entity.QBeerBeerCategory.*;
import static be.domain.beercategory.entity.QBeerCategory.*;
import static be.domain.user.entity.QUser.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.beer.entity.Beer;
import be.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SearchQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public Page<Beer> findBeersPageByQueryParam(String queryParam, Pageable pageable) {

		List<Beer> resultList = new ArrayList<>();

		String[] queryParamArr = queryParam.split(" ");
		StringPath korName = beer.beerDetailsBasic.korName;
		StringPath engName = beer.beerDetailsBasic.engName;

		log.info("####: " + queryParam);

		List<Beer> fullTextResultList = jpaQueryFactory
			.selectFrom(beer)
			.where(korName.likeIgnoreCase(queryParam)
				.or(engName.likeIgnoreCase(queryParam)))
			.orderBy(beer.beerDetailsBasic.korName.asc(), beer.beerDetailsBasic.engName.asc())
			.fetch();

		List<Beer> fullTextContainsResultList = jpaQueryFactory
			.selectFrom(beer)
			.where(korName.containsIgnoreCase(queryParam)
				.or(engName.containsIgnoreCase(queryParam)))
			.orderBy(beer.beerDetailsBasic.korName.asc(), beer.beerDetailsBasic.engName.asc())
			.fetch();

		log.info("#####: " + fullTextResultList);

		fullTextResultList.addAll(fullTextContainsResultList);

		for (String query : queryParamArr) {

			resultList.addAll(jpaQueryFactory
				.selectFrom(beer)
				.where(korName.containsIgnoreCase(query).or(engName.containsIgnoreCase(query)))
				.orderBy(beer.beerDetailsBasic.korName.asc(), beer.beerDetailsBasic.engName.asc())
				.fetch());
		}

		resultList = resultList.stream()
			.sorted((a, b) -> (int)((b.getBeerDetailsStars().getTotalAverageStars() * 100)
				- (a.getBeerDetailsStars().getTotalAverageStars() * 100)))
			.collect(Collectors.toList());

		resultList.addAll(0, fullTextResultList);

		List<Beer> result = resultList.stream().distinct().collect(Collectors.toList());

		int total = result.size();
		int start = (int)pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), total);

		return new PageImpl<>(result.subList(start, end), pageable, total);
	}

	public Page<Beer> findBeersPageByBeerCategoryQueryParam(String queryParam, Pageable pageable) {

		queryParam = queryParam.substring(1);

		List<Beer> beerList = jpaQueryFactory
			.selectFrom(beer)
			.join(beer.beerBeerCategories, beerBeerCategory)
			.join(beerBeerCategory.beerCategory, beerCategory)
			.where(beerCategory.beerCategoryType.stringValue().eq(queryParam))
			.orderBy(beer.beerDetailsStars.totalAverageStars.desc(), beer.beerDetailsBasic.korName.asc(),
				beer.beerDetailsBasic.engName.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = jpaQueryFactory
			.select(beer.count())
			.from(beer)
			.join(beer.beerBeerCategories, beerBeerCategory)
			.join(beerBeerCategory.beerCategory, beerCategory)
			.where(beerCategory.beerCategoryType.stringValue().eq(queryParam))
			.fetchOne();

		return new PageImpl<>(beerList, pageable, total);
	}

	public Page<Beer> findBeersPageByBeerTagQueryParam(String queryParam, Pageable pageable) {

		queryParam = queryParam.substring(1);

		List<Beer> beerList = jpaQueryFactory
			.selectFrom(beer)
			.where(beer.beerDetailsTopTags.tag1.eq(queryParam)
				.or(beer.beerDetailsTopTags.tag2.eq(queryParam)))
			.orderBy(beer.beerDetailsStars.totalAverageStars.desc(), beer.beerDetailsBasic.korName.asc(),
				beer.beerDetailsBasic.engName.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = jpaQueryFactory
			.select(beer.count())
			.from(beer)
			.where(beer.beerDetailsTopTags.tag1.eq(queryParam)
				.or(beer.beerDetailsTopTags.tag2.eq(queryParam)))
			.fetchOne();

		return new PageImpl<>(beerList, pageable, total);
	}

	public Page<Beer> findBeersPageByPairingCategoryQueryParam(String queryParam, Pageable pageable) {

		queryParam = queryParam.substring(1);

		List<Beer> beerList = jpaQueryFactory
			.selectFrom(beer)
			.where(beer.beerDetailsStatistics.bestPairingCategory.eq(queryParam))
			.orderBy(beer.beerDetailsStars.totalAverageStars.desc(), beer.beerDetailsBasic.korName.asc(),
				beer.beerDetailsBasic.engName.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = jpaQueryFactory
			.select(beer.count())
			.from(beer)
			.where(beer.beerDetailsStatistics.bestPairingCategory.eq(queryParam))
			.fetchOne();

		return new PageImpl<>(beerList, pageable, total);
	}

	public Page<User> findUsersPageByQueryParam(User loginUser, String queryParam, Pageable pageable) {

		queryParam = queryParam.substring(1);

		User findUser = jpaQueryFactory
			.selectFrom(user)
			.where(user.nickname.like(queryParam))
			.fetchOne();

		List<User> userList = jpaQueryFactory
			.selectFrom(user)
			.where(user.nickname.contains(queryParam))
			.orderBy(user.followerCount.desc(), user.nickname.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = jpaQueryFactory
			.select(user.count())
			.from(user)
			.where(user.nickname.contains(queryParam))
			.fetchOne();

		if (findUser != null) {
			userList.add(0, findUser);
			List<User> result = userList.stream().distinct().collect(Collectors.toList());
			return new PageImpl<>(result, pageable, total + 1);
		} else {
			return new PageImpl<>(userList, pageable, total);
		}

	}
}
