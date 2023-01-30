package be.domain.beertag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BeerTagQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

}
