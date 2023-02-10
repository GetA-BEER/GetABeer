package be.global.statistics.repository;

import static be.domain.beercategory.entity.QBeerCategory.*;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.global.statistics.entity.BeerCategoryStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BeerCategoryStatisticsQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final BeerCategoryStatisticsRepository beerCategoryStatisticsRepository;

	public void createAndSaveBeerCategoryStatistics() {

		List<Integer> countList =
			jpaQueryFactory.select(beerCategory.statCount)
				.from(beerCategory)
				.orderBy(beerCategory.id.asc())
				.fetch();

		BeerCategoryStatistics.BeerCategoryStatisticsBuilder beerCategoryStatisticsBuilder = BeerCategoryStatistics.builder();

		beerCategoryStatisticsBuilder.week(LocalDate.now().minusWeeks(1).get(WeekFields.ISO.weekOfYear()));
		beerCategoryStatisticsBuilder.ale(countList.get(0));
		beerCategoryStatisticsBuilder.lager(countList.get(0));
		beerCategoryStatisticsBuilder.weizen(countList.get(0));
		beerCategoryStatisticsBuilder.dunkel(countList.get(0));
		beerCategoryStatisticsBuilder.pilsener(countList.get(0));
		beerCategoryStatisticsBuilder.fruitBeer(countList.get(0));
		beerCategoryStatisticsBuilder.nonAlcoholic(countList.get(0));

		beerCategoryStatisticsRepository.save(beerCategoryStatisticsBuilder.build());

		jpaQueryFactory.update(beerCategory)
			.set(beerCategory.statCount, 0)
			.execute();
	}
}
