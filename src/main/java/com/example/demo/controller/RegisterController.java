package com.example.demo.controller;

import com.example.demo.model.dto.UserCert;
import com.example.demo.model.dto.UserDto;
import com.example.demo.service.RegisterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    // 顯示註冊表單頁面
    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register"; // 對應 /WEB-INF/jsp/register.jsp
    }

    @PostMapping
    public String registerUser(
    		@ModelAttribute("userDto") UserDto userDto,
            Model model,
            HttpServletRequest request
    ) {
        try {
            registerService.registerUser(userDto);

            UserCert userCert = new UserCert();
            userCert.setUsername(userDto.getUsername());
            
            // 若註冊成功，將使用者資訊存入 session
            HttpSession session = request.getSession();
            session.setAttribute("userCert", userCert);
            session.setAttribute("locale", request.getLocale());

            return "redirect:/news";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
