package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.FileResponse;
import com.blog.payloads.PageResponse;
import com.blog.payloads.PostDto;
import com.blog.services.FileService;
import com.blog.services.PostService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts/")
public class PostControllers {
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;

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
	public ResponseEntity<PageResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		PageResponse postList = this.postService.findAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageResponse>(postList, HttpStatus.OK);
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

	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchByKeyword(@PathVariable("keyword") String keyword) {
		List<PostDto> postList = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(postList, HttpStatus.OK);

	}

	@PostMapping("/images/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam MultipartFile image,
			@PathVariable("postId") Integer postId) throws IOException {
		PostDto postDto = this.postService.findPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);

		postDto.setImageName(fileName);
		PostDto postDto2 = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(postDto2, HttpStatus.OK);
	}

	@GetMapping(value = "/getimage/{image}", produces = MediaType.IMAGE_PNG_VALUE)
	public void getImage(@PathVariable("image") String image, HttpServletResponse httpServletResponse)
			throws IOException {
		InputStream fileName = this.fileService.getResources(path, image);
		httpServletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(fileName, httpServletResponse.getOutputStream());
	}

}
