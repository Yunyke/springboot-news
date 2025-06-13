package com.example.demo.service;

import com.example.demo.model.entity.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.model.dto.CnnNews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CnnCrawlerService cnnCrawlerService;

    @Autowired
    private BBCRssService bbcRssService;

    @Autowired
    private NHKRssService nhkRssService;

    public void fetchAndSaveAllNews() {
        saveCnnNews();
        saveBbcNews();
        saveNhkNews();
    }

    private void saveCnnNews() {
        List<CnnNews> cnnNewsList = cnnCrawlerService.getCnnNews();
        System.out.println("CNN News fetched: " + cnnNewsList.size());

        for (CnnNews cnn : cnnNewsList) {
            if (!newsRepository.findByUrl(cnn.getUrl()).isPresent()) {
                News news = new News();
                news.setTitle(cnn.getTitle());
                news.setDescription(cnn.getDescription());
                news.setUrl(cnn.getUrl());
                news.setImageUrl(cnn.getUrlToImage());
                news.setSource("CNN");
                news.setAuthor(cnn.getAuthor());
                news.setPublishedAt(parseZonedTime(cnn.getPublishedAt()));
                newsRepository.save(news);
            }
        }
    }

    private void saveBbcNews() {
        List<Map<String, String>> bbcNewsList = bbcRssService.getBbcNews();
        System.out.println("BBC News fetched: " + bbcNewsList.size());
        for (Map<String, String> item : bbcNewsList) {
            String url = item.get("link");
            if (!newsRepository.findByUrl(url).isPresent()) {
                News news = new News();
                news.setTitle(item.get("title"));
                news.setDescription(item.get("description"));
                news.setUrl(url);
                news.setImageUrl(item.get("imageUrl"));
                news.setSource("BBC");
                news.setPublishedAt(parseZonedTime(item.get("pubDate")));
                newsRepository.save(news);
            }
        }
    }

    private void saveNhkNews() {
        List<Map<String, String>> nhkNewsList = nhkRssService.getNhkNews();
        System.out.println("NHK News fetched: " + nhkNewsList.size());
        for (Map<String, String> item : nhkNewsList) {
            String url = item.get("link");
            if (!newsRepository.findByUrl(url).isPresent()) {
                News news = new News();
                news.setTitle(item.get("title"));
                news.setDescription(item.get("description"));
                news.setUrl(url);
                news.setImageUrl(item.get("imageUrl"));
                news.setSource("NHK");
                news.setPublishedAt(parseZonedTime(item.get("pubDate")));
                newsRepository.save(news);
                
            }
        }
    }

    private ZonedDateTime parseZonedTime(String dateTimeStr) {
        try {
            return ZonedDateTime.parse(dateTimeStr);
        } catch (Exception e) {
            return ZonedDateTime.now(); // fallback 用現在時間
        }
    }
    
    public List<News> getNewsByIds(List<Long> ids) {          
        if (ids == null || ids.isEmpty()) {                   
            return List.of();                                 
        }                                                     
        return newsRepository.findByIdIn(ids);               
    }         
    @Scheduled(cron = "0 */30 * * * *")
    public void autoFetchNews() {
        System.out.println("🕒 自動開始抓新聞...");
        fetchAndSaveAllNews();
    }
}
