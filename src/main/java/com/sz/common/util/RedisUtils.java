package com.sz.common.util;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.jboss.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	
	Logger logger=Logger.getLogger(RedisUtils.class);
	
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	

	/**
	 * 写入
	 * @param key
	 * @param value
	 * @param expire 过期时间(秒)，传null不过期
	 */
	public void set(final String key,final String value,final Integer expire){
	    redisTemplate.execute(new RedisCallback<Object>() {
	        @Override  
	        public Object doInRedis(RedisConnection connection) throws DataAccessException {
	        	byte[] keyByte=redisTemplate.getStringSerializer().serialize(key);
	        	byte[] valByte=redisTemplate.getStringSerializer().serialize(value);
	        	if(expire!=null){
	        		connection.setEx(keyByte,expire,valByte);
	        	}else{
	        		connection.set(keyByte,valByte);
	        	}
	            
	            return null;  
	        }  
	    });
		
	}
	
	/**
	 * 读取
	 * @param key
	 * @return
	 */
	public Serializable get(final String key){
		return redisTemplate.execute(new RedisCallback<Serializable>() {  
	        @Override  
	        public Serializable doInRedis(RedisConnection connection) throws DataAccessException {  
	            byte[] k = redisTemplate.getStringSerializer().serialize(key);  
	            if (connection.exists(k)) {  
	                byte[] value = connection.get(k);  
	                String val=redisTemplate.getStringSerializer().deserialize(value);
	                return val;
	            }  
	            return null;  
	        }  
	    });
	}
	
	
	
	
	/**
	 * 写入
	 * @param key
	 * @param value
	 * @param expire 过期时间(秒)，传null不过期
	 */
	public void write(String key,Serializable value,Long expire){
		ValueOperations<Serializable, Serializable> valueOperations=redisTemplate.opsForValue();
		if(expire!=null){
			valueOperations.set(key, value, expire, TimeUnit.SECONDS);
		}else{
			valueOperations.set(key, value);
		}
	}
	
	
	/**
	 * 读取
	 * @param key
	 * @return
	 */
	public Object read(String key){
		ValueOperations<Serializable, Serializable> valueOperations=redisTemplate.opsForValue();
		Serializable value=(Serializable)valueOperations.get(key);
		return value;
	}
	
	
	/**
	 * 删除指定key值
	 * @param key
	 */
	public void delete(String key) {
	    redisTemplate.delete(key);
	}
	
	/**
	 * 查询key，传*时获取全部key
	 * @param key
	 * @return
	 */
	public Set<Serializable> getKeys(String key){
		Set<Serializable> set=redisTemplate.keys(key);
		return set;
	}
	
	/**
	 * 删除所有
	 */
	public void deleteAll(){
		Set<Serializable> keyList=this.getKeys("*");
		redisTemplate.delete(keyList);
	}
	
	public static void main(String[] args) {
		try{
			ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-redis.xml"});
			RedisUtils s=(RedisUtils)context.getBean("redisUtils");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}
	
}
