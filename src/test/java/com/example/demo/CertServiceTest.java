package com.example.demo;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.excetion.CertExcetion;
import com.example.demo.model.dto.UserCert;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CertService;
import com.example.demo.service.UserService;


@SpringBootTest(classes = SpringbootNewsApplication.class)
public class CertServiceTest {
	
	@Autowired
	private CertService certService;
	
	@Test 
	void testUserAdd() {
		try {
			UserCert cert = certService.getCert("john1234", "1234");
			System.out.println("測試成功： 資料驗證通過");
		} catch (CertExcetion e) {
			e.printStackTrace();
		}
		
		
	}
}


