// package be.domain.chat.kafka;
//
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.messaging.handler.annotation.Payload;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Service;
//
// import be.domain.chat.kafka.entity.KafkaChatMessage;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class KafkaConsumerService {
//
// 	private final SimpMessagingTemplate template;
//
// 	// @KafkaListener(topics = KafkaConstants.TOPIC, groupId = KafkaConstants.GROUP_ID)
// 	public void listen(@Payload KafkaChatMessage message) {
// 		log.info("카프카 리스너로 전송 메세지 : " + message.getSender());
//
// 		template.convertAndSend("/topic/group", message);
// 	}
// }
