package be.global.chat.redis.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.chat.redis.dto.RedisRoomDto;
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

	public Long getOrCreate() {
		User user = userService.findLoginUser();
		if (!user.getRoles().contains("USER_ADMIN")) {
			RedisChatRoom chatRoom =
				chatRepository.getOrCreateRoom(user.getId())
					.orElseGet(() -> RedisChatRoom.create(user));
			String roomId = "room" + Objects.requireNonNull(chatRoom).getId();
			roomRepository.saveAndFlush(chatRoom);
			if (!topics.containsKey(roomId)) {
				ChannelTopic channelTopic = new ChannelTopic(roomId);
				redisMessageListener.addMessageListener(subscriber, channelTopic);
				topics.put(roomId, channelTopic);
			}

			return chatRoom.getId();
		}
		throw new RuntimeException("관리자는 채팅방을 생성할 수 없습니다.");
	}

	/* 어드민용? */
	@Transactional(readOnly = true)
	public List<RedisRoomDto.Response> findAllRooms() throws IOException {
		List<RedisRoomDto.Response> responses = chatRepository.findByAll();
		// responses.forEach(list -> list.isAdminRead(chatRepository.findChatRoom(list.getRoomId())));

		return responses;
	}

	public RedisChatRoom findById(Long roomId) {

		return roomRepository.findById(roomId)
			.orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다."));
	}
}
