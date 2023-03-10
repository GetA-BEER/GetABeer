package be.domain.chat.kafka.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.domain.chat.kafka.dto.MessageDto;
import be.domain.chat.kafka.entity.KafkaChatMessage;
import be.domain.chat.kafka.entity.KafkaChatRoom;
import be.domain.chat.kafka.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	/* producer */
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> sendMessage(@RequestBody MessageDto.Request request) {

		return ResponseEntity.ok(chatService.send(request));
	}

	/* 프론트엔드 메시지 전송 */
	@MessageMapping("/send")
	@SendTo("/topic/group") /* 해당 토픽을 구독하고 있는 클라이언트에게 전송 */
	public ResponseEntity<String> broadcastGroupMessage(@Payload MessageDto.Request request) {
		log.info("구독자들에게 메세지 전송");
		log.info("메세지 내용 : " +  request.getContent());

		return ResponseEntity.ok(chatService.send(request));
	}

	/* 카프카로 보내고 나면 출력이 되지만, 바로 실행하면 출력 안됨. -> 디비에 저장 필요? */
	@GetMapping
	public ResponseEntity<List<KafkaChatRoom>> getAllRoom() {

		return ResponseEntity.ok(chatService.findAll());
	}

	@GetMapping("/{roomId}")
	public ResponseEntity<KafkaChatRoom> getAllMessageAtChatRoom(@PathVariable Long roomId) {

		/* 메세지 리스트는 가지 않음...*/
		return ResponseEntity.ok(chatService.findAllMessage(roomId));
	}
}