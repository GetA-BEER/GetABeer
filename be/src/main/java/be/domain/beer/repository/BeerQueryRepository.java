package be.domain.beer.repository;

import be.domain.beer.entity.Beer;
import static be.domain.beer.entity.QBeer.beer;
import static be.domain.beer.entity.QBeerBeerCategory.beerBeerCategory;
import static be.domain.beer.entity.QBeerBeerTag.beerBeerTag;
import static be.domain.beercategory.entity.QBeerCategory.beerCategory;
import be.domain.beertag.entity.BeerTag;
import static be.domain.beertag.entity.QBeerTag.beerTag;
import static be.domain.comment.entity.QBeerComment.beerComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
                .orderBy(beer.beerDetailsStars.totalAverageStars.desc())
                .limit(5)
                .fetch();
    }

    public List<BeerTag> findTop4BeerTag(Beer findBeer) {

        return jpaQueryFactory
                .selectFrom(beerTag)
                .join(beerTag.beerBeerTags, beerBeerTag)
                .where(beerBeerTag.beer.eq(findBeer))
                .groupBy(beerTag)
                .orderBy(beerTag.count().desc())
                .limit(4)
                .fetch();

    }
}
