package com.example.demo.service;

import com.example.demo.model.dto.CnnNews;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class CnnCrawlerService {

    public CnnNews crawl(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0")
                            .get();

        CnnNews news = new CnnNews();

        // Step 1: 找出含 metadata 的 <script> 區塊
        Elements scriptTags = doc.select("script");
        for (Element script : scriptTags) {
            String content = script.html();
            if (content.contains("window.CNN.metadata")) {
                int jsonStart = content.indexOf("window.CNN.metadata") + "window.CNN.metadata = ".length();
                int jsonEnd = content.indexOf("};", jsonStart) + 1;

                String jsonString = content.substring(jsonStart, jsonEnd);
                JSONObject json = new JSONObject(jsonString);
                JSONObject meta = json.getJSONObject("content");

                // Step 2: 擷取欄位
                news.setTitle(meta.optString("headline"));
                news.setImg(meta.optString("srcset"));
                news.setByline(meta.optString("byline"));

                JSONArray authors = meta.optJSONArray("author");
                List<String> reporters = new ArrayList<>();
                if (authors != null) {
                    for (int i = 0; i < authors.length(); i++) {
                        reporters.add(authors.getString(i));
                    }
                }
                news.setReporter(reporters);

                // Step 3: 取發佈日期（fallback 至 byline 時戳）
                String byline = news.getByline();
                String extractedDate = byline.replaceAll(".*Updated\\s+([^,]+,\\s+[^,]+),.*", "$1");
                news.setDate("2025-06-09"); // or parse if needed

                break;
            }
        }

        // Step 4: 抓第一段當 description
        Element firstParagraph = doc.selectFirst("div.article__content-container p");
        if (firstParagraph != null) {
            news.setDescription(firstParagraph.text());
        }
     // Step 5: 抓所有段落作為 content
        Elements paragraphs = doc.select("p.vossi-paragraph");
        List<String> contentList = new ArrayList<>();
        for (Element para : paragraphs) {
            contentList.add(para.text());
        }
        news.setContent(contentList);
        return news;
    }
    

    public static void main(String[] args) {
        CnnCrawlerService service = new CnnCrawlerService();
        try {
            CnnNews news = service.crawl("https://edition.cnn.com/2025/06/08/middleeast/freedom-flotilla-gaza-aid-ship-thunberg-intl-hnk");
            System.out.println(news);
        } catch (IOException e) {
            System.err.println("Error crawling CNN: " + e.getMessage());
        }
    }
}