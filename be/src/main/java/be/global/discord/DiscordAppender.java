package be.global.discord;

import java.io.File;
import java.time.Instant;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiscordAppender extends AppenderBase<ILoggingEvent> {
	private WebhookClient client;
	private String webhookUri;

	Layout<ILoggingEvent> layout;

	@Override
	public void start() {
		if (this.layout == null) {
			addError("No layout set for the appender named [" + name + "].");
			return;
		}

		String header = layout.getFileHeader();
		System.out.print(header);
		super.start();
	}

	public Layout<ILoggingEvent> getLayout() {
		return layout;
	}

	public void setLayout(Layout<ILoggingEvent> layout) {
		this.layout = layout;
	}

	public String getWebhookUri() {
		return webhookUri;
	}

	public void setWebhookUri(String webhookUri) {
		this.webhookUri = webhookUri;
	}

	@Override
	protected void append(ILoggingEvent eventObject) {
		WebhookClientBuilder builder = new WebhookClientBuilder(webhookUri);
		client = builder.build();

		if (eventObject.getLevel().toInt() == Level.ERROR_INT) {
			WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();
			embedBuilder.setTitle(new WebhookEmbed.EmbedTitle("🚨🚨🚨에러가 발생했습니다!!!!🚨🚨🚨", null));
			embedBuilder.setDescription("[" + eventObject.getThreadName() + "] " + eventObject.getLoggerName());
			embedBuilder.addField(new WebhookEmbed.EmbedField(
				true,
				"자세한 에러사항은 첨부파일을 확인해주세요.",
				/*Arrays.toString(eventObject.getCallerData())*/ ""));
			embedBuilder.setColor(0xFF0000);
			embedBuilder.setTimestamp(Instant.ofEpochMilli(eventObject.getTimeStamp()));
			client.send(embedBuilder.build());
			client.send(new File("be/error/error.log"));
		}
	}
}
