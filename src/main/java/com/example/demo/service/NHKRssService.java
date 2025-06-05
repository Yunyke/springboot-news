package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NHKRssService {

    private static final SimpleDateFormat rssDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    private static final SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public List<Map<String, String>> getNhkNews() {
        List<Map<String, String>> newsList = new ArrayList<>();

        try {
            String url = "https://feedx.net/rss/nhk.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new URL(url).openStream());

            NodeList items = doc.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                String title = getTagValue("title", item);
                String description = getTagValue("description", item);
                String pubDateRaw = getTagValue("pubDate", item);
                String pubDate;
                try {
                    Date date = rssDateFormat.parse(pubDateRaw);
                    pubDate = outputDateFormat.format(date);
                } catch (Exception e) {
                    pubDate = pubDateRaw;
                }

                // 解析 description 裡的 <img src="..."> 圖片網址
                String imageUrl = "";
                if (description != null && description.contains("<img")) {
                    int imgTagStart = description.indexOf("<img");
                    int srcStart = description.indexOf("src=\"", imgTagStart);
                    if (srcStart > 0) {
                        srcStart += 5;
                        int srcEnd = description.indexOf("\"", srcStart);
                        if (srcEnd > srcStart) {
                            imageUrl = description.substring(srcStart, srcEnd);
                        }
                    }
                }

                // Fallback: 如果有 enclosure 標籤也可用
                if (imageUrl.isEmpty()) {
                    NodeList enclosureList = item.getElementsByTagName("enclosure");
                    if (enclosureList.getLength() > 0) {
                        Element enclosure = (Element) enclosureList.item(0);
                        if ("image/jpeg".equals(enclosure.getAttribute("type"))) {
                            imageUrl = enclosure.getAttribute("url");
                        }
                    }
                }

                String link = getTagValue("link", item);

                Map<String, String> newsItem = new HashMap<>();
                newsItem.put("title", title);
                newsItem.put("description", description);
                newsItem.put("pubDate", pubDate);
                newsItem.put("imageUrl", imageUrl); 
                newsItem.put("link", link);
                newsList.add(newsItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsList;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                return node.getTextContent();
            }
        }
        return "";
    }
}

//----------------------------------------
//json跳到nhk新聞
//String articleId = "20250603_04";
//String link = "https://www3.nhk.or.jp/nhkworld/zh/news/" + articleId + "/";