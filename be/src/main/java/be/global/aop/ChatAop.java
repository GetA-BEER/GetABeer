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
import be.domain.chat.redis.repository.RedisChatRepository;
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
	private final RedisChatRepository redisChatRepository;
	private final RedisMessageListenerContainer redisMessageListener;

	@AfterReturning(value = "Pointcuts.createChatRoom() && args(saved)")
	public void createChatRoom(JoinPoint joinPoint, User saved) {
		log.info("**** 회원가입 시 채팅방 생성 ****");

		User user = userService.findUserByEmail(saved.getEmail());

		createChatRoom(user);
	}

	@AfterReturning(value = "Pointcuts.googleChatRoom() && args(userBuilder)")
	public void googleChatRoom(JoinPoint joinPoint, User userBuilder) {
		log.info("***** 구글 소셜 로그인 시 채팅방이 없으면 자동 생성 ****");

		User user = userService.findUserByEmail(userBuilder.getEmail());

		createChatRoom(user);
	}

	@AfterReturning(value = "Pointcuts.kakaoChatRoom() && args(userBuilder)")
	public void kakaoChatRoom(JoinPoint joinPoint, User userBuilder) {
		log.info("***** 카카오 소셜 로그인 시 채팅방이 없으면 자동 생성 ****");

		User user = userService.findUserByEmail(userBuilder.getEmail());

		createChatRoom(user);
	}

	@AfterReturning(value = "Pointcuts.naverChatRoom() && args(userBuilder)")
	public void naverChatRoom(JoinPoint joinPoint, User userBuilder) {
		log.info("***** 네이버 소셜 로그인 시 채팅방이 없으면 자동 생성 ****");

		User user = userService.findUserByEmail(userBuilder.getEmail());

		createChatRoom(user);
	}

	private void createChatRoom(User user){

		RedisChatRoom room = redisChatRepository.isChatRoom(user.getId());

		if (room == null && !user.getRoles().contains("ROLE_ADMIN")) {
			RedisChatRoom redisChatRoom = RedisChatRoom.create(user);
			redisRoomRepository.save(Objects.requireNonNull(redisChatRoom));

			String roomId = "room" + redisChatRoom.getId();

			if (!topics.containsKey(roomId)) {
				ChannelTopic channelTopic = new ChannelTopic(roomId);
				redisMessageListener.addMessageListener(subscriber, channelTopic);
				topics.put(roomId, channelTopic);

				/* 관리자를 구독자로 어떻게 만들지? */
				// List<User> findAdmin = userService.findAdminUser();
				// for (User value : findAdmin) {
				// 	redisMessageListener.addMessageListener((MessageListener) value, channelTopic);
				// }
			}
		}
	}
}
