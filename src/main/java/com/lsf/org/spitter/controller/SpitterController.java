package com.lsf.org.spitter.controller;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.jets3t.service.S3Service;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lsf.org.spitter.domain.Spitter;
import com.lsf.org.spitter.serivce.SpitterService;

@Controller
@RequestMapping("/spitter")
public class SpitterController {
	@Resource
	public SpitterService spitterService;
	private final String s3AccessKey="1";
	private final String s3SecretKey="2";
	private String webRootPath;
	public void setWebRootPath(String webRootPath){
		this.webRootPath=webRootPath;
	}
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
	public String addSpitterFormForm(@Valid Spitter spitter,BindingResult bindingResult,@RequestParam(value="image",required=false) MultipartFile image){
		if(bindingResult.hasErrors()){
			return "spitters/edit";
		}
		spitterService.saveSpitter(spitter);
		
		
		try {
			if(image.isEmpty()){
				validateImage(image);//校验图片
				saveImage(spitter.getId()+".jpf",image);//保存图片
			}
		} catch (Exception e) {
			bindingResult.reject(e.getMessage());
			return "spitters/edit";
		}
		
		//前缀redirect s说明请求将被重定向到指定路径，如果用户点击了浏览器“刷新”按钮，通过重定向到另外一个页面我们能够避免表单重复提交
		return "redirect:/spitters/"+spitter.getUsername();
	}
	
	@RequestMapping(value="/{username}",method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable String username,Model model){
		model.addAttribute(spitterService.getSpitter(username));
		return "spitters/view";
	}
	
	private void validateImage(MultipartFile image){
		if(!image.getContentType().equals("image/jpeg")){
			throw new ImageUploadException("only jpg images accepted");
		}
	}
	//保存到本地服务上
	private void saveImage(String filename,MultipartFile image){
		try{
			File file = new File(webRootPath+"/common/"+filename);
			FileUtils.writeByteArrayToFile(file,image.getBytes());
		}catch(Exception e){
			throw new ImageUploadException("unable to upload image",e);
		}
	}
	
	private void saveIamgeToS3(String filename,MultipartFile image)throws ImageUploadException{
		try{
			AWSCredentials awsCredentials = new AWSCredentials(s3AccessKey ,s3SecretKey);
		S3Service s3 = new RestS3Service(awsCredentials);
		//创建S3 bucket 和object
		S3Bucket imageBucket = s3.createBucket("spitterImages");
		S3Object imageObject = new S3Object(filename);
		//设置图片数据
		imageObject.setDataInputStream(new ByteArrayInputStream(image.getBytes()));
		imageObject.setContentLength(image.getBytes().length);
		imageObject.setContentType("image/jpeg");
		//设置权限
		AccessControlList acl=new AccessControlList();
		acl.setOwner(imageBucket.getOwner());
		acl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
		imageObject.setAcl(acl);
		//保存图片
		s3.putObject(imageBucket, imageObject);
		
		}catch(Exception e){
			throw new ImageUploadException("unable to save image",e);
		}
	}
}
