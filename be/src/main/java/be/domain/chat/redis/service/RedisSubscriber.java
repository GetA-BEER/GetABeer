package be.domain.chat.redis.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.domain.chat.redis.entity.RedisChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper mapper;
	private final RedisTemplate redisTemplate;
	private final SimpMessageSendingOperations messageTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			/* 레디스 데이터 역직렬화*/
			String publishMsg = String.valueOf(redisTemplate.getStringSerializer().deserialize(message.getBody()));
			RedisChat redisChat = mapper.readValue(publishMsg, RedisChat.class);
			messageTemplate.convertAndSend("/sub/room/" + redisChat.getRoomId(), redisChat);
		} catch (Exception exception) {
			log.error("예외 발생 : " + exception.getMessage());
		}
	}
}
