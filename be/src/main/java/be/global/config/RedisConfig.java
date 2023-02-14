package be.global.config;

import static be.global.config.CacheConstant.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisHost, redisPort);
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		return container;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
		return redisTemplate;
	}

	@Bean
	public RedisCacheManager redisCacheManager() {
		RedisCacheConfiguration redisCacheConfig = RedisCacheConfiguration
			.defaultCacheConfig()
			.disableCachingNullValues()
			.serializeKeysWith(RedisSerializationContext
				.SerializationPair
				.fromSerializer(new StringRedisSerializer()))
			.serializeValuesWith(RedisSerializationContext
				.SerializationPair
				.fromSerializer(new GenericJackson2JsonRedisSerializer()));

		Map<String, RedisCacheConfiguration> cacheConfiguration = new HashMap<>();

		cacheConfiguration.put(MONTHLY_BEER, redisCacheConfig.entryTtl(Duration.ofMinutes(3)));
		cacheConfiguration.put(WEEKLY_BEER, redisCacheConfig.entryTtl(Duration.ofMinutes(3)));
		cacheConfiguration.put(RECOMMEND_BEER, redisCacheConfig.entryTtl(Duration.ofMinutes(3)));

		return RedisCacheManager.RedisCacheManagerBuilder
			.fromConnectionFactory(redisConnectionFactory())
			.cacheDefaults(redisCacheConfig)
			.build();
	}
}

// package be.global.config;
//
// import static be.global.config.CacheConstant.*;
//
// import java.time.Duration;
// import java.util.HashMap;
// import java.util.Map;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.cache.annotation.CachingConfigurerSupport;
// import org.springframework.cache.annotation.EnableCaching;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.cache.RedisCacheConfiguration;
// import org.springframework.data.redis.cache.RedisCacheManager;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.RedisSerializationContext;
// import org.springframework.data.redis.serializer.StringRedisSerializer;
//
// @EnableCaching
// @Configuration
// public class RedisConfig extends CachingConfigurerSupport {
//
// 	@Value("${spring.redis.host}")
// 	private String redisHost;
//
// 	@Value("${spring.redis.port}")
// 	private int redisPort;
//
// 	/*
// 	 * Redis Server와 상호작용 하기 위한 설정. 직렬화 설정 추가
// 	 */
// 	@Bean
// 	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
// 		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
// 		redisTemplate.setConnectionFactory(connectionFactory);
// 		redisTemplate.setKeySerializer(new StringRedisSerializer());
// 		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
// 		return redisTemplate;
// 	}
//
// 	@Bean
// 	public RedisConnectionFactory redisConnectionFactory() {
// 		return new LettuceConnectionFactory(redisHost, redisPort);
// 	}
//
// }
