// package be.global.chat.repository;
//
// import static be.global.chat.QMessage.*;
//
// import java.util.Arrays;
// import java.util.List;
// import java.util.Objects;
//
// import javax.persistence.EntityManager;
//
// import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
// import org.springframework.kafka.core.ConsumerFactory;
// import org.springframework.stereotype.Repository;
//
// import com.querydsl.core.types.dsl.Expressions;
// import com.querydsl.jpa.JPAExpressions;
// import com.querydsl.jpa.impl.JPAQueryFactory;
//
// import be.global.chat.ChatRoom;
// import be.global.chat.Message;
// import lombok.RequiredArgsConstructor;
//
// @Repository
// @RequiredArgsConstructor
// public class MessageRepository {
//
// 	private final ConsumerFactory<String, Message> consumerFactory;
// 	private final EntityManager em;
// 	private final JPAQueryFactory queryFactory;
//
// 	public List<Message> findAllLastChats(List<ChatRoom> rooms) {
// 		return queryFactory.selectFrom(message)
// 			.where(Expressions.list(message.chatRoom, message.timestamp)
// 				.in(JPAExpressions
// 					.select(message.chatRoom, message.timestamp.max())
// 					.from(message)
// 					.where(message.chatRoom.in(rooms))
// 					.groupBy(message.chatRoom)
// 				)).fetch();
// 	}
//
// 	public void save(Message message) {
// 		em.persist(message);
// 	}
//
// 	public List<Message> findAllChatsInRoom(Long roomId) {
// 		return queryFactory.selectFrom(message)
// 			.where(message.chatRoom.id.eq(roomId))
// 			.fetch();
// 	}
//
// 	public void checkLog() {
// 		System.out.println("***********************************************************");
// 		System.out.println("카프카 리스너 토픽 : " + Arrays.toString(consumerFactory.getListeners().toArray()));
// 		System.out.println("카프카 리스너 그룹 아이디 : " + Objects.requireNonNull(consumerFactory.getValueDeserializer()).toString());
// 		System.out.println("카프카 리스너 포스트 프로세스 : " + Arrays.toString(consumerFactory.getPostProcessors().toArray()));
// 		System.out.println("***********************************************************");
// 	}
//
// 	// public Optional<ChatRoom> getOrCreate() {}
// }
