package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.excetion.CertExcetion;
import com.example.demo.model.dto.UserCert;

public interface CertService {
	
    UserCert getCert(String username, String password) throws CertExcetion;
    
}

