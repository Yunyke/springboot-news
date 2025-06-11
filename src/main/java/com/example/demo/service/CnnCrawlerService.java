package com.example.demo.service;

import com.example.demo.model.dto.CnnNews;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
@Service
public class CnnCrawlerService {

	 public List<CnnNews> getCnnNews() {
        String basedURL = "https://newsapi.org/v2/everything?";
        String sources = "cnn";
        String language = "en";
        String apiKey = "ce761eb8cf3c4502afd4330735237d5f";
        String URL = basedURL + "sources=" + sources + "&language=" + language + "&apiKey=" + apiKey;

        StringBuilder response = new StringBuilder();
        List<CnnNews> newsList = new ArrayList<>();
        try {
            java.net.URL url = new java.net.URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            
         // 依 HTTP 回應碼顯示成功/失敗            
            System.out.println("CNN news fetch " + (connection.getResponseCode() == HttpURLConnection.HTTP_OK ? "success" : "failed with code " + connection.getResponseCode()));
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                response.append(currentLine);
            }
            // 🔍 開始解析 JSON 字串
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject item = articles.getJSONObject(i);

                CnnNews news = new CnnNews();
                news.setTitle(item.optString("title"));
                news.setAuthor(item.optString("author"));
                news.setUrlToImage(item.optString("urlToImage"));
                news.setDescription(item.optString("description"));
                news.setUrl(item.optString("url"));
                news.setContent(item.optString("content"));
                news.setPublishedAt(item.optString("publishedAt"));

                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }
}