package com.school.dao;

import com.school.entity.NewsDetailDTO;

import java.util.Map;

/**
 * Author: roger
 */
public interface INewsDetailDAO {

    int insert(NewsDetailDTO newsDetailDTO);

    NewsDetailDTO findByUrl(Map<String, String> urlMap);
}
