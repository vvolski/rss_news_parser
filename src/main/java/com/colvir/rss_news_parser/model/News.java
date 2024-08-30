package com.colvir.rss_news_parser.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News {
    @Id
    @SequenceGenerator(name = "news_seq", sequenceName = "news_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_seq")
    private long id;
    private String title;
    private String guid;
    private String link;
    private LocalDateTime pub_date;
}
