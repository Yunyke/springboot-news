package com.example.demo.controller;

import com.example.demo.model.entity.News;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private NewsService newsService;

   
    @GetMapping
    public String cartPage() {
        return "cart";  
    }

    
   
    @PostMapping("/load")
    public String loadCart(@RequestBody List<Long> newsIds, Model model) {
        System.out.println("🟡 收到的 ID 清單：" + newsIds);

        // 撈出符合 ID 的新聞
        List<News> newsList = newsService.getNewsByIds(newsIds);
        System.out.println("🟢 撈回來的新聞筆數：" + newsList.size());

        // 🔽 新增：過濾出實際有撈到的 ID，並印出沒找到的 ID
        List<Long> foundIds = newsList.stream().map(News::getId).toList();
        List<Long> notFoundIds = newsIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        for (News news : newsList) {
            System.out.println("📰 " + news.getId() + ": " + news.getTitle());
        }

        if (!notFoundIds.isEmpty()) {
            System.out.println("⚠️ 以下 ID 找不到對應新聞：" + notFoundIds);
        }
        
        model.addAttribute("cartNews", newsList);
        model.addAttribute("foundIds", foundIds); 
        return "cart :: cartFragment";
    }
}