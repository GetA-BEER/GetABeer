package be.global.chat.redis.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.chat.redis.dto.RedisMessageDto;
import be.global.chat.redis.entity.RedisChatRoom;
import be.global.chat.redis.repository.RedisChatRepository;
import be.global.chat.redis.repository.RedisRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisRoomService {
	private final UserService userService;
	private final RedisSubscriber subscriber;
	private final Map<String, ChannelTopic> topics;
	private final RedisChatRepository chatRepository;
	private final RedisRoomRepository roomRepository;
	private final RedisMessageListenerContainer redisMessageListener;

	public RedisChatRoom getOrCreate() {
		User user = userService.findLoginUser();
		RedisChatRoom chatRoom =
			chatRepository.getOrCreateRoom(user.getId())
				.orElseGet(() -> RedisChatRoom.create(user));
		String roomId = "room" + chatRoom.getId();

		if (!topics.containsKey(roomId)) {
			ChannelTopic channelTopic = new ChannelTopic(roomId);
			redisMessageListener.addMessageListener(subscriber, channelTopic);
			topics.put(roomId, channelTopic);
		}

		return roomRepository.save(chatRoom);
	}

	/* 어드민용? */
	@Transactional(readOnly = true)
	public List<RedisChatRoom> findAllRooms() {

		return roomRepository.findByAll();
	}

	public RedisChatRoom findById(Long roomId) {

		return roomRepository.findById(roomId)
			.orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다."));
	}
}
