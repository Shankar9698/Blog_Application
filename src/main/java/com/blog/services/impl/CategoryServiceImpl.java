package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepository;
import com.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=this.dtoToCategory(categoryDto);
		Category createdCategory=this.categoryRepository.save(category);
		return categoryToDto(createdCategory);
	}

	@Override
	public List<CategoryDto> findAllCategory() {
		List<Category> categories=categoryRepository.findAll();
		return categories.stream().map(x->this.categoryToDto(x)).collect(Collectors.toList());
		
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
		this.categoryRepository.delete(category);
		
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
	
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		return categoryToDto(category);
	}

	@Override
	public CategoryDto findById(Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("categoryDto", "categoryId", categoryId));
		return categoryToDto(category);
	}
	public Category dtoToCategory(CategoryDto categoryDto) {
		Category category=modelMapper.map(categoryDto, Category.class);
		return category;
	}
	public CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto=modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
