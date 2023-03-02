package be.global.chat.redis.controller;

import java.util.List;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.chat.redis.dto.RedisMessageDto;
import be.global.chat.redis.entity.RedisChat;
import be.global.chat.redis.entity.RedisChatMessage;
import be.global.chat.redis.entity.RedisChatRoom;
import be.global.chat.redis.service.RedisChatService;
import be.global.chat.redis.service.RedisPublisher;
import be.global.chat.redis.service.RedisRoomService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class RedisChatController {

	private final UserService userService;
	private final RedisPublisher publisher;
	private final RedisChatService chatService;
	private final RedisRoomService roomService;

	@GetMapping("/room")
	public ResponseEntity<RedisChatRoom> createRoom() {

		return ResponseEntity.ok(roomService.getOrCreate());
	}

	@GetMapping("/rooms")
	public ResponseEntity<List<RedisChatRoom>> getAllRooms() {

		return ResponseEntity.ok(roomService.findAllRooms());
	}

	@MessageMapping("/{roomId}")
	public void sendMessage(@DestinationVariable Long roomId, RedisMessageDto.Request request) {
		User user = userService.findLoginUser();
		Long userId = user.getId();

		publisher.publish(ChannelTopic.of("room" + roomId),
			new RedisChat(roomId, userId, request.getContent()));
	}

	@GetMapping("/message/{roomId}")
	public ResponseEntity<List<RedisChatMessage>> findAllChats(@PathVariable Long roomId) {

		return ResponseEntity.ok(chatService.findAllChatsInRoom(roomId));
	}
}
