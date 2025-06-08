package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CnnNews {

	private String title;        // 文章標題
    private String content;      // 內文（可抓全文或前幾段）
    private String publishDate;  // 發布時間（通常 ISO 格式）
    private String imageUrl;     // 主圖網址
    private String author;   // 記者或來源
    private String url;          // 文章網址（建議保留）
    
}
