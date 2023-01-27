package be.domain.beer.repository;

import be.domain.beer.entity.Beer;
import static be.domain.beer.entity.QBeer.beer;
import static be.domain.beer.entity.QBeerBeerCategory.beerBeerCategory;
import be.domain.beercategory.entity.BeerCategory;
import static be.domain.beercategory.entity.QBeerCategory.beerCategory;
import static be.domain.comment.entity.QBeerComment.beerComment;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BeerQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Beer> findMonthlyBeer() {
        return jpaQueryFactory.selectFrom(beer)
                .join(beer.beerCommentList, beerComment)
                .where(beerComment.createdAt.month().eq(LocalDateTime.now().getMonthValue() - 1))
                .orderBy(beer.beerDetailsRatings.totalAverageRating.desc())
                .limit(5)
                .fetch();
    }

//    public List<BeerCategory> findTop4BeerCategory(Beer findBeer) {
//
//        NumberPath<Long> quantity = Expressions.numberPath(Long.class, "quantity");
//
//        return jpaQueryFactory
//                .from(beerBeerCategory)
//                .join(beerBeerCategory.beerCategory, beerCategory)
//                .where(beerBeerCategory.beer.id.eq(findBeer.getId()))
//                .select(Projections.constructor(BeerCategory.class, beerCategory.id,
//                        beerCategory.beerCategoryType, beerCategory.beerCategoryType.count()))
//                .orderBy(quantity.desc())
//                .limit(4)
//                .fetch();
//
//    }
}
