package com.school.Service;

import com.school.dao.INewsDAO;
import com.school.dao.INewsDetailDAO;
import com.school.entity.News;
import com.school.entity.NewsDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class NewsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private INewsDAO newsDAO;

    @Autowired
    private INewsDetailDAO newsDetailDAO;

    @Transactional
    public void storeDataToDB(News subjectNews, NewsDetail detailNews) {
        if (subjectNews == null || detailNews == null)
            return;

        if (subjectNews != null) {
            storeDataToDB(subjectNews);
        }

        if (detailNews != null && subjectNews == null) {
            storeDataToDB(detailNews);
        } else if (detailNews != null && subjectNews != null) {
            detailNews.setNewsID(subjectNews.getId());
            newsDetailDAO.insert(detailNews);
        }
    }

    @Transactional
    public void storeDataToDB(News subjectNews) {
        if (subjectNews == null)
            return;

        newsDAO.insert(subjectNews);
    }

    @Transactional
    public void storeDataToDB(NewsDetail detailNews) {
        if (detailNews == null)
            return;

        News subjectNews = newsDAO.findByUrl(new HashMap<String, String>() {{
            put("linkUrl", detailNews.getSourceArticleUrl());
        }});
        if (subjectNews != null) {
            detailNews.setNewsID(subjectNews.getId());
            newsDetailDAO.insert(detailNews);
        }
    }

    @Transactional
    public void storeDataToDB(List<News> subjectNewsList) {
        if (subjectNewsList == null || subjectNewsList.size() == 0)
            return;

        for (News subjectNews : subjectNewsList) {
            newsDAO.insert(subjectNews);
        }
    }
}
