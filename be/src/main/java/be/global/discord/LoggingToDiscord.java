package be.global.discord;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LoggingToDiscord {

	@Value("${discord.webhook}")
	private String url;

	@EventListener(ApplicationReadyEvent.class)
	public void sendServerCheck() {
		WebhookClientBuilder builder = new WebhookClientBuilder(url);
		WebhookClient client = builder.build();
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			String host = InetAddress.getLocalHost().getHostName();

			WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();
			embedBuilder.setTitle(new WebhookEmbed.EmbedTitle("🎉🎉🎉서버 실행 확인🎉🎉🎉", null));
			embedBuilder.setColor(0x5ced73);
			embedBuilder.setDescription(host + " 에서 " + ip + " 서버가 정상적으로 실행되었습니다.");
			client.send(embedBuilder.build());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
