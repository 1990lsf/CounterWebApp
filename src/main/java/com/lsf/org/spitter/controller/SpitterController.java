package com.lsf.org.spitter.controller;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lsf.org.spitter.domain.Spitter;
import com.lsf.org.spitter.serivce.SpitterService;

@Controller
@RequestMapping("/spitter")
public class SpitterController {
	@Resource
	public SpitterService spitterService;

	@Inject
	public SpitterController(SpitterService spitterService) {
		this.spitterService = spitterService;
	}

	@RequestMapping(value = "/spittles", method = RequestMethod.GET)
	public String listSpittlesForSpitter(
			@RequestParam("spitter") String username, Model model) {

		Spitter spitter = spitterService.getSpitter(username);
		model.addAttribute("username", username);
		model.addAttribute(spitter);
		model.addAttribute(spitterService.getSpitterlesForSpitter(username));
		return "spittles/list";

	}

	@RequestMapping(method = RequestMethod.GET, params = "new")
	// 只处理对/spitter的HTTP GET 请求并要求请求中必须包含名为“new”的查询参数.
	public String createSpiiterProfile(Model model) {
		model.addAttribute(new Spitter());
		return "spitters/edit";
	}
	/**
	 * 保存
	 * @param spitter
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String addSpitterFormForm(@Valid Spitter spitter,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "spitters/edit";
		}
		spitterService.saveSpitter(spitter);
		//前缀redirect s说明请求将被重定向到指定路径，如果用户点击了浏览器“刷新”按钮，通过重定向到另外一个页面我们能够避免表单重复提交
		return "redirect:/spitters/"+spitter.getUsername();
	}
	
	@RequestMapping(value="/{username}",method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable String username,Model model){
		model.addAttribute(spitterService.getSpitter(username));
		return "spitters/view";
	}
	
}
