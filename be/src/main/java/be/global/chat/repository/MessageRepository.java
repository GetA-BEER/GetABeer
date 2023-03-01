package be.global.chat.repository;

import static be.global.chat.QMessage.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import be.global.chat.ChatRoom;
import be.global.chat.Message;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

	// @Autowired
	// private final KafkaMessageListenerContainer listenerContainer;
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public List<Message> findAllLastChats(List<ChatRoom> rooms) {
		return queryFactory.selectFrom(message)
			.where(Expressions.list(message.chatRoom, message.timestamp)
				.in(JPAExpressions
					.select(message.chatRoom, message.timestamp.max())
					.from(message)
					.where(message.chatRoom.in(rooms))
					.groupBy(message.chatRoom)
				)).fetch();
	}

	public void save(Message message) {
		em.persist(message);
	}

	public List<Message> findAllChatsInRoom(Long roomId) {
		return queryFactory.selectFrom(message)
			.where(message.chatRoom.id.eq(roomId))
			.fetch();
	}

	// public Optional<ChatRoom> getOrCreate() {}
}
