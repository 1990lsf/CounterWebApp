package com.lsf.org.spitter.controller;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lsf.org.spitter.domain.Spitter;
import com.lsf.org.spitter.serivce.SpitterService;

@Controller
@RequestMapping("/spitter")
public class SpitterController {
	@Resource
	public  SpitterService spitterService;
@Inject
public SpitterController(SpitterService spitterService){
	this.spitterService=spitterService;
}
	@RequestMapping(value = "/spittles", method = RequestMethod.GET)
	public String listSpittlesForSpitter(
			@RequestParam("spitter") String username, Model model) {
	
		Spitter spitter = spitterService.getSpitter(username);
		model.addAttribute("username",username);
		model.addAttribute(spitter);
		model.addAttribute(spitterService.getSpitterlesForSpitter(username));
		return "spittles/list";

	}
	
	@RequestMapping(method=RequestMethod.GET,params="new")
	//只处理对/spitter的HTTP GET 请求并要求请求中必须包含名为“new”的查询参数.
	public String createSpiiterProfile(Model model){
		model.addAttribute(new Spitter());
		return "spitters/edit";
	}
}
