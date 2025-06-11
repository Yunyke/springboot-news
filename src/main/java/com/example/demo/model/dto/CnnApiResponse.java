package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class CnnApiResponse {
	 private String status;
	    private int totalResults;
	    private List<CnnNews> articles;
}