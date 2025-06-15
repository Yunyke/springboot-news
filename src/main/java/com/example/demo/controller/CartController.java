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

        List<News> newsList = newsService.getNewsByIds(newsIds);
        System.out.println("🟢 撈回來的新聞筆數：" + newsList.size());
        for (News news : newsList) {
            System.out.println("📰 " + news.getId() + ": " + news.getTitle());
        }

        model.addAttribute("cartNews", newsList);
        return "cart :: cartFragment";
    }

   
    
}
