package com.isuper.soft.home.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * 
 * Redis配置
 *
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {

	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		return cacheManager;
	}

	@Bean
	public KeyGenerator openApiKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName()).append("-");
				sb.append(method.getName()).append(":");
				boolean first = true;
				for (Object obj : params) {
					if (!first) {
						sb.append(".");
						first = false;
					}
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

}
