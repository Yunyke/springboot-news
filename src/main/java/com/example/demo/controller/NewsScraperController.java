package com.example.demo.controller;

import com.example.demo.model.dto.CnnNews;
import com.example.demo.model.dto.UserCert;
import com.example.demo.repository.NewsRepository;
import com.example.demo.service.BBCRssService;
import com.example.demo.service.CnnCrawlerService;
import com.example.demo.service.NHKRssService;
import com.example.demo.service.NewsService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 處理 HTTP 請求。當使用者打開網站 /，會由這支 class 來處理。
@Slf4j // Lombok 自動幫你加上 log 變數
public class NewsScraperController {

	

	
	
	private final NewsService newsService; 
	private final NewsRepository newsRepository;


	// 建構子注入，把務注入進來給這個 Controller 使用。
	public NewsScraperController(NewsService newsService,
            NewsRepository newsRepository) {
			this.newsService   = newsService;
			this.newsRepository = newsRepository;
}

	// 處理「打開首頁 / 或 /news」時的請求，主要負責：
	// 1.加載各新聞來源的資料
	// 2.裝進 model 裡供前端（Thymeleaf）使用
	// 3.處理登入使用者的顯示邏輯
	@GetMapping({ "/", "/news" })
	public String showNews(Model model, HttpSession session) {

		model.addAttribute("title", "Daily News");

		/* 1) 先確定三家新聞都抓好、寫進 DB ------------------------- */
        newsService.fetchAndSaveAllNews();   // CNN / BBC / NHK
        
        /* 2) 依 source 從同一張表撈出來 --------------------------- */
        model.addAttribute("cnnNewsList", newsRepository.findBySourceOrderByPublishedAtDesc("CNN"));
        model.addAttribute("bbcNewsList", newsRepository.findBySourceOrderByPublishedAtDesc("BBC"));
        model.addAttribute("nhkNewsList", newsRepository.findBySourceOrderByPublishedAtDesc("NHK"));
    	
		return "index"; // 回傳 Thymeleaf 或其他 template 名稱
	}
}
