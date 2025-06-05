package com.example.demo.controller;

import com.example.demo.service.BBCRssService;
import com.example.demo.service.NHKRssService;
import com.example.demo.service.WSJRssService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.BBCRssService;
@Controller
public class NewsScraperController {

    private final BBCRssService bbcRssService;
    private final NHKRssService nhkRssService; 
    private final WSJRssService wsjRssService; 

    public NewsScraperController(BBCRssService bbcRssService,NHKRssService nhkRssService,WSJRssService wsjRssService) {
        this.bbcRssService = bbcRssService;
        this.nhkRssService = nhkRssService;
        this.wsjRssService = wsjRssService;
    }
    
    @GetMapping("/")
    public String redirectToNews() {
        return "redirect:/news";
    }

    @GetMapping("/news")
    public String showBbcNews(Model model) {
        model.addAttribute("title", "Daily News");
        model.addAttribute("newsList", bbcRssService.getBbcNews());
        model.addAttribute("nhkNewsList", nhkRssService.getNhkNews());
        model.addAttribute("wsjNewsList", wsjRssService.getReutersNews());
        return "index";
    }
}
