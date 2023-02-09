package be.domain.user.repository;

import static be.domain.beercategory.entity.QBeerCategory.*;
import static be.domain.user.entity.QUser.*;
import static be.domain.user.entity.QUserBeerCategory.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.user.entity.UserBeerCategory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserBeerCategoryQRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public List<UserBeerCategory> findUserBeerCategoryByUserId(Long userId) {

		return jpaQueryFactory.selectFrom(userBeerCategory)
			.join(userBeerCategory.user, user)
			.join(userBeerCategory.beerCategory, beerCategory)
			.where(userBeerCategory.user.id.eq(userId))
			.fetch();
	}

	public void delete(Long userId) {

		jpaQueryFactory.delete(userBeerCategory)
			.where(userBeerCategory.user.id.eq(userId))
			.execute();
	}

}
