package com.lsf.org.spitter.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lsf.org.spitter.domain.User;
import com.lsf.org.spitter.serivce.UserService;
@Controller

public class UserController {
	@Autowired
	private final UserService userService;
	 @Inject
	public UserController(UserService userService){
		this.userService=userService;
	}
	  @RequestMapping(value="/redis/save",method=RequestMethod.GET)
	public void saveToReids(){
		User user = new User();
        user.setId("2");
        user.setName("obama");
        String keyString="user:2";
        userService.set(keyString,user);
//		userService.save(user);
	}
	  @RequestMapping(value="/redis/read",method=RequestMethod.GET)
	  public void getFromRedis(){
		  User user=new User();
		  user=userService.read("1");
		  System.out.println(user.getId()+"===="+user.getName());
	  }
	  
	  @RequestMapping("/redis/delete")
	  public void deleteFromRedis(){
		  userService.delete("1");
		  
	  }
}
