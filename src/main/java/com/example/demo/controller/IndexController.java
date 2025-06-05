//package com.example.demo.controller;
//
//
//import com.example.demo.service.BBCRssService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class IndexController {
//
//    @Autowired
//    private BBCRssService bbcRssService;
//
//    @GetMapping("/news")
//    public String showBbcNews(Model model) {
//        model.addAttribute("title", "News");
//        model.addAttribute("newsList", bbcRssService.getBbcNews());
//        return "index"; // 對應 /templates/bbc.html
//    }
//}
