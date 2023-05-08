package com.blog.entities;

import java.util.ArrayList;
import java.util.List;

import com.blog.payloads.PostDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	@Column(name = "title", nullable = false)
	private String categoryTitle;
	@Column(name = "description", nullable = false)
	private String categoryDescription;

	/*
	 * @JsonManagedReference
	 * 
	 * @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch =
	 * FetchType.LAZY) private List<Post> post = new ArrayList<>();
	 */
}
