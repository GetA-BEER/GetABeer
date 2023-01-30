package be.domain.beer.repository;

import be.domain.beer.entity.BeerBeerCategory;
import static be.domain.beer.entity.QBeerBeerCategory.beerBeerCategory;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BeerBeerCategoryQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public void delete(BeerBeerCategory category) {

            jpaQueryFactory.delete(beerBeerCategory)
                    .where(beerBeerCategory.eq(category))
                    .execute();
    }

    public void deleteList(List<BeerBeerCategory> beerBeerCategories) {

        beerBeerCategories.forEach(category -> {
            jpaQueryFactory.delete(beerBeerCategory)
                    .where(beerBeerCategory.eq(category))
                    .execute();
        });
    }

    public void deleteAllByBeerId(Long beerId) {

        jpaQueryFactory.delete(beerBeerCategory)
                .where(beerBeerCategory.beer.id.eq(beerId))
                .execute();
    }
}
