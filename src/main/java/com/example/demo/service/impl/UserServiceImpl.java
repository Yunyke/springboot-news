package com.example.demo.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.News;
import com.example.demo.model.entity.User;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return userMapper.toDto(user);
    }

    @Override
    public void addUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
    }

    // ⭐ 新增：收藏新聞
    @Override
    public void addFavorite(Integer userId, Long newsId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("找不到使用者"));

        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("找不到新聞"));

        if (user.getFavoriteNews() == null) {
            user.setFavoriteNews(new HashSet<>());
        }

        user.getFavoriteNews().add(news);
        userRepository.save(user);
    }

    // ⭐ 新增：移除收藏
    @Override
    public void removeFavorite(Integer userId, Long newsId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("找不到使用者"));

        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("找不到新聞"));

        if (user.getFavoriteNews() != null) {
            user.getFavoriteNews().remove(news);
        }

        userRepository.save(user);
    }

    // ⭐ 新增：取得收藏
    @Override
    public Set<News> getFavorites(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("找不到使用者"));
        return user.getFavoriteNews();
    }
}
