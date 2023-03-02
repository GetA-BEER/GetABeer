package be.global.chat.kafka.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.chat.ChatRoom;
import be.global.chat.Message;
import be.global.chat.kafka.KafkaConstants;
import be.global.chat.kafka.dto.MessageRequest;
import be.global.chat.kafka.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

	private final UserService userService;
	private final ChatRoomRepository chatRoomRepository;
	private final KafkaTemplate<String, Message> kafkaTemplate;

	@Transactional
	public String send(MessageRequest request) {
		User user = userService.findLoginUser();
		ChatRoom chatRoom = chatRoomRepository.findById(user.getId());

		Message message = Message.builder()
			.roomId(chatRoom.getId())
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
			kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
			log.info("성공적 메세지 발송 : " + message.toString());
		} catch (Exception e) {
			log.error("ERROR 발생 : "  + e.getMessage());
			throw new RuntimeException(e);
		}

		return "메세지가 전송되었습니다.";
	}

	public List<ChatRoom> findAll() {

		return chatRoomRepository.findAllChatRoom();
	}

	/* 로그 찍으면서 메서드 체킹 */
	public ChatRoom findAllMessage(Long roomId) {

		return chatRoomRepository.findById(roomId);
	}
}
