package com.example.demo;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;


@SpringBootTest(classes = SpringbootNewsApplication.class)
public class UserJPATests {
	
	@Autowired
	private UserService userService;
	
	@Test void testUserAdd() {
		
		
		
		System.out.println("測試新增ok " );
	}
}


//	@Test void testFindAllUsers() {
//		System.out.println("測試查詢全部: " + userRepository.findAll());
//	}
//	
//	@Test void testGetOneUser() {
//		System.out.println("測試查詢單筆: " + userRepository.findById(1));
//		System.out.println("測試查詢單筆: " + userRepository.findById(2));
//	}
//	
//	@Test void updateUser() {
//		User uptUser = new User(2,  "Mary", "mary999", "999", LocalDate.of(1995,2,16), "Female", "mary999@gmail.com", true);
//		System.out.println("修改前: " + uptUser);
//		User user = userRepository.save(uptUser);
//		System.out.println("修改後: " + user);
//	}
//	
//	@Test void deleteUser() {
//		int userId = 1;
//		userRepository.deleteById(userId);
//		System.out.println("測試刪除: " + userId);
//	}
//	
//
