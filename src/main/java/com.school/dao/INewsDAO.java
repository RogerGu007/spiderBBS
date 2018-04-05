package com.school.dao;

import com.school.entity.News;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface INewsDAO {

    List<News> selectAllNews();

    int insert(News record);

    News findByUrl(Map<String, String> urlMap);
}
