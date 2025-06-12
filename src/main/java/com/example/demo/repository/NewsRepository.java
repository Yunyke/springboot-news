package com.example.demo.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.News;
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByUrl(String url); 
    boolean existsByUrl(String url);
    List<News> findBySource(String source);
    List<News> findByPublishedAtBetween(ZonedDateTime start, ZonedDateTime end);
    List<News> findByTitleContaining(String keyword);
}