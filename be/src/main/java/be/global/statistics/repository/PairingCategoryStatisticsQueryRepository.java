package be.global.statistics.repository;

import static be.domain.pairing.entity.QPairing.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.pairing.entity.PairingCategory;
import be.global.statistics.entity.PairingCategoryStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PairingCategoryStatisticsQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final PairingCategoryStatisticsRepository pairingCategoryStatisticsRepository;

	public void createAndSaveBeerTagStatistics() {

		List<Integer> countList = new ArrayList<>();

		for (int i = 0; i < 8; i++) {
			countList.add(jpaQueryFactory.select(pairing.pairingCategory)
				.where(pairing.createdAt.between(LocalDateTime.now().minusWeeks(1L), LocalDateTime.now()))
				.where(pairing.pairingCategory.eq(PairingCategory.values()[i]))
				.fetch()
				.size());
		}

		PairingCategoryStatistics.PairingCategoryStatisticsBuilder pairingCategoryStatisticsBuilder = PairingCategoryStatistics.builder();

		pairingCategoryStatisticsBuilder.week(LocalDate.now().minusWeeks(1).get(WeekFields.ISO.weekOfYear()));
		pairingCategoryStatisticsBuilder.fried(countList.get(0));
		pairingCategoryStatisticsBuilder.grill(countList.get(1));
		pairingCategoryStatisticsBuilder.stir(countList.get(2));
		pairingCategoryStatisticsBuilder.fresh(countList.get(3));
		pairingCategoryStatisticsBuilder.dry(countList.get(4));
		pairingCategoryStatisticsBuilder.snack(countList.get(5));
		pairingCategoryStatisticsBuilder.soup(countList.get(6));
		pairingCategoryStatisticsBuilder.etc(countList.get(7));

		pairingCategoryStatisticsRepository.save(pairingCategoryStatisticsBuilder.build());
	}
}
