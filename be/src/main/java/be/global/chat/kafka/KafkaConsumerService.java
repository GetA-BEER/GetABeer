package be.global.chat.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import be.global.chat.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

	private final SimpMessagingTemplate template;

	@KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.GROUP_ID)
	public void listen(@Payload Message message) {
		log.info("카프카 리스너로 전송 메세지 : " + message.getSender());

		template.convertAndSend("/topic/group", message);
	}
}
