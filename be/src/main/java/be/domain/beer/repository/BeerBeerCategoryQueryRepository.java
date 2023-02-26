package be.domain.beer.repository;

import static be.domain.beer.entity.QBeerBeerCategory.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.beer.entity.BeerBeerCategory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BeerBeerCategoryQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public void delete(BeerBeerCategory category) {

		jpaQueryFactory.delete(beerBeerCategory)
			.where(beerBeerCategory.eq(category))
			.execute();
	}

	public void deleteList(List<BeerBeerCategory> beerBeerCategories) {

		beerBeerCategories.forEach(category -> {
			jpaQueryFactory.delete(beerBeerCategory)
				.where(beerBeerCategory.eq(category))
				.execute();
		});
	}

	public void deleteAllByBeerId(Long beerId) {

		jpaQueryFactory.delete(beerBeerCategory)
			.where(beerBeerCategory.beer.id.eq(beerId))
			.execute();
	}
}
