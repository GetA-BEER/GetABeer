package be.global.chat.redis.repository;

import static be.global.chat.redis.entity.QRedisChatMessage.*;
import static be.global.chat.redis.entity.QRedisChatRoom.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import be.global.chat.redis.entity.RedisChatMessage;
import be.global.chat.redis.entity.RedisChatRoom;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisChatRepository {
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public void save(RedisChatMessage message) {
		em.persist(message);
	}

	public List<RedisChatMessage> findAllChatInRoom(Long roomId) {

		return queryFactory
			.selectFrom(redisChatMessage)
			.where(redisChatMessage.chatRoom.id.eq(roomId))
			.fetch();
	}

	public Optional<RedisChatRoom> getOrCreateRoom(Long clientId) {

		return Optional.ofNullable(queryFactory
			.selectFrom(redisChatRoom)
			.where(redisChatRoom.sender.id.eq(clientId).or(redisChatRoom.receiver.id.eq(clientId)))
			.fetchFirst());
	}
}
