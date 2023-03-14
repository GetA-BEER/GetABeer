package be.domain.chat.redis.repository;

import static be.domain.chat.redis.entity.QRedisChatMessage.*;
import static be.domain.chat.redis.entity.QRedisChatRoom.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.domain.chat.redis.dto.RedisRoomDto;
import be.domain.chat.redis.entity.RedisChatMessage;
import be.domain.chat.redis.entity.RedisChatRoom;
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

	public RedisRoomDto.Response getChatRoom(Long userId) {

		return queryFactory
			.select(Projections.fields(RedisRoomDto.Response.class,
				redisChatRoom.id.as("roomId"),
				redisChatRoom.sender.id.as("senderId"),
				redisChatRoom.isAdminRead.as("isAdminRead")
				)).from(redisChatRoom)
			.where(redisChatRoom.id.eq(userId))
			.fetchFirst();
	}

	public RedisChatRoom findChatRoom(Long roomId) {

		return queryFactory.selectFrom(redisChatRoom)
			.where(redisChatRoom.id.eq(roomId)).fetchFirst();
	}
	public List<RedisRoomDto.Response> findByAll() {

		return queryFactory
			.select(Projections.fields(RedisRoomDto.Response.class,
				redisChatRoom.id.as("roomId"),
				redisChatRoom.sender.id.as("senderId"),
				redisChatRoom.isAdminRead
				)).from(redisChatRoom).fetch();
	}
}
