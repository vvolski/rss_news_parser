package com.colvir.rss_news_parser.job;

import com.colvir.rss_news_parser.service.ParserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class ScheduledTask {
    @Autowired
    private ParserService parserService;

    /* Запуск задания для парсинга и сохранение новостей из портала в базу данных */
    @Scheduled(fixedDelayString = "${parser.fixDelayJob: 10000}")
    public void scheduledParseNews(){
        parserService.parseNews();
    }
}
