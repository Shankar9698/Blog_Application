package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter 
@Getter 
@NoArgsConstructor 
@AllArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	@NotBlank
	@Size(min=4, message="Title should consist of atleast 4 characters...")
	private String categoryTitle;
	@NotBlank
	@Size(min=10, message="Description should consists of atleast 10 characters...")
	private String categoryDescription;

}
