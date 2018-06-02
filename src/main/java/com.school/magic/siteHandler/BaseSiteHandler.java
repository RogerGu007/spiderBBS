package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public interface BaseSiteHandler {

    List<String> getRequests();

    NewsDTO extractNews(Page page);

    NewsDetailDTO extractNewsDetails(Page page);

    boolean isLoginPage();

    void extractCookie(Page page);

    int getSiteLocationCode();

    NewsDetailDTO extractNewsDetailsFromText(Page page);

    NewsDTO extractNewsFromText(Page page);

    String getLinkUrl();
}
