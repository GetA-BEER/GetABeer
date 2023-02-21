package be.global.statistics.repository;

import static be.domain.beertag.entity.QBeerTag.*;
import static be.domain.rating.entity.QRating.*;
import static be.domain.user.entity.QUser.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.pairing.entity.PairingCategory;
import be.domain.user.entity.enums.Gender;
import be.global.statistics.entity.BeerTagStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BeerTagStatisticsQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final BeerTagStatisticsRepository beerTagStatisticsRepository;

	public void createAndSaveBeerTagStatistics() {

		List<Integer> countList =
			jpaQueryFactory.select(beerTag.statCount)
				.from(beerTag)
				.orderBy(beerTag.id.asc())
				.fetch();

		List<Integer> genderCountList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			genderCountList.add(jpaQueryFactory.select(rating)
				.join(rating.user, user)
				.where(rating.createdAt.eq(LocalDateTime.now().minusDays(1)))
				.where(rating.user.gender.eq(Gender.values()[i]))
				.fetch().size());
		}

		BeerTagStatistics.BeerTagStatisticsBuilder beerTagStatisticsBuilder = BeerTagStatistics.builder();

		beerTagStatisticsBuilder.date(LocalDate.now().minusDays(1));
		beerTagStatisticsBuilder.week(LocalDate.now().get(WeekFields.ISO.weekOfYear()));
		beerTagStatisticsBuilder.straw(countList.get(0));
		beerTagStatisticsBuilder.gold(countList.get(1));
		beerTagStatisticsBuilder.brown(countList.get(2));
		beerTagStatisticsBuilder.black(countList.get(3));
		beerTagStatisticsBuilder.sweet(countList.get(4));
		beerTagStatisticsBuilder.sour(countList.get(5));
		beerTagStatisticsBuilder.bitter(countList.get(6));
		beerTagStatisticsBuilder.rough(countList.get(7));
		beerTagStatisticsBuilder.fruity(countList.get(8));
		beerTagStatisticsBuilder.flower(countList.get(9));
		beerTagStatisticsBuilder.malty(countList.get(10));
		beerTagStatisticsBuilder.noScent(countList.get(11));
		beerTagStatisticsBuilder.weak(countList.get(12));
		beerTagStatisticsBuilder.middle(countList.get(13));
		beerTagStatisticsBuilder.strong(countList.get(14));
		beerTagStatisticsBuilder.noCarbonation(countList.get(15));
		beerTagStatisticsBuilder.male(genderCountList.get(0));
		beerTagStatisticsBuilder.female(genderCountList.get(1));
		beerTagStatisticsBuilder.refuse(genderCountList.get(2));

		beerTagStatisticsRepository.save(beerTagStatisticsBuilder.build());

		jpaQueryFactory.update(beerTag)
			.set(beerTag.statCount, 0)
			.execute();

	}
}
