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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedisChatController {

	private final RedisPublisher publisher;
	private final RedisChatService chatService;
	private final RedisRoomService roomService;

	/* 관리자는 채팅방을 생성할 수 없음 */
	@GetMapping("/api/chats/room")
	public ResponseEntity<RedisRoomDto.Response> getChatRoom() {

		return ResponseEntity.ok(roomService.getChatRoom());
	}

	@GetMapping("/api/chats/rooms")
	public ResponseEntity<List<RedisRoomDto.Response>> getAllRooms() throws IOException {

		return ResponseEntity.ok(roomService.findAllRooms());
	}

	@MessageMapping("/api/chats/{roomId}")
	@PostMapping("/api/chats/{roomId}")
	public void sendMessage(@DestinationVariable Long roomId, @RequestBody RedisMessageDto.Request request) {
		log.info("*************** 레디스 체팅 컨트롤러에 오신 여러분 환영합니다 ***********");

		// User user = userService.findLoginUser();
		Long userId = request.getId();

		publisher.publish(ChannelTopic.of("room" + roomId),
			new RedisChat(roomId, userId, request.getContent()));

		chatService.save(roomId, request);
		log.info("*************** 레디스 체팅 컨트롤러에 오신 여러분 안녕하 가셰요 ***********");
	}


	@GetMapping("/api/chats/message/{roomId}")
	public ResponseEntity<List<RedisChatMessage>> findAllChats(@PathVariable Long roomId) {

		return ResponseEntity.ok(chatService.findAllChatsInRoom(roomId));
	}
}
