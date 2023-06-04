//package com.codigo.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.stereotype.Component;
//
//@Component
//@EnableRedisRepositories
//public class RedisConfig {
//	
//	@Bean
//	public StringRedisSerializer stringRedisSerializer() {
//	  StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//	  return stringRedisSerializer;
//	}
//
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate() {
//		RedisTemplate<String, Object> template = new RedisTemplate<>();
//		template.setConnectionFactory(jedisConnectionFactory());
//		template.setExposeConnection(true);
//		// No serializer required all serialization done during impl
//		template.setKeySerializer(stringRedisSerializer());
//		// `redisTemplate.setHashKeySerializer(stringRedisSerializer());
//		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//		template.afterPropertiesSet();
//		template.setEnableTransactionSupport(true);
//		return template;
//	}
//
//	@Bean
//	JedisConnectionFactory jedisConnectionFactory() {
//		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
//		jedisConFactory.setHostName("localhost");
//		jedisConFactory.setPort(6379);
//		jedisConFactory.setUsePool(true);
//		jedisConFactory.getPoolConfig().setMaxIdle(30);
//		jedisConFactory.getPoolConfig().setMinIdle(10);
//		return jedisConFactory;
//	}
//}
