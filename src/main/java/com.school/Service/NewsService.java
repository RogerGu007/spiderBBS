package com.school.Service;

import com.school.dao.INewsDAO;
import com.school.dao.INewsDetailDAO;
import com.school.dao.IUserDAO;
import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.entity.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class NewsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private INewsDAO newsDAO;

    @Resource
    private INewsDetailDAO newsDetailDAO;

    @Resource
    private IUserDAO userDAO;

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

    public UserDTO getPublisherId(String nickname) {
        return userDAO.findByNickname(nickname);
    }

    public void invalidNews(String id, String linkUrl) throws Exception {
        if (StringUtils.isEmpty(linkUrl) && StringUtils.isNotEmpty(id)) {
            NewsDTO newsDTO = newsDAO.findById(Integer.valueOf(id));
            linkUrl = newsDTO.getLinkUrl();
        } else if (StringUtils.isEmpty(id) && StringUtils.isEmpty(linkUrl)) {
            throw new Exception("传参为空");
        }
        newsDAO.invalidNews(linkUrl);
    }
}
