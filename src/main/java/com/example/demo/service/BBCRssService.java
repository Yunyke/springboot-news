package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BBCRssService {

    public List<Map<String, String>> getBbcNews() {
        List<Map<String, String>> newsList = new ArrayList<>();

        try {
            String url = "https://feeds.bbci.co.uk/news/rss.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new URL(url).openStream());

            NodeList items = doc.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                String title = getTagValue("title", item);
                String description = getTagValue("description", item);
                String pubDate = getTagValue("pubDate", item);
                String imageUrl = "";
                String link = getTagValue("link", item);
                
                NodeList mediaList = item.getElementsByTagName("media:thumbnail");
                if (mediaList.getLength() > 0) {
                    Element media = (Element) mediaList.item(0);
                    imageUrl = media.getAttribute("url");
                }

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
