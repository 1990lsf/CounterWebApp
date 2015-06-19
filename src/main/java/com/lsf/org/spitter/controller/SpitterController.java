package com.lsf.org.spitter.controller;

import javax.annotation.Resource;

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

	@RequestMapping(value = "/spittles", method = RequestMethod.GET)
	public String listSpittlesForSpitter(
			@RequestParam("spitter") String username, Model model) {
		System.out.println("this is spitterController " + username);
		Spitter spitter = spitterService.getSpitter(username);
		model.addAttribute("username",username);
		model.addAttribute(spitter);
		model.addAttribute(spitterService.getSpitterlesForSpitter(username));
		return "spittles/list";

	}
}
