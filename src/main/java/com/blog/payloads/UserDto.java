package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private int id;
	@NotEmpty()
	@Size(min=4,message="name should consists of min 4 letters")
	private String name;
	@Email(message="invalid email format")
	private String email;
	@NotEmpty
	@Size(min=3, max=10, message="password should contains min 3 character and max 10 character")
	private String password;
	@NotEmpty
	private String about;


}
