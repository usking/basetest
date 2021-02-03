package com.sz.test;

import java.util.Collections;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

@Component
public class RedisLockUtils {
	
	@Resource(name="redisTemplate2")
	private RedisTemplate<String, String> redisTemplate;
	
	String script = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then return redis.call('EXPIRE', KEYS[1], ARGV[2]) else return 0 end";
	// 指定 lua 脚本，并且指定返回值类型，使用lua脚本写法保证原子性
    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

	public boolean tryLock(String key, String value, long timeout) {
	    // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
	    Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), value, String.valueOf(timeout));
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void lock(String key, String value, long timeout) {
		while(!tryLock(key, value, timeout)) {
			
		}
	}
	
	public void unlock(String key) {
		redisTemplate.delete(key);
	}
}
