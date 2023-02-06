package be.global.statistics.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BeerCategoryTypeStatisticsQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
}
