package com.blog.services;

import java.util.List;

import com.blog.entities.Comment;
import com.blog.entities.User;
import com.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

	void deleteComment(Integer commentId);
	
	List<Comment> getCommentByUser(Integer userId);

}
