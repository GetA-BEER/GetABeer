package be.domain.beer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BeerCategoryQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
