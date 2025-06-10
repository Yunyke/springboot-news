package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;

public interface RegisterService {
	UserDto findByUsername(String username);

    UserDto registerUser(UserDto userDto);
}