package com.example.demo.service;

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
import java.io.InputStreamReader;
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

        List<News> saved = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new URL(API_URL + "?sources=cnn&language=en&apiKey=" + API_KEY).openStream()))) {

            String jsonStr = br.lines().reduce("", String::concat);
            JSONArray articles = new JSONObject(jsonStr).getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {

                JSONObject art = articles.getJSONObject(i);
                String url = art.optString("url");
                if (newsRepository.existsByUrl(url)) continue;

                /* --- 2-1 先爬 CNN 網頁全文 ---------------- */
                String fullText = "";
                try {
                    Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

                    // 🔧 修改：支援更多 selector，抓不到就 fallback
                    Elements paras = doc.select("article div[data-component='text-block'], div.l-container p, article p");

                    StringBuilder sbTxt = new StringBuilder();
                    for (Element p : paras) {
                        if (!p.text().isBlank()) sbTxt.append(p.text()).append("\n\n");
                    }
                    fullText = sbTxt.toString().trim();

                    // 🔧 額外提示哪些網頁沒抓到
                    if (fullText.isEmpty()) {
                        System.err.println("⚠️ 無法抓取全文: " + url);
                    }

                } catch (Exception ex) {
                    System.err.println("❌ 爬取失敗: " + url);
                    ex.printStackTrace();
                }

                /* --- 2-2 存進資料庫 ----------------------- */
                News news = new News();
                news.setTitle(art.optString("title"));
                news.setDescription(art.optString("description"));
                news.setUrl(url);
                news.setImageUrl(art.optString("urlToImage"));
                news.setAuthor(art.optString("author"));
                news.setSource("CNN");

                // 🔧 修改：避開 NewsAPI 的簡略 content（含 "[+xxx chars]"）
                String apiContent = art.optString("content");
                if (apiContent.contains("[+") || apiContent.length() < 100) {
                    apiContent = ""; // 判斷為 preview
                }

                news.setContent(fullText.isEmpty() ? apiContent : fullText);

                String publishedAtStr = art.optString("publishedAt");
                if (!publishedAtStr.isEmpty()) {
                    news.setPublishedAt(ZonedDateTime.parse(publishedAtStr));
                }

                saved.add(newsRepository.save(news));
            }

        } catch (Exception e) {
            System.err.println("❌ CNN 抓取失敗");
            e.printStackTrace();
        }

        return saved;
    }
}
