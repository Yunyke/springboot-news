package com.example.demo.controller;

import com.example.demo.model.dto.CnnNews;
import com.example.demo.model.dto.UserCert;
import com.example.demo.service.BBCRssService;
import com.example.demo.service.CnnCrawlerService;
import com.example.demo.service.NHKRssService;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 處理 HTTP 請求。當使用者打開網站 /，會由這支 class 來處理。
@Slf4j // Lombok 自動幫你加上 log 變數
public class NewsScraperController {

	private static final String CNN_URL = "https://edition.cnn.com/2025/06/08/middleeast/freedom-flotilla-gaza-aid-ship-thunberg-intl-hnk";

	private final BBCRssService bbcRssService;
	private final NHKRssService nhkRssService;
	
	private final CnnCrawlerService cnnCrawlerService;


	// 建構子注入，把務注入進來給這個 Controller 使用。
	public NewsScraperController(BBCRssService bbcRssService, NHKRssService nhkRssService,
			CnnCrawlerService cnnCrawlerService) {
		this.bbcRssService = bbcRssService;
		this.nhkRssService = nhkRssService;
		
		this.cnnCrawlerService = cnnCrawlerService;
	}

	// 處理「打開首頁 / 或 /news」時的請求，主要負責：
	// 1.加載各新聞來源的資料
	// 2.裝進 model 裡供前端（Thymeleaf）使用
	// 3.處理登入使用者的顯示邏輯
	@GetMapping({ "/", "/news" })
	public String showNews(Model model, HttpSession session) {

		model.addAttribute("title", "Daily News");

		// BBC / NHK / WSJ 都是 RSS Service 拿資料出來塞到 model 裡。這樣前端頁面就能用 th:each 等語法去讀取並渲染。
		model.addAttribute("bbcNewsList", bbcRssService.getBbcNews());
		model.addAttribute("nhkNewsList", nhkRssService.getNhkNews());
		
		List<CnnNews> newsList = cnnCrawlerService.getCnnNews();		
    	model.addAttribute("newsList", newsList);
    	
		return "index"; // 回傳 Thymeleaf 或其他 template 名稱
	}
}
