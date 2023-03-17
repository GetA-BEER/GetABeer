package be.domain.chat.redis.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.chat.redis.entity.ChatType;
import be.domain.chat.redis.repository.RedisChatRepository;
import be.domain.chat.redis.repository.RedisRoomRepository;
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
public class RedisChatService {
	private final UserService userService;
	private final RedisRoomService roomService;
	private final RedisChatRepository chatRepository;
	private final RedisRoomRepository roomRepository;

	@Transactional
	public List<RedisMessageDto.Response> findAllChatsInRoom(Long roomId) {

		User user = userService.findLoginUser();

		RedisChatRoom chatRoom = roomService.getChatRoom(roomId);

		/* 이곳에 로그인 유저가 관리자가 아니고, 룸 아이디랑 유저 아이디가 다르면 예외를 던질 메서드를 추가할 예정 */

		List<RedisMessageDto.Response> responses =  chatRepository.findAllChatInRoom(chatRoom.getId());

		responses.forEach(
			list -> {
				User sender = userService.getUser(list.getUserId());
				list.addRole(sender);
			}
		);

		return responses;
	}

	@Transactional
	public void save(Long roomId, RedisMessageDto.Request request, User user) {

		RedisChatRoom room = roomService.getChatRoom(roomId);

		/* 이곳에 로그인 유저가 관리자가 아니고, 룸 아이디랑 유저 아이디가 다르면 예외를 던질 메서드를 추가할 예정 */

		RedisChatMessage message = RedisChatMessage.builder()
			.chatRoom(room)
			.sender(user)
			.content(request.getContent())
			.type(ChatType.to(request.getType()))
			.createdAt(LocalDateTime.now())
			.build();

		chatRepository.save(message);

		room.changeStatus(user.getRoles().contains("ROLE_ADMIN"));

		roomRepository.save(room);
	}
}
