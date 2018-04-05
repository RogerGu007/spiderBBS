package com.school.Service;

import com.school.dao.INewsDAO;
import com.school.dao.INewsDetailDAO;
import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "NewsService")
public class NewsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private INewsDAO newsDAO;

    @Autowired
    private INewsDetailDAO newsDetailDAO;

    public NewsService() {
        logger.info("test news service initial");
    }

    @Transactional
    public void storeDataToDB(NewsDTO subjectNews, NewsDetailDTO detailNews) {
        if (subjectNews == null || detailNews == null)
            return;

        if (subjectNews != null) {
            storeDataToDB(subjectNews);
        }

        if (detailNews != null) {
            detailNews.setNewsID(subjectNews.getId());
            newsDetailDAO.insert(detailNews);
        } else {
            storeDataToDB(detailNews);
        }
    }

    @Transactional
    public void storeDataToDB(NewsDTO subjectNews) {
        if (subjectNews == null)
            return;

        newsDAO.insert(subjectNews);
    }

    @Transactional
    public void storeDataToDB(NewsDetailDTO detailNews) {
        if (detailNews == null)
            return;

        String sourceArticleUrl = detailNews.getSourceArticleUrl();
        NewsDTO subjectNews = newsDAO.findByUrl(sourceArticleUrl);
        if (subjectNews != null) {
            detailNews.setNewsID(subjectNews.getId());
            newsDetailDAO.insert(detailNews);
        }
    }

    @Transactional
    public void storeDataToDB(List<NewsDTO> subjectNewsList) {
        if (subjectNewsList == null || subjectNewsList.size() == 0)
            return;

        for (NewsDTO subjectNews : subjectNewsList) {
            newsDAO.insert(subjectNews);
        }
    }
}
