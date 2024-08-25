package com.colvir.rss_news_parser.service;

import com.colvir.rss_news_parser.config.ConnectionConfig;
import com.colvir.rss_news_parser.model.News;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ParserService {

    private final NewsService newsService;
    private final ConnectionConfig connectionConfig;

    private static final String ITEM = "item";
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String PUB_DATE = "pubDate";
    private static final String GUID = "guid";


    /* Зачитка элемента для парсинга */
    private String getEventChars(XMLEventReader eventReader) throws XMLStreamException {
        String eventChars = "";
        XMLEvent event = eventReader.nextEvent();
        if (event instanceof Characters) {
            eventChars = event.asCharacters().getData();
        }
        return eventChars;
    }

    /* Преобразование в формат даты для поиска и сохранения в БД */
    public LocalDateTime getFormattedDate(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US));
    }

    /* Парсинг и сохранение новостей из портала в БД */
    public void parseNews() {
        try {
            //Подключение к ресурсу, создание артефактов для работы с XML через STAX
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(connectionConfig.getUrlConnection().getInputStream());


            String title = "";
            String guid = "";
            String link = "";
            LocalDateTime pub_date = null;

            //Читаем пока есть следующая ветка тегов
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    //Нашли стартовый тег, фильтруем его наименование по константам
                    String localPart = event.asStartElement().getName().getLocalPart();
                    //Наполняем объект данными текущей ветки тегов
                    switch (localPart) {
                        case TITLE -> title = getEventChars(eventReader);
                        case GUID -> guid = getEventChars(eventReader);
                        case LINK -> link = getEventChars(eventReader);
                        case PUB_DATE -> pub_date = getFormattedDate(getEventChars(eventReader));
                    }
                    //Если последний элемент порции тегов: проверяем что новости нет в БД и вставляем
                } else if (event.isEndElement() &&
                        event.asEndElement().getName().getLocalPart().equals(ITEM) &&
                        guid.equals(link) && !newsService.isExist(guid)) {
                    //Всегда чистый объект без остатков данных прошлых итераций
                    News news = new News();
                    news.setTitle(title);
                    news.setGuid(guid);
                    news.setLink(link);
                    news.setPub_date(pub_date);

                    newsService.save(news);
                }
            }
        } catch (XMLStreamException | IOException | URISyntaxException e) {

            throw new RuntimeException(e);
        }
    }
}
