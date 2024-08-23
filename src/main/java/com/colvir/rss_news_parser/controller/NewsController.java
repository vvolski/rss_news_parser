package com.colvir.rss_news_parser.controller;

import com.colvir.rss_news_parser.dto.NewsListResponse;
import com.colvir.rss_news_parser.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@RestController
@RequestMapping("news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("getLastCountNews/{countNews}")
    @ResponseStatus(HttpStatus.OK)
    public NewsListResponse getLastCountNews(@PathVariable("countNews") Integer countNews){
        return newsService.getNewsLastCount(countNews);
    }

    @GetMapping("getNewsBetweenDate")
    public NewsListResponse getBetweenDateNews(
            @RequestParam(value = "startDate") LocalDateTime startDate,
            @RequestParam(value = "endDate") LocalDateTime endDate) {
        return newsService.getNewsBetweenDate(startDate, endDate);
    }

    @GetMapping("getCountNewsInDate/{pubDate}")
    public Integer getCountNewsInDate(@PathVariable("pubDate") LocalDate pubDate) {
        return newsService.getCountNewsInDate(pubDate);
    }
}
