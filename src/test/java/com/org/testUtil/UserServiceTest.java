package com.org.testUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lsf.org.spitter.domain.User;
import com.lsf.org.spitter.serivce.UserService;

public class UserServiceTest {
	@Autowired
	private ApplicationContext app;  
	@Autowired
    private UserService userService; 
@Before
 public void before(){
	 ApplicationContext ac =  new ClassPathXmlApplicationContext("redis-config.xml");
	 UserService userService = (UserService)ac.getBean("userService");
 }
	@Test
	public void test() {
		  // -------------- Create ---------------  
        String uid = "u123456";  
        String address1 = "上海";  
        User user = new User();  
        user.setName(address1);  
        user.setId(uid);  
        userService.save(user);  
  
        // ---------------Read ---------------  
        user = userService.read(uid);  
  
        assertEquals(address1, user.getName());  
  
        // --------------Update ------------  
        String address2 = "北京";  
        user.setName(address2);  
        userService.save(user);  
  
        user = userService.read(uid);  
  
        assertEquals(address2, user.getName());  
  
        // --------------Delete ------------  
        userService.delete(uid);  
        user = userService.read(uid);  
        assertNull(user); 
	}

}
