package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CnnApiResponse {
	
	    private int totalResults;
	    private List<CnnNews> articles;
}