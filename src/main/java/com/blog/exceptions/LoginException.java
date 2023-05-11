package com.blog.exceptions;

public class LoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginException(String message) {
		super(message);

	}

	public LoginException() {
		super();

	}

}
