package be.domain.beercategory.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BeerCategoryQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	//    public Optional<BeerCategory> finBeerCategoryByBeerCategoryType(BeerCategoryType beerCategoryType) {
	//
	//    }
}
