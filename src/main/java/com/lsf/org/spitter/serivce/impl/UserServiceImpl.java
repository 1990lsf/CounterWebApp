package com.lsf.org.spitter.serivce.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Service;

import com.lsf.org.spitter.domain.User;
import com.lsf.org.spitter.serivce.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	private ValueOperations<String, User> operations;
	@PostConstruct
	public void init(){
		redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(User.class));
		operations=redisTemplate.opsForValue();
	}
	public void set(String key,User value){
		operations.set(key, value);
	}
	public User get(String key){
		return operations.get(key);
	}
	@Override
	public void save(final User user) {
		redisTemplate.execute(new RedisCallback<Object>(){
			public Object doInRedis(RedisConnection connection)throws DataAccessException{
				connection.set(redisTemplate.getStringSerializer().serialize("user.id."+user.getId()),redisTemplate.getStringSerializer().serialize(user.getName()));
				return null;
			}
			
		});
	}

	@Override
	public User read(final String id) {
		return redisTemplate.execute(new RedisCallback<User>() {  
	        @Override  
	        public User doInRedis(RedisConnection connection)  
	                throws DataAccessException {  
	            byte[] key = redisTemplate.getStringSerializer().serialize("user.uid." + id);  
	            if (connection.exists(key)) {  
	                byte[] value = connection.get(key);  
	                String name = redisTemplate.getStringSerializer().deserialize(value);  
	                User user = new User();  
	                user.setName(name);  
	                user.setId(id);  
	                return user;  
	            }  
	            return null;  
	        }  
	    });
	}

	@Override
	public void delete(final String id) {
		redisTemplate.execute(new RedisCallback<Object>() {  
	        public Object doInRedis(RedisConnection connection) {  
	            connection.del(redisTemplate.getStringSerializer().serialize("user.uid." + id));  
	            return null;  
	        }  
	    });

	}
	public static void main(String[] args) {
		ApplicationContext ac =  new ClassPathXmlApplicationContext("redis-config.xml");
        UserService userService = (UserService)ac.getBean("userService");
        User user1 = new User();
        user1.setId("1");
        user1.setName("obama");
        userService.save(user1);
        User user2 = userService.read("1");
        System.out.println(user2.getName());
    }
}
