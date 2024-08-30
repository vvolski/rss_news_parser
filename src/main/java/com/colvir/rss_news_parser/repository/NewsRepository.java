package com.colvir.rss_news_parser.repository;

import com.colvir.rss_news_parser.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    /* Получение списка новостей по GIUD */
    List<News> findAllByGuid(String guid);

    /* Получение списка новостей за промежуток времени */
    @Query(value = "SELECT a.* FROM news a WHERE pub_date BETWEEN :startDate AND :endDate ORDER BY a.pub_date ASC", nativeQuery = true)
    List<News> findAllByPubDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /* Получение N последних новостей */
    @Query(value = "SELECT * FROM (SELECT a.* FROM news a ORDER BY a.pub_date DESC LIMIT :countNews) ORDER BY pub_date ASC", nativeQuery = true)
    List<News> findAllByPubDateDesc(Integer countNews);

    /* Получение количества новостей на определенную дату */
    @Query(value = "SELECT COUNT(a.*) FROM news a WHERE CAST(a.pub_date AS DATE) = CAST(:pubDate AS DATE)", nativeQuery = true)
    Integer findCountByPubDate(LocalDate pubDate);
}
