package com.lsf.org.spitter.controller;

public class ImageUploadException extends RuntimeException {
/**
	 * 
	 */
	private static final long serialVersionUID = 9199733652326906243L;
public ImageUploadException(String message){
	super(message);
}
public ImageUploadException(String message,Exception e ){
	super(message,e);
}
}
