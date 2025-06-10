package com.example.demo.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;

import com.example.demo.service.UserService;
@Service
public class UserServiceImpl  implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDto getUser(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return null;
		}
		return userMapper.toDto(user);
	}

	@Override
	public void addUser(Integer id, String name, String username, String password, LocalDate birthdate, String gender, String email, Boolean active) {
		User user = new User();
		user.setName(name);
	    user.setUsername(username);
	    user.setPassword(password);
	    user.setBirthdate(birthdate);
	    user.setGender(gender);
	    user.setEmail(email);
	    user.setActive(active);
	   
		userRepository.save(user);
	}

	
}