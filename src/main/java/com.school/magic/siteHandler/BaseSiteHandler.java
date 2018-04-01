package com.school.magic.siteHandler;

import com.school.entity.News;
import com.school.entity.NewsDetail;

import java.util.List;

public interface BaseSiteHandler {

    public List<String> getRequests();

    public News extractNews();

    public NewsDetail extractNewsDetails();

    public boolean isLoginPage();
}
