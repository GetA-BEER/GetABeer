package be.domain.mail.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Value("${spring.mail.port}")
	private int port;
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.username}")
	private String username;
	@Value("${spring.mail.password}")
	private String password;
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private Boolean auth;
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private Boolean starttls;
	// @Value("${spring.mail.properties.mail.smtp.starttls.required}")
	// private Boolean starttlsRequired;
	// @Value("${spring.mail.properties.mail.smtp.socketFactory.fallback}")
	// private Boolean fallback;

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(host);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		javaMailSender.setPort(port);
		javaMailSender.setJavaMailProperties(getMailProperties());
		javaMailSender.setDefaultEncoding("UTF-8");
		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.debug", "true");
		properties.put("mail.smtp.starttls.enable", starttls);
		// properties.put("mail.smtp.starttls.required", starttlsRequired);
		// properties.put("mail.smtp.socketFactory.fallback",fallback);
		// properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		return properties;
	}
}