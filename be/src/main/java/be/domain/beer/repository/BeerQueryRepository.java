package be.domain.beer.repository;

import static be.domain.beer.entity.QBeer.*;
import static be.domain.beer.entity.QBeerBeerTag.*;
import static be.domain.beertag.entity.QBeerTag.*;
import static be.domain.rating.entity.QRating.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.beer.entity.Beer;
import be.domain.beertag.entity.BeerTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BeerQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public List<Beer> findMonthlyBeer() {

		return jpaQueryFactory.selectFrom(beer)
			.join(beer.ratingList, rating)
			.where(rating.createdAt.month().eq(LocalDateTime.now().getMonthValue() - 1))
			.orderBy(beer.beerDetailsStars.totalAverageStars.desc())
			.limit(5)
			.fetch();
	}

	public List<BeerTag> findTop4BeerTag(Beer findBeer) {

		return jpaQueryFactory
			.selectFrom(beerTag)
			.join(beerTag.beerBeerTags, beerBeerTag)
			.where(beerBeerTag.beer.eq(findBeer))
			.groupBy(beerTag)
			.orderBy(beerTag.count().desc())
			.limit(4)
			.fetch();
	}

	public Beer findBeerByRatingId(Long ratingId) {

		return jpaQueryFactory
			.selectFrom(beer)
			.join(beer.ratingList, rating)
			.where(rating.id.eq(ratingId))
			.fetchFirst();
	}

	public Page<Beer> findBeersPageByQueryParam(String queryParam, Pageable pageable) {

		List<Beer> resultList = new ArrayList<>();

		String[] queryParamArr = queryParam.split(" ");
		StringPath korName = beer.beerDetailsBasic.korName;
		StringPath engName = beer.beerDetailsBasic.engName;

		log.info("####: " + queryParam);

		List<Beer> fullTextResultList = jpaQueryFactory.selectFrom(beer)
			.where(korName.containsIgnoreCase(queryParam)
				.or(engName.containsIgnoreCase(queryParam)))
			.fetch();

		log.info("#####: " + fullTextResultList);

		for (String query : queryParamArr) {

			resultList.addAll(jpaQueryFactory
				.selectFrom(beer)
				.where(korName.containsIgnoreCase(query).or(engName.containsIgnoreCase(query)))
				.fetch());
		}

		resultList = resultList.stream()
			.sorted((a, b) -> (int)((b.getBeerDetailsStars().getTotalAverageStars() * 100)
				- (a.getBeerDetailsStars().getTotalAverageStars() * 100)))
			.collect(Collectors.toList());

		resultList.addAll(0, fullTextResultList);

		int total = resultList.size();
		int start = (int)pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), total);

		return new PageImpl<>(resultList.subList(start, end), pageable, total);
	}
}
