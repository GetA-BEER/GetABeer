package be.domain.beertag.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BeerTagQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

}
