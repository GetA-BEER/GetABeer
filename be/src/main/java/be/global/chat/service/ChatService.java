package be.global.chat.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.chat.ChatRoom;
import be.global.chat.Message;
import be.global.chat.config.KafkaConstants;
import be.global.chat.dto.MessageRequest;
import be.global.chat.repository.ChatRoomRepository;
import be.global.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

	private final UserService userService;
	private final MessageRepository messageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final KafkaTemplate<String, Message> kafkaTemplate;

	public Message send(MessageRequest request) {
		User user = userService.findLoginUser();
		ChatRoom chatRoom = chatRoomRepository.findById(user.getId());

		Message message = Message.builder()
			.chatRoom(chatRoom)
			.sender(user.getNickname())
			.content(request.getContent())
			.timestamp(LocalDateTime.now())
			.build();

		messageRepository.save(message);

		ListenableFuture<SendResult<String, Message>> future = kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
			@Override
			public void onFailure(Throwable ex) {
				log.error("******************************************");
				log.error("메세지를 발송할 수 없습니다. : " + message.getContent());
				log.error("이유 : " + ex.getMessage());
				log.error("******************************************");
			}

			@Override
			public void onSuccess(SendResult<String, Message> result) {
				log.info("********************************************");
				log.info("메세지가 성공적으로 발송되었습니다." + message.getContent());
				log.info("with offset : " + result.getRecordMetadata().offset());
				log.info("********************************************");
			}
		});

		// try {
		// 	kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
		// 	log.info("성공적 메세지 발송 : " + message.toString());
		// } catch (Exception e) {
		// 	log.error("ERROR 발생 : "  + e.getMessage());
		// 	throw new RuntimeException(e);
		// }

		return message;
	}

	public List<ChatRoom> findAll() {

		return chatRoomRepository.findAllChatRoom();
	}

	/* 로그 찍으면서 메서드 체킹 */
	public void findAllMessage() {
		kafkaTemplate.flush();
		log.info("Default Topic : " + kafkaTemplate.getDefaultTopic());
		log.info("Transaction Id Perfix: " + kafkaTemplate.getTransactionIdPrefix());
		log.info("Partitions For : " + Arrays.toString(kafkaTemplate.partitionsFor("getabeer").toArray()));
		log.info("Producer Factory" + kafkaTemplate.getProducerFactory().getListeners());
	}
}
