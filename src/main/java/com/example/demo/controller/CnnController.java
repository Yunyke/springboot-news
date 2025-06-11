package com.example.demo.controller;

import com.example.demo.model.dto.CnnNews;
import com.example.demo.service.CnnCrawlerService;

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

    @GetMapping("/cnn")
    public String getCnnNews(Model model) {
        String rawJson = cnnCrawlerService.getCnnNews();  // 從 service 抓原始 JSON
        JSONObject raw = new JSONObject(rawJson);         // 解析 JSON
        String pretty = raw.toString(2);                  // 格式化成漂亮 JSON

        model.addAttribute("newsData", pretty);           // 傳給前端
        return "cnn";
    }
}