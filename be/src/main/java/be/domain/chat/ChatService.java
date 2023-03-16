package be.domain.chat;

import java.util.Map;
import java.util.Objects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import be.domain.chat.redis.entity.RedisChatRoom;
import be.domain.chat.redis.repository.RedisChatRepository;
import be.domain.chat.redis.repository.RedisRoomRepository;
import be.domain.chat.redis.service.RedisSubscriber;
import be.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ChatService {
	private final RedisSubscriber subscriber;
	private final Map<String, ChannelTopic> topics;
	private final RedisRoomRepository redisRoomRepository;
	private final RedisChatRepository redisChatRepository;
	private final RedisMessageListenerContainer redisMessageListener;

	public void createChatRoom(User user){

		RedisChatRoom room = redisChatRepository.isChatRoom(user.getId());

		if (room == null && !user.getRoles().contains("ROLE_ADMIN")) {
			RedisChatRoom redisChatRoom = RedisChatRoom.create(user);
			redisRoomRepository.save(Objects.requireNonNull(redisChatRoom));

			String roomId = "room" + redisChatRoom.getId();

			if (!topics.containsKey(roomId)) {
				ChannelTopic channelTopic = new ChannelTopic(roomId);
				redisMessageListener.addMessageListener(subscriber, channelTopic);
				topics.put(roomId, channelTopic);
			}
		}
	}
}
