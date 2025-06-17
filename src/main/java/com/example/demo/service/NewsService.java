package com.example.demo.service;

import com.example.demo.model.entity.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.model.dto.BbcNews;
import com.example.demo.model.dto.CnnNews;
import com.example.demo.model.dto.NhkNews;

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
    	List<News> cnnNewsList = cnnCrawlerService.fetchAndSaveIfNotExist();
        System.out.println("CNN News fetched: " + cnnNewsList.size());

        
    }

    private void saveBbcNews() {
        List<BbcNews> bbcNewsList = bbcRssService.getBbcNews();
        System.out.println("BBC News fetched: " + bbcNewsList.size());

        for (BbcNews item : bbcNewsList) {
            String url = item.getLink();
            if (!newsRepository.findByUrl(url).isPresent()) {
                News news = new News();
                news.setTitle(item.getTitle());
                news.setDescription(item.getDescription());
                news.setUrl(url);
                news.setImageUrl(item.getImageUrl());
                news.setSource("BBC");
                news.setPublishedAt(parseZonedTime(item.getPubDate()));
                newsRepository.save(news);
            }
        }
    }


    private void saveNhkNews() {
        List<NhkNews> nhkNewsList = nhkRssService.getNhkNews();
        System.out.println("NHK News fetched: " + nhkNewsList.size());

        for (NhkNews item : nhkNewsList) {
            String url = item.getLink();
            if (!newsRepository.findByUrl(url).isPresent()) {
                News news = new News();
                news.setTitle(item.getTitle());
                news.setDescription(item.getDescription());
                news.setUrl(url);
                news.setImageUrl(item.getImageUrl());
                news.setSource("NHK");
                news.setPublishedAt(parseZonedTime(item.getPubDate())); 
                news.setContent(item.getContent());
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