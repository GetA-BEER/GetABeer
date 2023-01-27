package be.domain.beercategory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BeerCategoryQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

//    public Optional<BeerCategory> finBeerCategoryByBeerCategoryType(BeerCategoryType beerCategoryType) {
//
//    }
}
