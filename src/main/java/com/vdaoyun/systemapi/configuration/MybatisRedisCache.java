package com.vdaoyun.systemapi.configuration;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

@DependsOn("SpringContextHolder")
public class MybatisRedisCache implements Cache {

	private static final Logger log = LoggerFactory.getLogger(MybatisRedisCache.class);

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

	private RedisTemplate<String, Object> redisTemplate = SpringContextHolder.getBean("redisTemplate");

	private String id;

	public MybatisRedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		log.info("Redis Cache id " + id);
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void putObject(Object key, Object value) {
		if (value != null) {
			redisTemplate.opsForValue().set(key.toString(), value, 1, TimeUnit.DAYS);
		}
	}

	@Override
	public Object getObject(Object key) {
		try {
			if (key != null) {
				Object obj = redisTemplate.opsForValue().get(key.toString());
				return obj;
			}
		} catch (Exception e) {
			log.error("redis ");
		}
		return null;
	}

	@Override
	public Object removeObject(Object key) {
		try {
			if (key != null) {
				redisTemplate.delete(key.toString());
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void clear() {

		try {
			Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
			if (!keys.isEmpty()) {
				redisTemplate.delete(keys);
			}
		} catch (Exception e) {
		}

	}

	@Override
	public int getSize() {
		Long size = (Long) redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});
		return size.intValue();
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

}
