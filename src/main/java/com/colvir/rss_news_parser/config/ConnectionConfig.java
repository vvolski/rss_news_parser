package com.colvir.rss_news_parser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.*;

@Configuration
public class ConnectionConfig {

    @Value("${parser.connection.urlString: https://ria.ru/export/rss2/archive/index.xml}")
    private String urlString;

    @Value("${parser.connection.urlConnectionTimeout: 5000}")
    private int urlConnectionTimeout;

    /* Подключение к новостному порталу */
    public URLConnection getUrlConnection() throws URISyntaxException, IOException {
        URLConnection connection = new URI(urlString).toURL().openConnection();
        connection.setConnectTimeout(urlConnectionTimeout);
        return connection;
    }
}
