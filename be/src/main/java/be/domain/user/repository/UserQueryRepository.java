package be.domain.user.repository;

import static be.domain.comment.entity.QPairingComment.*;
import static be.domain.comment.entity.QRatingComment.*;
import static be.domain.follow.entity.QFollow.*;
import static be.domain.pairing.entity.QPairing.*;
import static be.domain.rating.entity.QRating.*;
import static be.domain.user.entity.QUser.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public UserDto.UserPageResponse findUserPage(Long userId) {

		User findUser = jpaQueryFactory.selectFrom(user)
			.where(user.id.eq(userId))
			.fetchFirst();

		Long ratingCount = jpaQueryFactory.select(rating.count())
			.from(user)
			.join(user.ratingList, rating)
			.where(rating.user.id.eq(userId))
			.fetchOne();

		Long pairingCount = jpaQueryFactory.select(pairing.count())
			.from(user)
			.join(user.pairingList, pairing)
			.where(pairing.user.id.eq(userId))
			.fetchOne();

		Long pairingCommentCount = jpaQueryFactory.select(pairingComment.count())
			.from(pairingComment)
			.where(pairingComment.user.id.eq(userId))
			.fetchOne();

		Long ratingCommentCount = jpaQueryFactory.select(ratingComment.count())
			.from(ratingComment)
			.where(ratingComment.user.id.eq(userId))
			.fetchOne();

		Long followerCount = jpaQueryFactory.select(follow.count())
			.from(follow)
			.where(follow.followedUser.id.eq(userId))
			.fetchOne();

		Long followingCount = jpaQueryFactory.select(follow.count())
			.from(follow)
			.where(follow.followingUser.id.eq(userId))
			.fetchOne();

		return UserDto.UserPageResponse.builder()
			.id(findUser.getId())
			.imgUrl(findUser.getImageUrl())
			.nickname(findUser.getNickname())
			.ratingCount(ratingCount)
			.pairingCount(pairingCount)
			.commentCount(pairingCommentCount + ratingCommentCount)
			.followerCount(followerCount)
			.followingCount(followingCount)
			.build();
	}

	public List<User> findAdminUser() {

		return jpaQueryFactory
			.selectFrom(user)
			.where(user.roles.contains("ROLE_ADMIN"))
			.fetch();
	}
}
