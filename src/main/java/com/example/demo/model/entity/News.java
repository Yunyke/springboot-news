package com.example.demo.model.entity;


import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 1000)
    private String description;
    @Column(length = 500,unique = true, nullable = false)
    private String url;
    private String imageUrl;
    private String source;
    private String author;
    @Column(columnDefinition = "TEXT")
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private ZonedDateTime publishedAt;
    
}