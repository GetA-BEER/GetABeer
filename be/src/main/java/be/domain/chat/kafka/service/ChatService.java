package be.domain.chat.kafka.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.chat.kafka.dto.MessageDto;
import be.domain.chat.kafka.entity.KafkaChatMessage;
import be.domain.chat.kafka.repository.ChatRoomRepository;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.domain.chat.kafka.entity.KafkaChatRoom;
import be.domain.chat.kafka.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

	private final UserService userService;
	private final ChatRoomRepository chatRoomRepository;
	private final KafkaTemplate<String, KafkaChatMessage> kafkaTemplate;

	@Transactional
	public String send(MessageDto.Request request) {
		User user = userService.findLoginUser();
		KafkaChatRoom kafkaChatRoom = chatRoomRepository.findById(user.getId());

		KafkaChatMessage message = KafkaChatMessage.builder()
			.roomId(kafkaChatRoom.getId())
			.sender(user.getNickname())
			.content(request.getContent())
			.timestamp(LocalDateTime.now().toString())
			.build();

		/* 비동기 처리 */
		// ListenableFuture<SendResult<String, Message>> future =
		// kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message);
		// future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
		// 	@Override
		// 	public void onFailure(Throwable ex) {
		// 		log.error("******************************************");
		// 		log.error("메세지를 발송할 수 없습니다. : " + message.getContent());
		// 		log.error("이유 : " + ex.getMessage());
		// 		log.error("******************************************");
		// 	}
		//
		// 	@Override
		// 	public void onSuccess(SendResult<String, Message> result) {
		// 		log.info("********************************************");
		// 		log.info("메세지가 성공적으로 발송되었습니다." + message.getContent());
		// 		log.info("with offset : " + result.getRecordMetadata().offset());
		// 		log.info("********************************************");
		// 	}
		// });

		/* 동기 처리 */
		try {
			if (request.getType().equalsIgnoreCase("SUGGEST")) {
				kafkaTemplate.send(KafkaConstants.TOPIC_SUGGEST, message).get();
			} else if (request.getType().equalsIgnoreCase("REPORT")) {
				kafkaTemplate.send(KafkaConstants.TOPIC_REPORT, message).get();
			} else {
				throw new RuntimeException("잘못된 메세지 타입입니다.");
			}

			log.info("성공적 메세지 발송 : " + message.toString());
		} catch (Exception e) {
			log.error("ERROR 발생 : "  + e.getMessage());
			throw new RuntimeException(e);
		}

		return "메세지가 전송되었습니다.";
	}

	public List<KafkaChatRoom> findAll() {

		return chatRoomRepository.findAllChatRoom();
	}

	/* 로그 찍으면서 메서드 체킹 */
	public KafkaChatRoom findAllMessage(Long roomId) {

		return chatRoomRepository.findById(roomId);
	}
}
