package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

import model.User;

public class UserRegisterService {

	private  UserRepository userRepository;

    @Autowired
    public UserRegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User newUser) {
        userRepository.save(newUser);
    }
}