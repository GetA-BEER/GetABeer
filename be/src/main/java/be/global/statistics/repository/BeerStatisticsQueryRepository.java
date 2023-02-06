package be.global.statistics.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.beer.entity.Beer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BeerStatisticsQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

}
