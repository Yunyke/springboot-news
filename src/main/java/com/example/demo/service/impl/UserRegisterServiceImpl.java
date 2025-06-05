package com.example.demo.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.demo.service.UserRegisterService;
@Service
public class UserRegisterServiceImpl  {
	
	// 新增 User
	public void addUser(Integer id,
				 String name,
				 String username, 
				 String password, 
				 LocalDate birthdate,
				 String gender,
				 String email) {
		 System.out.println("User added: " + name);
	}
	
	
	
}