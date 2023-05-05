package com.blog.services;

import java.util.List;

import com.blog.payloads.PostDto;

public interface PostService {
	//create
	//PostDto createPost(PostDto postDto);
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//view all
	List<PostDto> findAllPost();
	
	//view by id
	PostDto findPostById(Integer postId);
	
	//view by user
	List<PostDto> findPostByUser(Integer userId);
	
	//view by category
	List<PostDto> findPostByCategory(Integer categoryId);
	
	//search post
	List<PostDto> serchPosts(String keyword);

}
