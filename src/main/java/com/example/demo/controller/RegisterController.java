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
             // ✅ 呼叫註冊服務
             registerService.registerUser(userDto);

             // ✅ 建立登入憑證並儲存使用者資訊
             UserCert userCert = new UserCert();
             userCert.setUsername(userDto.getUsername());
             userCert.setName(userDto.getName()); // ✅ 新增：將 name 放入 UserCert 供顯示歡迎用

             // ✅ 將使用者資訊存入 Session
             HttpSession session = request.getSession();
             session.setAttribute("userCert", userCert);
             session.setAttribute("name", userDto.getName()); // ✅ 新增：單獨設置 name 屬性給畫面用
             session.setAttribute("locale", request.getLocale());

             // ✅ 導向首頁
             return "redirect:/news";
         } catch (Exception e) {
             model.addAttribute("error", e.getMessage());
             return "register";
         }
        }
    
}
