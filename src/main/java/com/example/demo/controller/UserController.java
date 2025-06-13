package com.example.demo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entity.News;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private NewsService newsService;
    
    @PostMapping("/cart/load")
    public String loadCart(@RequestBody List<Long> newsIds, Model model) {
        List<News> newsList = newsService.getNewsByIds(newsIds);
        model.addAttribute("cartNews", newsList);
        return "cart :: cartFragment"; // 可用 Thymeleaf fragment render
    }

    @PostMapping("/{userId}/favorites/{newsId}")
    public ResponseEntity<String> addFavorite(
            @PathVariable Integer userId,
            @PathVariable Long newsId) {
        userService.addFavorite(userId, newsId);
        return ResponseEntity.ok("已加入收藏");
    }

    @DeleteMapping("/{userId}/favorites/{newsId}")
    public ResponseEntity<String> removeFavorite(
            @PathVariable Integer userId,
            @PathVariable Long newsId) {
        userService.removeFavorite(userId, newsId);
        return ResponseEntity.ok("已移除收藏");
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<Set<News>> getFavorites(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getFavorites(userId));
    }
}