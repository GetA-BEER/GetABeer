package be.global.statistics.repository;

import static be.global.statistics.entity.QTotalStatistics.*;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.global.statistics.entity.TotalStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TotalStatisticsQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public TotalStatistics findTotalStatistics() {
		return jpaQueryFactory.selectFrom(totalStatistics)
			.where(totalStatistics.date.eq(LocalDate.now()))
			.fetchOne();
	}
}
