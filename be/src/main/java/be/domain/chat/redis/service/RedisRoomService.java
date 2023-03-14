package be.domain.chat.redis.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.chat.redis.repository.RedisChatRepository;
import be.domain.chat.redis.repository.RedisRoomRepository;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.domain.chat.redis.dto.RedisRoomDto;
import be.domain.chat.redis.entity.RedisChatRoom;
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

	public RedisRoomDto.Response getChatRoom() {
		User user = userService.findLoginUser();
		if (!user.getRoles().contains("USER_ADMIN")) {

			return chatRepository.getChatRoom(user.getId());
		}

		/* 관리자는 채팅방 리스트를 불러온 후 -> 해당 채팅방 입장? */
		throw new RuntimeException("관리자 요청 메서드가 아닙니다.");
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
