package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.Comment;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	@Autowired
	CommentService commentService;

	@PostMapping("/create/{postId}/{userId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable("postId") Integer postId, @PathVariable("userId") Integer userId) {
		CommentDto commentDto2 = this.commentService.createComment(commentDto, postId, userId);
		return new ResponseEntity<CommentDto>(commentDto2, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId) {

		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Comment>> getCommentByUser(@PathVariable("userId") Integer userId) {
		List<Comment> comment = this.commentService.getCommentByUser(userId);
		return ResponseEntity.ok(comment);
	}

}
