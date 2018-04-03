package com.school.magic.siteHandler;

import com.school.entity.News;
import com.school.entity.NewsDetail;
import com.school.spiderConstants.LocationEnum;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public interface BaseSiteHandler {

    public List<String> getRequests();

    public News extractNews(Page page, Selectable item);

    public NewsDetail extractNewsDetails(Page page, Selectable item);

    public boolean isLoginPage();

    public int getSiteLocationCode();

    public String getLinkUrl();
}
