package be.domain.chat.redis.service;

import java.util.Arrays;

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
			log.info("*************** 여기는 레디스 섭스크라이버 ***************************");
			log.info("메세지 어케 들어옴? : " + Arrays.toString(message.getBody()));
			log.info("메세지 어케 들어오심? : " + Arrays.toString(message.getChannel()));

			/* 레디스 데이터 역직렬화*/
			String publishMsg = String.valueOf(redisTemplate.getStringSerializer().deserialize(message.getBody()));
			log.info("역 직렬화 체킹 : "  + publishMsg);
			RedisChat redisChat = mapper.readValue(publishMsg, RedisChat.class);
			log.info("레디스 챗 체킹 : " + redisChat.getContent());
			messageTemplate.convertAndSend("/sub/room/" + redisChat.getRoomId(), redisChat);
			messageTemplate.convertAndSend("/sub/room/mapper/" + redisChat.getRoomId(), publishMsg);
		} catch (Exception exception) {
			log.error("예외 발생 : " + exception.getMessage());
		}
	}
}
