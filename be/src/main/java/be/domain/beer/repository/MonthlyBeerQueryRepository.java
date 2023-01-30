package be.domain.beer.repository;

import be.domain.beer.entity.MonthlyBeer;
import static be.domain.beer.entity.QMonthlyBeer.monthlyBeer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MonthlyBeerQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<List<MonthlyBeer>> findMonthlyBeer() {

        return Optional.ofNullable(jpaQueryFactory.selectFrom(monthlyBeer)
                .where(monthlyBeer.createdAt.month().eq(LocalDateTime.now().getMonthValue()))
                .orderBy(monthlyBeer.averageStar.desc())
                .fetch());
    }
}
