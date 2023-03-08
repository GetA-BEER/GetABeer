package be.global.discord;

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
		WebhookClient client = builder.build();

		// if (eventObject.getLevel().toInt() == Level.ERROR_INT) {
		// 	WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();
		// 	embedBuilder.setTitle(new WebhookEmbed.EmbedTitle("🚨🚨🚨🚨🚨🚨에러 발생!!!!🚨🚨🚨🚨🚨🚨", null));
		// 	// embedBuilder.setDescription(eventObject.getThreadName() + " " + eventObject.getLoggerName()
		// 		/*+eventObject.getLevel() +
		// 			eventObject.getFormattedMessage()
		// 		 " "
		// 		+ eventObject.getThreadName() + " "
		// 		+ eventObject.getLoggerName() + " : "
		// 		+ Arrays.toString(eventObject.getCallerData())); */
		// 	// embedBuilder.addField(new WebhookEmbed.EmbedField(true,
		// 	// 	eventObject.getThreadName() + " "
		// 	// 	+ eventObject.getLoggerName(), Arrays.toString(eventObject.getCallerData())));
		// 	embedBuilder.setColor(0xFF0000);
		// 	embedBuilder.setTimestamp(Instant.ofEpochMilli(eventObject.getTimeStamp()));
		// 	client.send(embedBuilder.build());
		// }
	}
}
