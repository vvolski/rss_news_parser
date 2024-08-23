package com.colvir.rss_news_parser.mapper;

import com.colvir.rss_news_parser.dto.NewsListResponse;
import com.colvir.rss_news_parser.dto.NewsResponse;
import com.colvir.rss_news_parser.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {
    NewsResponse newsToResponse(News news);
    List<NewsResponse> newsListToResponseList(List<News> news);
    default NewsListResponse newsListToResponse(List<News> news) {
        return new NewsListResponse(newsListToResponseList(news));
    }
}
