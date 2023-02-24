package be.domain.beerwishlist.repository;

import static be.domain.beer.entity.QBeer.*;
import static be.domain.beerwishlist.entity.QBeerWishlist.*;
import static be.domain.user.entity.QUser.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.beerwishlist.entity.BeerWishlist;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BeerWishListQRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public List<BeerWishlist> findByUserAndTrue(Long userId) {

		return jpaQueryFactory.selectFrom(beerWishlist)
			.join(beerWishlist.beer, beer)
			.join(beerWishlist.user, user)
			.where(beerWishlist.user.id.eq(userId))
			.where(beerWishlist.wished.eq(true))
			.orderBy(beerWishlist.modifiedAt.desc())
			.fetch();
	}

	public void deleteByBeer(Long beerId) {

		jpaQueryFactory.delete(beerWishlist)
			.where(beerWishlist.beer.id.eq(beerId));
	}
}
