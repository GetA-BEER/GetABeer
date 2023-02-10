package be.global.statistics.repository;

import static be.domain.beer.entity.QBeer.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.global.statistics.entity.BeerStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BeerStatisticsQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final BeerStatisticsJdbcRepository beerStatisticsJdbcRepository;

	public void createAndSaveBeerStatistics() {

		List<BeerStatistics> list = new ArrayList<>();

		jpaQueryFactory.selectFrom(beer)
			.where(beer.beerDetailsStatistics.statViewCount.goe(1))
			.orderBy(beer.beerDetailsStatistics.statViewCount.desc())
			.fetch()
			.forEach(findBeer -> {
				BeerStatistics beerStatistics = BeerStatistics.builder().build();
				beerStatistics.create(findBeer);

				list.add(beerStatistics);

				findBeer.getBeerDetailsStatistics().resetStatistic(); // 통계 초기화
			});

		beerStatisticsJdbcRepository.saveAll(list);
	}
}
