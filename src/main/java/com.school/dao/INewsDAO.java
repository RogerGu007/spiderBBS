package com.school.dao;

import com.school.entity.NewsDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface INewsDAO {

    List<NewsDTO> selectAllNews();

    int insert(NewsDTO record);

    NewsDTO findByUrl(Map<String, String> urlMap);
}
