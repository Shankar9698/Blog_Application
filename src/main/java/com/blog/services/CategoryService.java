package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//readAll
	List<CategoryDto> findAllCategory();
	
	//delete
	void deleteCategory(Integer categoryId);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//readbyid
	CategoryDto findById(Integer categoryId);

}
