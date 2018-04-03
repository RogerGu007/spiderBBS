package com.school.dao;

import com.school.entity.News;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface INewsDAO {

    List<News> selectAllNews();

    int insert(News record);

    News findByUrl(String url);
}
