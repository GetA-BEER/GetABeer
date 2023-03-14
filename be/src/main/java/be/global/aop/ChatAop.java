package be.global.aop;

import java.util.Map;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import be.domain.chat.redis.entity.RedisChatRoom;
import be.domain.chat.redis.repository.RedisRoomRepository;
import be.domain.chat.redis.service.RedisSubscriber;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ChatAop {
	private final RedisSubscriber subscriber;
	private final UserService userService;
	private final Map<String, ChannelTopic> topics;
	private final RedisRoomRepository redisRoomRepository;
	private final RedisMessageListenerContainer redisMessageListener;

	@AfterReturning(value = "Pointcuts.createChatRoom() && args(saved)")
	public void createChatRoom(JoinPoint joinPoint, User saved) {
		log.info("**** 회원가입 시 채팅방 생성 ****");

		User user = userService.findUserByEmail(saved.getEmail());

		if (!user.getRoles().contains("ROLE_ADMIN")) {
			RedisChatRoom chatRoom = RedisChatRoom.create(user);
			redisRoomRepository.save(Objects.requireNonNull(chatRoom));
			String roomId = "room" + chatRoom.getId();

			if (!topics.containsKey(roomId)) {
				ChannelTopic channelTopic = new ChannelTopic(roomId);
				redisMessageListener.addMessageListener(subscriber, channelTopic);
				topics.put(roomId, channelTopic);
			}
		}
	}
}
