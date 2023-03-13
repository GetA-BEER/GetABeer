package be.domain.notice.repository;

import static be.domain.notice.entity.QNotification.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NotificationQRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public void deleteAllNotification(Long userId) {
		jpaQueryFactory.delete(notification)
			.where(notification.user.id.eq(userId))
			.execute();
	}
}
