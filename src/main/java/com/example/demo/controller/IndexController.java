package com.example.demo.controller;


import com.example.demo.service.BBCRssService;
import com.example.demo.service.NHKRssService;
import com.example.demo.service.WSJRssService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
//  
//	private final BBCRssService bbcRssService;
//    private final NHKRssService nhkRssService;
//    private final WSJRssService wsjRssService;
//
//    public IndexController(BBCRssService bbcRssService, NHKRssService nhkRssService, WSJRssService wsjRssService) {
//        this.bbcRssService = bbcRssService;
//        this.nhkRssService = nhkRssService;
//        this.wsjRssService = wsjRssService;
//    }
//
//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("title", "Daily News");
//        model.addAttribute("newsList", bbcRssService.getBbcNews());
//        model.addAttribute("nhkNewsList", nhkRssService.getNhkNews());
//        model.addAttribute("wsjNewsList", wsjRssService.getReutersNews());
//        return "index"; // 不重導，直接回傳 index 頁面
//    }

}
