package com.example.demo.service;

import com.example.demo.model.dto.NewsDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsScraperService {

    public List<NewsDto> fetchNews() throws IOException {
        String url = "https://edition.cnn.com/2025/05/28/business/us-court-blocks-trumps-tariffs";
        Document doc = Jsoup.connect(url).get();

        String imageUrl = getMetaImage(doc);
        String title = getTitle(doc);
        String time = getTime(doc);
        String reporter = getReporter(doc);
        String content = getContent(doc);

        NewsDto newsItem = new NewsDto(title, content, time, imageUrl, reporter, url);

        List<NewsDto> newsList = new ArrayList<>();
        for (int i = 0; i < 2; i++) newsList.add(newsItem); // 重複兩筆
        for (int i = newsList.size(); i < 9; i++) newsList.add(new NewsDto()); // 補空白卡

        return newsList;
    }

    private String getMetaImage(Document doc) {
        Element imageMeta = doc.selectFirst("meta[property=og:image]");
        return imageMeta != null ? imageMeta.attr("content") : "N/A";
    }

    private String getTitle(Document doc) {
        Element titleEl = doc.selectFirst("h1");
        return titleEl != null ? titleEl.text() : "N/A";
    }

    private String getTime(Document doc) {
        Element timeEl = doc.selectFirst("time");
        return timeEl != null ? timeEl.attr("datetime") : "N/A";
    }

    private String getReporter(Document doc) throws IOException {
        Element jsonLd = doc.selectFirst("script[type=application/ld+json]");
        if (jsonLd != null) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonLd.html());
            JsonNode authorNode = root.get("author");
            if (authorNode != null) {
                return authorNode.isArray()
                        ? authorNode.get(0).get("name").asText()
                        : authorNode.get("name").asText();
            }
        }
        return "N/A";
    }

    private String getContent(Document doc) {
        Elements paragraphs = doc.select("main p");
        StringBuilder sb = new StringBuilder();
        for (Element p : paragraphs) {
            sb.append(p.text()).append("\n");
        }
        return sb.toString().trim();
    }
}
