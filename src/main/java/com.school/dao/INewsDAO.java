package com.school.dao;

import com.school.entity.NewsDTO;
import com.school.entity.NewsDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface INewsDAO {

    List<NewsDTO> selectAllNews();

    int insert(NewsDTO record);

    NewsDTO findByUrl(String url);
}
