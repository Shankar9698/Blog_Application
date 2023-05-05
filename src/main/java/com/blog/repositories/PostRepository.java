package com.blog.repositories;

import java.util.List;
import com.blog.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	  List<Post> findByUser(User user); 
	  List<Post> findByCategory(Category category);
	 // List<Post> searchByTitleContaining(String title); different way
	  @Query("select p from Post p where p.title like :key")
	  List<Post> searchByTitleContaining(@Param("key") String title);
	 
}
