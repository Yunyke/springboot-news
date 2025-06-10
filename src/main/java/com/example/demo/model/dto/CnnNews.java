package com.example.demo.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CnnNews {
    private String title;
    private String img;
    private List<String> reporter;
    private String byline;
    private String date;
    private String description;
    private List<String> content;
    private String link;

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
               "Image: " + img + "\n" +
               "Reporters: " + reporter + "\n" +
               "Byline: " + byline + "\n" +
               "Date: " + date + "\n" +
               "Description: " + description + "\n" +
               "Content:\n" + String.join("\n", content);
    }
}
//"title": "Israel intercepts Gaza-bound aid ship, detaining Greta Thunberg and other prominent activists",
//"img": "https://media.cnn.com/api/v1/images/stellar/prod/still-21687969-16016-016-still.jpg?c=16x9&q=h_653,w_1160,c_fill/f_webp",
//"reporter": [
//  "Mohammed Tawfeeq",
//  "Helen Regan"
//],
//"byline": "By Kareem Khadder, Mohammed Tawfeeq, Abeer Salman and Helen Regan, CNN",
//"date": "2025-06-09",
//"description": "Israel has intercepted a Gaza-bound aid ship carrying Greta Thunberg and other prominent activists, detaining those onboard and taking them to Israel."
//}