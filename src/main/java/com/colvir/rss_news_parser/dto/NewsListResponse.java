package com.colvir.rss_news_parser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewsListResponse {
    private List<NewsResponse> news;
}
