package com.example.demo.model.entity;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;
	private String name;
	private String username;
	private String password;
	private LocalDate birthdate;
	private String gender;
	private String email;
	private Boolean active;
	
	@ManyToMany
	@JoinTable(
	    name = "user_favorite_news",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "news_id")
	)
	
	private Set<News> favoriteNews = new HashSet<>();
}