package be.domain.chat.redis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.domain.chat.redis.dto.RedisRoomDto;
import be.domain.chat.redis.service.RedisChatService;
import be.domain.chat.redis.service.RedisPublisher;
import be.domain.chat.redis.service.RedisRoomService;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.domain.chat.redis.dto.RedisMessageDto;
import be.domain.chat.redis.entity.RedisChat;
import be.domain.chat.redis.entity.RedisChatMessage;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class RedisChatController {

	private final UserService userService;
	private final RedisPublisher publisher;
	private final RedisChatService chatService;
	private final RedisRoomService roomService;

	/* 관리자는 채팅방을 생성할 수 없음 */
	@GetMapping("/room")
	public ResponseEntity<Long> createRoom() {

		return ResponseEntity.status(HttpStatus.CREATED).body(roomService.getOrCreate());
	}

	@GetMapping("/rooms")
	public ResponseEntity<List<RedisRoomDto.Response>> getAllRooms() throws IOException {

		return ResponseEntity.ok(roomService.findAllRooms());
	}

	@MessageMapping("/{roomId}")
	@PostMapping("/{roomId}")
	public void sendMessage(@DestinationVariable Long roomId, @RequestBody RedisMessageDto.Request request) {
		User user = userService.findLoginUser();
		Long userId = user.getId();

		publisher.publish(ChannelTopic.of("room" + roomId),
			new RedisChat(roomId, userId, request.getContent()));

		// chatService.save(roomId, request);
	}

	@GetMapping("/message/{roomId}")
	public ResponseEntity<List<RedisChatMessage>> findAllChats(@PathVariable Long roomId) {

		return ResponseEntity.ok(chatService.findAllChatsInRoom(roomId));
	}
}
