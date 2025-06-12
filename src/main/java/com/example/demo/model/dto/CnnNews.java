package com.example.demo.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CnnNews {
	private Long id;
	private String title;
	private String author;
	private String urlToImage;
	private String description;
	private String url;
	private String content;
	private String publishedAt;
	private Boolean favorited;
}
//"publishedAt": "2025-06-10T15:28:34Z",
//"author": "Curt Devine",
//"urlToImage": "https://media.cnn.com/api/v1/images/stellar/prod/gettyimages-2218402431.jpg?c=16x9&q=w_800,c_fill",
//"description": "Los Angeles officials say ICE keeps them in the dark on raids. That hurts protest response, police say | CNNcnn.com",
//"source": {
//  "name": "CNN",
//  "id": "cnn"
//},
//"title": "Los Angeles officials say ICE keeps them in the dark on raids. That hurts protest response, police say | CNN",
//"url": "https://www.cnn.com/2025/06/10/us/los-angeles-officials-ice-protests-invs",
//"content": "As federal authorities stage immigration raids across Los Angeles, local police and officials say they are being kept in the dark a lack of communication the citys leaders blame for hurting their res\u2026 [+8471 chars]"
//},