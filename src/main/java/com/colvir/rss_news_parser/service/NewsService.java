package com.colvir.rss_news_parser.service;

import com.colvir.rss_news_parser.dto.NewsListResponse;
import com.colvir.rss_news_parser.dto.NewsResponse;
import com.colvir.rss_news_parser.mapper.NewsMapper;
import com.colvir.rss_news_parser.model.News;
import com.colvir.rss_news_parser.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;


    /* Проверка наличия новости по GUID в БД */
    public boolean isExist(String guid) {
        List<News> news = newsRepository.findAllByGuid(guid);
        return !news.isEmpty();
    }

    /* Сохранение новости в БД */
    public NewsResponse save(News news) {
        newsRepository.save(news);
        return newsMapper.newsToResponse(news);
    }

    /* Получение списка новостей за промежуток времени */
    public NewsListResponse getNewsBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        return newsMapper.newsListToResponse(newsRepository.findAllByPubDateBetween(startDate, endDate));
    }

    /* Получение N последних новостей */
    public NewsListResponse getNewsLastCount(Integer countNews) {
        return newsMapper.newsListToResponse(newsRepository.findAllByPubDateDesc(countNews));
    }

    /* Получение количества новостей на определенную дату */
    public Integer getCountNewsInDate(LocalDate pubDate) {
        return newsRepository.findCountByPubDate(pubDate);
    }
}
