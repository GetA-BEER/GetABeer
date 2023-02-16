// package be.global.chat;
//
// import org.springframework.kafka.annotation.EnableKafka;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Service;
//
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Service
// public class KafkaConsumerService {
//
// 	private final SimpMessagingTemplate template;
//
// 	public KafkaConsumerService(SimpMessagingTemplate template) {
// 		this.template = template;
// 	}
//
// 	@KafkaListener(topics = "getabeer")
// 	public void listen(Message message) {
// 		log.info("카프카 리스너로 전송 메세지 : " + message.getAuthor());
//
// 		template.convertAndSend("/topic/group", message);
// 	}
// }
