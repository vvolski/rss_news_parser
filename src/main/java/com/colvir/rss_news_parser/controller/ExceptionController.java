package com.colvir.rss_news_parser.controller;

import com.colvir.rss_news_parser.exception.InternalServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.colvir.rss_news_parser.controller")
public class ExceptionController {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<InternalServerErrorException> handleControllerException() {
        InternalServerErrorException internalServerErrorException = new InternalServerErrorException("Probably, invalid format data. Please, check and try again");
        return ResponseEntity.internalServerError().body(internalServerErrorException);
    }
}