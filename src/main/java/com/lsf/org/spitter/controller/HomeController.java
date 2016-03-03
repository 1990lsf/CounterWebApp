package com.lsf.org.spitter.controller;

import java.util.Map;

import javax.inject.Inject;

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
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Map<String,Object> model){
		System.out.println("add method");
		model.put("message","add method");
		return "add";
	}
	
	@RequestMapping(value="/addTest",method=RequestMethod.GET)
	public String addTest(Map<String,Object> model){
		System.out.println("add test  method");
		model.put("message","add test method");
		return "addTest";
	}
	@RequestMapping(value="/showhome",method=RequestMethod.GET)
	public String showHome(Map<String,Object> model){
		System.out.println("show home  method");
		model.put("message","show home method");
		return "home";
	}
	
}
