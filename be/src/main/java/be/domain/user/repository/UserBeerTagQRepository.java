package be.domain.user.repository;

import static be.domain.beertag.entity.QBeerTag.*;
import static be.domain.user.entity.QUser.*;
import static be.domain.user.entity.QUserBeerTag.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.user.entity.UserBeerTag;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserBeerTagQRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public List<UserBeerTag> findUserBeerTagByUserId(Long userId) {

		return jpaQueryFactory.selectFrom(userBeerTag)
			.join(userBeerTag.user, user)
			.join(userBeerTag.beerTag, beerTag)
			.where(userBeerTag.user.id.eq(userId))
			.fetch();
	}

	public void delete(Long userId) {

		jpaQueryFactory.delete(userBeerTag)
			.where(userBeerTag.user.id.eq(userId))
			.execute();
	}
}
