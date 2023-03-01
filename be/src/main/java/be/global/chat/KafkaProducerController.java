package be.global.chat;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.global.chat.config.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaProducerController {

	private final KafkaTemplate<String, Message> kafkaTemplate;

	/* producer */
	@PostMapping
	public void sendMessage(@RequestBody Message message) {
		message.setTimestamp(LocalDateTime.now().toString());
		log.info("Produce message : " + message.toString());
		try {
			kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* 프론트엔드로 메시지를 전송 */
	@MessageMapping("/sendMessage")
	@SendTo("/topic/group")
	public Message broadcastGroupMessage(@Payload Message message) {
		return message;
	}
}