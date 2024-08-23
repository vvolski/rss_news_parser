package com.colvir.rss_news_parser.service;

import com.colvir.rss_news_parser.MapperConfiguration;
import com.colvir.rss_news_parser.dto.NewsListResponse;
import com.colvir.rss_news_parser.dto.NewsResponse;
import com.colvir.rss_news_parser.mapper.NewsMapper;
import com.colvir.rss_news_parser.model.News;
import com.colvir.rss_news_parser.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        NewsService.class,
        NewsMapper.class
})
@SpringBootTest(classes = {MapperConfiguration.class})
public class NewsServiceTest {
    @Autowired
    private NewsService newsService;
    @MockBean
    private NewsRepository newsRepository;

    private final News tnews = new News(0L, "Тестовая новость", "https://ria.ru/20240822/pozhar-1967715077.html",
            "https://ria.ru/20240822/pozhar-1967715077.html", LocalDateTime.parse("Thu, 22 Aug 2024 09:55:01 +0300", DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US)));
    private final News tnews_saved = new News(1, tnews.getTitle(), tnews.getGuid(), tnews.getLink(), tnews.getPub_date());
    private final News tnews2_saved = new News(2, "Тестовая новость 2", "https://ria.ru/20240823/puteshestviya-1967889895.html",
            "https://ria.ru/20240823/puteshestviya-1967889895.html", LocalDateTime.parse("Fri, 23 Aug 2024 05:00:13 +0300", DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US)));
    private final List<News> newsList_saved = List.of(tnews_saved, tnews2_saved);
    private final NewsResponse newsResponse = new NewsResponse(tnews_saved.getTitle(), tnews_saved.getLink(), tnews_saved.getPub_date());
    private final NewsResponse news2Response = new NewsResponse(tnews2_saved.getTitle(), tnews2_saved.getLink(), tnews2_saved.getPub_date());
    private final List<NewsResponse> newsListToResponse = List.of(newsResponse, news2Response);

    @Test
    void saveNews_success() {
        //Подготовка входных данных
        when(newsRepository.save(tnews)).thenReturn(tnews_saved);
        //Подготовка ожидаемого результата
        NewsResponse expectedResponse = new NewsResponse(tnews_saved.getTitle(), tnews_saved.getLink(), tnews_saved.getPub_date());
        NewsResponse actualResponse = newsService.save(tnews);
        //Начало теста
        assertEquals(expectedResponse, actualResponse);
        verify(newsRepository).save(tnews);
    }

    @Test
    void isExist_exist() {
        //Подготовка входных данных
        when(newsRepository.findAllByGuid(tnews_saved.getGuid())).thenReturn(newsList_saved);
        //Начало теста
        assertTrue(newsService.isExist(tnews_saved.getGuid()));
    }

    // Получение списка новостей за промежуток времени
    @Test
    void getNewsBetweenDate_success() {
        //Подготовка входных данных
        when(newsRepository.findAllByPubDateBetween(tnews_saved.getPub_date(), tnews2_saved.getPub_date())).thenReturn(newsList_saved);
        //Подготовка ожидаемого результата
        NewsListResponse expectedListResponse = new NewsListResponse(newsListToResponse);
        NewsListResponse actualListResponse = newsService.getNewsBetweenDate(tnews_saved.getPub_date(), tnews2_saved.getPub_date());
        //Начало теста
        assertEquals(expectedListResponse, actualListResponse);
    }

    //Получение N последних новостей
    @Test
    void getNewsLastCount() {
        //Подготовка входных данных
        when(newsRepository.findAllByPubDateDesc(2)).thenReturn(newsList_saved);
        //Подготовка ожидаемого результата
        NewsListResponse expectedListResponse = new NewsListResponse(newsListToResponse);
        NewsListResponse actualListResponse = newsService.getNewsLastCount(2);
        //Начало теста
        assertEquals(expectedListResponse, actualListResponse);
    }

    // Получение количества новостей на определенную дату/
    @Test
    void getCountNewsInDate() {
        //Подготовка входных данных
        LocalDate date = LocalDate.parse("Fri, 23 Aug 2024 05:00:13 +0300", DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US));
        Integer expectedCount = 1;
        when(newsRepository.findCountByPubDate(date)).thenReturn(expectedCount);
        //Подготовка ожидаемого результата
        Integer actualCount = newsService.getCountNewsInDate(date);
        //Начало теста
        assertEquals(expectedCount, actualCount);
    }
}
