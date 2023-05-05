package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts/")
public class PostControllers {
	@Autowired
	private PostService postService;

	@PostMapping("/user/{userId}/category/{categoryId}")

	public ResponseEntity<PostDto> createPosts(@Valid @RequestBody PostDto postDto,
			@PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId) {

		PostDto post = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<List<PostDto>> getPostByuser(@PathVariable("userId") Integer userId) {
		List<PostDto> postDtos = this.postService.findPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {
		List<PostDto> postDtos = this.postService.findPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);

	}

	@GetMapping("/all")
	public ResponseEntity<List<PostDto>> getAllPost() {
		List<PostDto> postList = this.postService.findAllPost();
		return new ResponseEntity<List<PostDto>>(postList, HttpStatus.OK);
	}

	@GetMapping("/get/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
		PostDto postDto = this.postService.findPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,
			@PathVariable("postId") Integer postId) {
		PostDto postDto2 = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(postDto2);

	}

	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully", true), HttpStatus.OK);
	}

}
