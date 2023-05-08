package com.blog.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.payloads.PostDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	CommentRepository commentRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment comment2 = this.commentRepository.save(comment);
		return this.modelMapper.map(comment2, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		this.commentRepository.delete(comment);
	}

	@Override
	public List<Comment> getCommentByUser(Integer userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		List<Comment> comment=this.commentRepository.getCommentByUser(user);
		return comment;
	}

}
