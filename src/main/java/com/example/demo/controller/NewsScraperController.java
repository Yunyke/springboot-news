package com.example.demo.controller;

import com.example.demo.model.dto.CnnNews;
import com.example.demo.model.dto.UserCert;
import com.example.demo.service.BBCRssService;
import com.example.demo.service.CnnCrawlerService;
import com.example.demo.service.NHKRssService;
import com.example.demo.service.WSJRssService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class NewsScraperController {

    private static final String CNN_URL = "https://edition.cnn.com/2025/06/08/middleeast/freedom-flotilla-gaza-aid-ship-thunberg-intl-hnk";

    private final BBCRssService bbcRssService;
    private final NHKRssService nhkRssService;
    private final WSJRssService wsjRssService;
    private final CnnCrawlerService cnnCrawlerService;

    public NewsScraperController(
        BBCRssService bbcRssService,
        NHKRssService nhkRssService,
        WSJRssService wsjRssService,
        CnnCrawlerService cnnCrawlerService
    ) {
        this.bbcRssService = bbcRssService;
        this.nhkRssService = nhkRssService;
        this.wsjRssService = wsjRssService;
        this.cnnCrawlerService = cnnCrawlerService;
    }

    @GetMapping("/news")
    public String showNews(Model model, HttpSession session) {
        model.addAttribute("title", "Daily News");

        // BBC / NHK / WSJ 都是 RSS 的方式
        model.addAttribute("newsList", bbcRssService.getBbcNews());
        model.addAttribute("nhkNewsList", nhkRssService.getNhkNews());
        model.addAttribute("wsjNewsList", wsjRssService.getReutersNews());

        // CNN 是爬蟲方式，需要特別 try-catch
        try {
            CnnNews cnnNews = cnnCrawlerService.crawl(CNN_URL);
            model.addAttribute("cnnNews", cnnNews);
        } catch (Exception e) {
            log.warn("CNN 爬蟲失敗：{}", e.getMessage());
            model.addAttribute("cnnNewsError", "無法載入 CNN 新聞：" + e.getMessage());
        }

        // 若 session 有登入資訊，顯示使用者名稱
        UserCert userCert = (UserCert) session.getAttribute("userCert");
        if (userCert != null) {
            model.addAttribute("username", userCert.getUsername());
        }

        return "index"; // 回傳 Thymeleaf 或其他 template 名稱
    }
}
