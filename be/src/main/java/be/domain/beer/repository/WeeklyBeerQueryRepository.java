package be.domain.beer.repository;

import static be.domain.beer.entity.QWeeklyBeer.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.beer.entity.WeeklyBeer;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WeeklyBeerQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public List<WeeklyBeer> findWeeklyBeer() {

		return jpaQueryFactory.selectFrom(weeklyBeer)
			.where(weeklyBeer.createdAt.month().eq(LocalDateTime.now().getMonthValue()))
			.orderBy(weeklyBeer.createdAt.desc(), weeklyBeer.averageStar.desc())
			.limit(5)
			.fetch();
	}
}
