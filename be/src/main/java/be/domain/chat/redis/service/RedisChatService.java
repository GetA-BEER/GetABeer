package be.domain.chat.redis.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.chat.redis.repository.RedisChatRepository;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.domain.chat.redis.dto.RedisMessageDto;
import be.domain.chat.redis.entity.RedisChatMessage;
import be.domain.chat.redis.entity.RedisChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisChatService {
	private final UserService userService;
	private final RedisRoomService roomService;
	private final RedisChatRepository chatRepository;

	public List<RedisChatMessage> findAllChatsInRoom(Long roomId) {

		User user = userService.findLoginUser();

		RedisChatRoom chatRoom = roomService.findById(roomId);

		return chatRepository.findAllChatInRoom(chatRoom.getId());
	}

	@Transactional
	public void save(Long roomId, RedisMessageDto.Request request) {
		User user = userService.getUser(request.getId());

		RedisChatRoom room = roomService.findById(roomId);

		RedisChatMessage message = RedisChatMessage.builder()
			.chatRoom(room)
			.sender(user)
			.content(request.getContent())
			// .type(request.getType())
			.createdAt(LocalDateTime.now())
			.build();

		chatRepository.save(message);
	}
}
