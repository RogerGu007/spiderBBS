package com.school.dao;

import com.school.entity.News;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface INewsDAO {

    List<News> selectAllNews();

    public int addRecord(News record);
}
