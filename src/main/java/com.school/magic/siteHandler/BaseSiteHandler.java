package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public interface BaseSiteHandler {

    public List<String> getRequests();

    public NewsDTO extractNews(Page page);

    public NewsDetailDTO extractNewsDetails(Page page);

    public boolean isLoginPage();

    public int getSiteLocationCode();

    public String getLinkUrl();
}
