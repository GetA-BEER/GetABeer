package be.domain.chat.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import be.domain.chat.redis.entity.RedisChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {

	private final RedisTemplate<String, Object> redisTemplate;

	public void publish(ChannelTopic topic, RedisChat message) {
		log.info("여기는 레디스 퍼블리셔");
		log.error("여기는 레디스 퍼블리셔");
		log.info("요청이 들어온다아아아아아악!!!!");
		log.error("요청이 들어온다아아아아아악!!!!");
		redisTemplate.convertAndSend(topic.getTopic(), message);
		log.info("*********** 레디스로 전송 ***********");
	}
}
