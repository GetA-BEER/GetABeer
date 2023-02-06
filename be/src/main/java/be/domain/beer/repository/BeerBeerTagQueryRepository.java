package be.domain.beer.repository;

import static be.domain.beer.entity.QBeer.*;
import static be.domain.beer.entity.QBeerBeerTag.*;
import static be.domain.beertag.entity.QBeerTag.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.beer.entity.Beer;
import be.domain.beer.entity.BeerBeerTag;
import be.domain.beertag.entity.BeerTagType;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BeerBeerTagQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public BeerBeerTag findBeerBeerTagByBeerAndBeerTagType(Beer findbeer, BeerTagType beerTagType) {

		return jpaQueryFactory.selectFrom(beerBeerTag)
			.join(beerBeerTag.beer, beer)
			.join(beerBeerTag.beerTag, beerTag)
			.where(beerBeerTag.beer.eq(findbeer)
				.and(beerBeerTag.beerTag.beerTagType.eq(beerTagType)))
			.fetchFirst();
	}
}
