package be.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		log.info("**여기는 웹소켓 컨피그");
		log.error("**여기는 웹소켓 컨피그");

		registry.addEndpoint("/ws")
			.setAllowedOriginPatterns("*");
			// .withSockJS(); /* 코어스 방지 */

		log.info("여기는 웹소켓 컨피그");
		log.error("여기는 웹소켓 컨피그");
		log.info("엔드포인트 확인");
		log.error("엔드포인트 확인");
		log.info("웹소케케케케켓!!!!!!!!!!");
		log.error("웹소케케케케켓!!!!!!!!!!");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		log.info("여기는 웹소켓 컨피그");
		log.error("여기는 웹소켓 컨피그");
		log.info("메세지 브로커");
		log.error("메세지 브로커");
		log.info("웹소케케케케켓!!!!!!!!!!");
		log.error("웹소케케케케켓!!!!!!!!!!");
		registry.setApplicationDestinationPrefixes("/pub");
		registry.enableSimpleBroker("/sub");
	}
}
