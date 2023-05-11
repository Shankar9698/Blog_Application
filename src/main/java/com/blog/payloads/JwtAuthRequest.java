package com.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
public class JwtAuthRequest {
	private String userName;
	private String password;

}
