package com.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.payloads.CommentDto;
import com.blog.payloads.UserDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "posts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	@Column(name = "posts_title", length = 100, nullable = false)
	private String title;
	@Column(length = 100)
	private String content;
	private String imageName;
	private Date addedDate;
	@JsonIgnore
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	@JsonIgnore
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL) 
	  private Set<Comment> comments=new HashSet<>();
	 
}
