package be.domain.beer.repository;

import static be.domain.beer.entity.QMonthlyBeer.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.beer.entity.MonthlyBeer;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MonthlyBeerQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public List<MonthlyBeer> findMonthlyBeer() {

		return jpaQueryFactory.selectFrom(monthlyBeer)
			.where(monthlyBeer.createdAt.month().eq(LocalDateTime.now().getMonthValue()))
			.orderBy(monthlyBeer.averageStar.desc())
			.fetch();
	}
}
