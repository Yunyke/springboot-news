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

    public String getCnnNews() {
        String basedURL = "https://newsapi.org/v2/everything?";
        String sources = "cnn";
        String language = "en";
        String apiKey = "ce761eb8cf3c4502afd4330735237d5f";
        String URL = basedURL + "sources=" + sources + "&language=" + language + "&apiKey=" + apiKey;

        StringBuilder response = new StringBuilder();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}