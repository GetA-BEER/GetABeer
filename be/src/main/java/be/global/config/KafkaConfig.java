// package be.global.config;
//
// import java.util.HashMap;
// import java.util.Map;
//
// import org.apache.kafka.clients.admin.AdminClientConfig;
// import org.apache.kafka.clients.admin.NewTopic;
// import org.apache.kafka.clients.consumer.ConsumerConfig;
// import org.apache.kafka.common.serialization.StringDeserializer;
// import org.apache.kafka.common.serialization.StringSerializer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.annotation.EnableKafka;
// import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
// import org.springframework.kafka.core.ConsumerFactory;
// import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
// import org.springframework.kafka.core.DefaultKafkaProducerFactory;
// import org.springframework.kafka.core.KafkaAdmin;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.core.ProducerFactory;
// import org.springframework.kafka.support.serializer.JsonDeserializer;
// import org.springframework.kafka.support.serializer.JsonSerializer;
//
// import be.domain.chat.kafka.entity.KafkaChatMessage;
// import be.domain.chat.kafka.KafkaConstants;
//
// // @EnableKafka
// // @Configuration
// class KafkaConfig {
//
// 	/* --------------------------------------------- 카프카 토픽 등록? ------------------------------------------------- */
// 	@Bean
// 	public KafkaAdmin kafkaAdmin() {
// 		Map<String, Object> config = new HashMap<>();
// 		config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER);
//
// 		return new KafkaAdmin(config);
// 	}
//
// 	@Bean
// 	public NewTopic topic() {
// 		return new NewTopic(KafkaConstants.TOPIC, 1, (short) 1);
// 	}
//
//
// 	/* --------------------------------------------- 카프카 컨슈머 빈 ------------------------------------------------- */
// 	@Bean
// 	ConcurrentKafkaListenerContainerFactory<String, KafkaChatMessage> kafkaListenerContainerFactory() {
// 		ConcurrentKafkaListenerContainerFactory<String, KafkaChatMessage> factory =
// 			new ConcurrentKafkaListenerContainerFactory<>();
// 		factory.setConsumerFactory(consumerFactory());
//
// 		return factory;
// 	}
//
// 	@Bean
// 	public ConsumerFactory<String, KafkaChatMessage> consumerFactory() {
//
// 		return new DefaultKafkaConsumerFactory<>(consumerConfigurations(),
// 			new StringDeserializer(), new JsonDeserializer<>(KafkaChatMessage.class));
// 	}
//
// 	@Bean
// 	public Map<String, Object> consumerConfigurations() {
// 		Map<String, Object> configurations = new HashMap<>();
// 		configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER);
// 		configurations.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID);
// 		configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
// 		configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
// 		configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
// 		return configurations;
// 	}
//
// 	/* --------------------------------------------- 카프카 프로듀서 빈 ------------------------------------------------- */
// 	@Bean
// 	public ProducerFactory<String, KafkaChatMessage> producerFactory() {
//
// 		return new DefaultKafkaProducerFactory<>(producerConfigurations());
// 	}
//
// 	@Bean
// 	public HashMap<String, Object> producerConfigurations() {
// 		HashMap<String, Object> configurations = new HashMap<>();
// 		configurations
// 			.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
// 				KafkaConstants.KAFKA_BROKER);
// 		configurations
// 			.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
// 				StringSerializer.class);
// 		configurations
// 			.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
// 				JsonSerializer.class);
//
// 		return configurations;
// 	}
//
// 	@Bean
// 	public KafkaTemplate<String, KafkaChatMessage> kafkaTemplate() {
// 		return new KafkaTemplate<>(producerFactory());
// 	}
// }
