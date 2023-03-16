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
import be.domain.chat.redis.entity.RedisChatRoom;
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
	private final UserService userService;

	/* 자신의 채팅방 확인 -> 오로지 회원만 가능 */
	@GetMapping("/api/chats/room")
	public ResponseEntity<RedisRoomDto.Response> getChatRoom() {

		return ResponseEntity.ok(roomService.getChatRoom());
	}

	/* 관리자가 모든 채팅방 확인? */
	@GetMapping("/api/chats/rooms")
	public ResponseEntity<List<RedisRoomDto.Response>> getAllRooms() throws IOException {

		return ResponseEntity.ok(roomService.findAllRooms());
	}

	@MessageMapping("/api/chats/{roomId}")
	@PostMapping("/api/chats/{roomId}")
	public void sendMessage(@DestinationVariable Long roomId, @RequestBody RedisMessageDto.Request request) {
		log.info("*************** 레디스 체팅 컨트롤러에 오신 여러분 환영합니다 ***********");

		User user = userService.findLoginUser();

		publisher.publish(ChannelTopic.of("room" + roomId),
			new RedisChat(roomId, user.getId(), request.getContent(), request.getType()));

		chatService.save(roomId, request, user);
		log.info("*************** 레디스 체팅 컨트롤러에 오신 여러분 안녕하 가셰요 ***********");
	}


	/* 위에서 룸 아이디를 받는 이유 -> 관리자가 메세지를 가져와야 할 수 도 있기 때문 */
	@GetMapping("/api/chats/message/{roomId}")
	public ResponseEntity<List<RedisMessageDto.Response>> findAllChats(@PathVariable Long roomId) {

		return ResponseEntity.ok(chatService.findAllChatsInRoom(roomId));
	}

	/* 채팅방 생성 : ONLY 관리자 */
	@PostMapping("/api/chat/create/{userId}")
	public void createRoom(@PathVariable Long userId) {

		roomService.createChatRoom(userId);
	}
}
