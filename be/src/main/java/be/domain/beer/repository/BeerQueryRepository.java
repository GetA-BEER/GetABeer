package be.domain.beer.repository;

import static be.domain.beer.entity.QBeer.*;
import static be.domain.beer.entity.QBeerBeerCategory.*;
import static be.domain.beer.entity.QBeerBeerTag.*;
import static be.domain.beercategory.entity.QBeerCategory.*;
import static be.domain.beertag.entity.QBeerTag.*;
import static be.domain.beerwishlist.entity.QBeerWishlist.*;
import static be.domain.rating.entity.QRating.*;
import static be.domain.user.entity.QUser.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
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
import be.domain.beercategory.entity.BeerCategory;
import be.domain.beertag.entity.BeerTag;
import be.domain.rating.entity.Rating;
import be.domain.user.entity.User;
import be.domain.user.entity.UserBeerCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BeerQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public List<Beer> findMonthlyBeer() {

		return jpaQueryFactory
			.selectFrom(beer)
			.join(beer.ratingList, rating)
			.where(rating.createdAt.month().eq(LocalDateTime.now().getMonthValue() - 1))
			.orderBy(rating.star.avg().desc())
			// .orderBy(beer.beerDetailsStars.totalAverageStars.desc())
			.limit(5)
			.fetch();
	}

	public List<Beer> findWeeklyBeer() {

		return jpaQueryFactory
			.selectFrom(beer)
			.join(beer.ratingList, rating)
			.where(rating.createdAt
				.between(LocalDate.now().minusDays(7).atStartOfDay(), LocalDate.now().atStartOfDay()))
			.orderBy(rating.star.avg().desc())
			// .orderBy(beer.beerDetailsStars.totalAverageStars.desc())
			.limit(5)
			.fetch();
	}

	public List<Beer> findRecommendBeer(User findUser) {

		List<BeerCategory> beerCategoryList =
			findUser.getUserBeerCategories().stream()
				.map(UserBeerCategory::getBeerCategory)
				.collect(Collectors.toList());

		List<Beer> result = new ArrayList<>();

		for (BeerCategory category : beerCategoryList) {

			List<Beer> beerList = jpaQueryFactory.selectFrom(beer)
				.join(beer.beerBeerCategories, beerBeerCategory)
				.join(beerBeerCategory.beerCategory, beerCategory)
				.where(beerCategory.eq(category))
				.fetch();

			result.addAll(beerList);
		}

		return result.stream()
			.distinct()
			.sorted((a, b) -> (int)((b.getBeerDetailsStars().getTotalAverageStars() * 100)
				- (a.getBeerDetailsStars().getTotalAverageStars() * 100)))
			.limit(5)
			.collect(Collectors.toList());
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

	public List<Beer> findSimilarBeer(Beer findBeer) {

		List<String> beerCategories = findBeer.getBeerBeerCategories().stream()
			.map(beerBeerCategory1 -> beerBeerCategory1.getBeerCategory().toString())
			.collect(Collectors.toList());

		List<Beer> result = new ArrayList<>();

		for (String category : beerCategories) {

			List<Beer> beerList = jpaQueryFactory
				.selectFrom(beer)
				.join(beer.beerBeerCategories, beerBeerCategory)
				.join(beerBeerCategory.beerCategory, beerCategory)
				.where(beerCategory.beerCategoryType.stringValue().eq(category)
					.and(beer.beerDetailsTopTags.tag1.eq(beerCategories.get(0))
						.or(beer.beerDetailsTopTags.tag1.eq(beerCategories.get(1))))
					.or(beer.beerDetailsTopTags.tag2.eq(beerCategories.get(0))
						.or(beer.beerDetailsTopTags.tag2.eq(beerCategories.get(1)))))
				.fetch();

			result.addAll(beerList);
		}

		return result.stream()
			.distinct()
			.sorted((a, b) -> (int)((b.getBeerDetailsStars().getTotalAverageStars() * 100)
				- (a.getBeerDetailsStars().getTotalAverageStars() * 100)))
			.limit(5)
			.collect(Collectors.toList());
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

	public Page<Beer> findCategoryBeers(String queryParam, Pageable pageable) {

		List<Beer> beerList = jpaQueryFactory.selectFrom(beer)
			.join(beer.beerBeerCategories, beerBeerCategory)
			.join(beerBeerCategory.beerCategory, beerCategory)
			.where(beerCategory.beerCategoryType.stringValue().eq(queryParam))
			.orderBy(beer.beerDetailsStars.totalAverageStars.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return new PageImpl<>(beerList, pageable, beerList.size());
	}

	public Rating findBestRating(Beer findBeer) {

		return jpaQueryFactory.selectFrom(rating)
			.where(rating.beer.eq(findBeer))
			.fetchFirst();
	}

	public Page<Beer> findMyPageBeers(User loginUser, Pageable pageable) {

		List<Beer> beerList = jpaQueryFactory.select(beer)
			.join(beer.beerWishlists, beerWishlist)
			.join(beerWishlist.user, user)
			.from(beer)
			.where(beerWishlist.user.eq(loginUser))
			.orderBy(beerWishlist.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return new PageImpl<>(beerList, pageable, beerList.size());
	}

	public List<Rating> findMyRatingWithWishlist(User loginUser) {

		return jpaQueryFactory.select(rating)
			.join(beer.beerWishlists, beerWishlist)
			.join(beer.ratingList, rating)
			.join(beerWishlist.user, user)
			.from(rating)
			.where(beerWishlist.user.eq(loginUser))
			.fetch();
	}
}
