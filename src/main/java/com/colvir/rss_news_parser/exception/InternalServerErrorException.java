package com.colvir.rss_news_parser.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InternalServerErrorException {
    private String message;
}
