package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Comment;
import com.blog.entities.User;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	List<Comment> getCommentByUser(User user);
}
