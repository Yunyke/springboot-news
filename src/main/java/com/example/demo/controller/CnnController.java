package com.example.demo.controller;

import com.example.demo.model.dto.CnnNews;
import com.example.demo.model.entity.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.service.BBCRssService;
import com.example.demo.service.CnnCrawlerService;
import com.example.demo.service.NHKRssService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CnnController {  // ✅ 類名修正a


    @Autowired
    private CnnCrawlerService cnnCrawlerService;
    @Autowired
    private final NewsRepository newsRepository;
    
    public CnnController(NewsRepository newsRepository) {
		
		this.newsRepository = newsRepository;
	}

    @GetMapping("/cnn")
    public String getCnnNews(Model model) {
    	cnnCrawlerService.fetchAndSaveIfNotExist(); // 先確保抓並存
    	List<News> cnnNewsList = newsRepository.findBySource("CNN");
    	model.addAttribute("newsList", cnnNewsList);
        return "cnn";
    }
}