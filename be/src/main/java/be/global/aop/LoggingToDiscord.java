// package be.global.aop;
//
// import java.io.IOException;
// import java.net.InetAddress;
// import java.net.UnknownHostException;
// import java.util.logging.Logger;
//
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.event.EventListener;
//
// import ch.qos.logback.classic.spi.LoggingEvent;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Configuration
// public class LoggingToDiscord {
//
// 	@Value("${discord.webhook}")
// 	private String url;
//
// 	@EventListener(ApplicationReadyEvent.class)
// 	public void sendServerCheck() {
// 		DiscordWebhook webhook = new DiscordWebhook(url);
//
// 		try {
// 			String ip = InetAddress.getLocalHost().getHostName();
// 			String host = InetAddress.getLocalHost().getHostName();
//
// 			webhook.setTts(true);
// 			webhook.setContent(host + "에서" + ip + " 서버가 정상적으로 실행되었습니다.");
// 			webhook.execute();
// 		} catch (UnknownHostException e) {
// 			e.printStackTrace();
// 		} catch (IOException e) {
// 			throw new RuntimeException(e);
// 		}
// 	}
// }
