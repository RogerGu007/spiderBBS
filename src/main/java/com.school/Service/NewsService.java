package com.school.Service;

import com.school.dao.INewsDAO;
import com.school.dao.INewsDetailDAO;
import com.school.dao.IPublisherDAO;
import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.entity.PublisherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    @Autowired
    private IPublisherDAO publisherDAO;

    @Transactional
    public void storeDataToDB(NewsDTO subjectNews, NewsDetailDTO detailNews) {
        if (subjectNews == null || detailNews == null)
            return;

        storeDataToDB(subjectNews);
        detailNews.setNewsID(subjectNews.getId());
        newsDetailDAO.insert(detailNews);
    }

    @Transactional
    public void storeDataToDB(NewsDTO subjectNews) {
        if (subjectNews == null)
            return;

        newsDAO.insert(subjectNews);
    }

    @Transactional
    public void storeDataToDB(final NewsDetailDTO detailNews) {
        if (detailNews == null)
            return;

        NewsDTO subjectNews = newsDAO.findByUrl(new HashMap<String, String>() {{
            put("linkUrl", detailNews.getSourceArticleUrl());
        }});
        if (subjectNews != null) {
            detailNews.setNewsID(subjectNews.getId());
            newsDetailDAO.insert(detailNews);
        }
    }

    public NewsDetailDTO getNewsDetailByUrl(final String url) {
        return newsDetailDAO.findByUrl(new HashMap<String, String>() {{ put("sourceArticleUrl", url); }});
    }

    public PublisherDTO getPublisherId(String username) {
        return publisherDAO.findByUsername(username);
    }
}
