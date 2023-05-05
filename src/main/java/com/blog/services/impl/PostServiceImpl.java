package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.UserDto;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.CategoryService;
import com.blog.services.PostService;
import com.blog.services.UserService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;

//few changes are there , iam getting databind exception so i took service instead of repository
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepository.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
		Post post2 = this.modelMapper.map(post, Post.class);
		post2.setImageName(postDto.getImageName());
		post2.setTitle(postDto.getTitle());
		post2.setContent(postDto.getContent());
		Post post3 = this.postRepository.save(post2);
		return this.modelMapper.map(post3, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		this.postRepository.delete(post);
	}

	@Override
	public List<PostDto> findAllPost() {
		List<Post> postList = this.postRepository.findAll();
		return postList.stream().map(x -> this.postToDto(x)).collect(Collectors.toList());
	}

	@Override
	public PostDto findPostById(Integer postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> findPostByUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		List<Post> postList = this.postRepository.findByUser(user);
		List<PostDto> postDtos = postList.stream().map(x -> this.modelMapper.map(x, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> findPostByCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		List<Post> postList = this.postRepository.findByCategory(category);
		List<PostDto> postDtoList = postList.stream().map(x -> this.modelMapper.map(x, PostDto.class))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> serchPosts(String keyword) {

		return null;
	}

	public Post dtoToPost(PostDto postDto) {
		Post post = this.modelMapper.map(postDto, Post.class);
		return post;
	}

	public PostDto postToDto(Post post) {
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

}
