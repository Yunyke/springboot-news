package com.example.demo.service;

import com.example.demo.model.dto.CnnNews;
import com.example.demo.model.entity.News;
import com.example.demo.repository.NewsRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class CnnCrawlerService {

    @Autowired
    private NewsRepository newsRepository;

    private static final String API_URL = "https://newsapi.org/v2/everything";
    private static final String API_KEY = "ce761eb8cf3c4502afd4330735237d5f";

    public List<News> fetchAndSaveIfNotExist() {
        List<News> savedNewsList = new ArrayList<>();

        try {
            String url = API_URL + "?sources=cnn&language=en&apiKey=" + API_KEY;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject json = new JSONObject(sb.toString());
            JSONArray articles = json.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject item = articles.getJSONObject(i);
                String urlStr = item.optString("url");

                // ✅ 避免重複存入
                if (!newsRepository.existsByUrl(urlStr)) {
                    News news = new News();
                    news.setTitle(item.optString("title"));
                    news.setDescription(item.optString("description"));
                    news.setUrl(urlStr);
                    news.setImageUrl(item.optString("urlToImage"));
                    news.setAuthor(item.optString("author"));
                    news.setSource("CNN");

                    // publishedAt 轉成 ZonedDateTime
                    String publishedAtStr = item.optString("publishedAt");
                    if (publishedAtStr != null && !publishedAtStr.isEmpty()) {
                        news.setPublishedAt(ZonedDateTime.parse(publishedAtStr));
                    }

                    savedNewsList.add(newsRepository.save(news));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedNewsList;
    }
}
