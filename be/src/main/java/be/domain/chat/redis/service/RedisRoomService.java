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

	public RedisChatRoom getChatRoom(Long roomId) {

		return roomRepository.findById(roomId)
			.orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다."));
	}

	/* 채팅방이 만들어지지 않은 유저 만들어주기*/
	@Transactional
	public void createChatRoom(Long userId) {
		User loginUser = userService.findLoginUser();

		if (!loginUser.getRoles().contains("ROLE_ADMIN")) {
			throw new RuntimeException("관리자만 접근할 수 있습니다.");
		}

		User user = userService.getUser(userId);
		RedisChatRoom chatRoom = chatRepository.isChatRoom(user.getId());
		if (chatRoom != null) {
			throw new RuntimeException("이미 채팅방이 존재합니다.");
		}

		RedisChatRoom redisChatRoom = RedisChatRoom.create(user);
		String roomId = "room" + Objects.requireNonNull(redisChatRoom).getId();

		if (!topics.containsKey(roomId)) {
			ChannelTopic channelTopic = new ChannelTopic(roomId);
			redisMessageListener.addMessageListener(subscriber, channelTopic);
			topics.put(roomId, channelTopic);

			/* 관리자를 수동으로 구독자를 만드는 방법 ?*/
			// List<User> findAdmin = userService.findAdminUser();
			// for (User value : findAdmin) {
			// 	redisMessageListener.setSubscriptionExecutor();
			// }
		}

		roomRepository.save(Objects.requireNonNull(redisChatRoom));
	}
}
