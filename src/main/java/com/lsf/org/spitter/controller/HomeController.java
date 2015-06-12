package com.lsf.org.spitter.controller;

import java.util.Map;

import javax.inject.Inject;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lsf.org.spitter.serivce.SpitterService;

@Controller

public class HomeController {
	public static final int DEFAULT_SPITTLES_PER_PAGE = 25;
	@Autowired
	private SpitterService spitterService;

	@Inject
	public HomeController(SpitterService spitterService) {
		this.spitterService = spitterService;
	}

	@RequestMapping
	public String showHomePage(Map<String, Object> model) {
		System.out.println("i am here");
		model.put("message","hellworld");
		return "home";
	}
	
	public String add(Map<String,Object> model){
		System.out.println("add method");
		model.put("message","add method");
		return "add";
	}
	
}
