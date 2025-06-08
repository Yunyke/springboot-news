package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.model.dto.UserDto;

public interface UserService {
	public UserDto getUser(String username);
	public void addUser(Integer id, String name, String username, String password, LocalDate birthdate, String gender, String email, Boolean active);
	
}

