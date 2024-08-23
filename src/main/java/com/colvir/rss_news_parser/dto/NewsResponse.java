package com.colvir.rss_news_parser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NewsResponse {
    private String title;
    private String link;
    private LocalDateTime pub_date;
}
