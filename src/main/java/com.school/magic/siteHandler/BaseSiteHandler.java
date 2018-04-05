package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.spiderEnums.LocationEnum;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public interface BaseSiteHandler {

    public List<String> getRequests();

    public NewsDTO extractNews(Page page, Selectable item);

    public NewsDetailDTO extractNewsDetails(Page page, Selectable item);

    public boolean isLoginPage();

    public int getSiteLocationCode();

    public String getLinkUrl();
}
