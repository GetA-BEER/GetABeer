// package be.global.chat.config;
//
// import java.util.HashMap;
//
// import org.apache.kafka.common.serialization.StringSerializer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.annotation.EnableKafka;
// import org.springframework.kafka.core.DefaultKafkaProducerFactory;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.core.ProducerFactory;
// import org.springframework.kafka.support.serializer.JsonSerializer;
//
// import be.global.chat.Message;
//
// @EnableKafka
// @Configuration
// public class ProducerConfiguration {
//
// 	@Bean
// 	public ProducerFactory<String, Message> producerFactory() {
//
// 		return new DefaultKafkaProducerFactory<>(producerConfigurations());
// 	}
//
// 	@Bean
// 	public HashMap<String, Object> producerConfigurations() {
// 		HashMap<String, Object> configurations = new HashMap<>();
// 		configurations.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER);
// 		configurations.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
// 		configurations.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
// 		return configurations;
// 	}
//
// 	@Bean
// 	public KafkaTemplate<String, Message> kafkaTemplate() {
// 		return new KafkaTemplate<>(producerFactory());
// 	}
// }
