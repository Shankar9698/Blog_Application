package com.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String key;
	Integer value;
	public ResourceNotFoundException(String name, String key, Integer userId) {
		super(String.format("%s not found with %s : %s", name,key,userId));
		this.name = name;
		this.key = key;
		this.value = userId;
	}
	

}
