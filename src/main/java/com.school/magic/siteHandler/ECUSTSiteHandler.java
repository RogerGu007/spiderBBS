package com.school.magic.siteHandler;

import com.school.magic.constants.Constant;
import com.school.magic.constants.ECUSTSiteConstant;
import com.school.magic.constants.SiteEnum;
import us.codecraft.webmagic.Site;

import java.util.ArrayList;
import java.util.List;

import static com.school.magic.constants.ECUSTSiteConstant.ECUST_BBS_DOMAIN;
import static com.school.magic.constants.TJSiteConstant.*;

public class ECUSTSiteHandler extends SQSiteHandler{

    private List<String> mChildNodes = new ArrayList<>();

    @Override
    public int getSiteLocationCode() {
        return com.school.spiderEnums.LocationEnum.SHANGHAI.getZipCode();
    }

    @Override
    public String getLinkUrl() {
        return FORMITEMLINKURL;
    }

    @Override
    public boolean isLoginPage() {
        if (getmPage() == null)
            return false;

        if (getmPage().getUrl().toString().equalsIgnoreCase(ECUSTSiteConstant.LOGIN_URL))
            return true;

        return false;
    }

    @Override
    protected String getFormNodeXPath() {
        return FORMNODE;
    }

    @Override
    protected String getFormItemXPath() {
        if (mChildNodes.size() == 0)
            return null;

        String childXPath = String.format("//img[@src=\"%s\"]", mChildNodes.get(0));
        for (int ii = 1; ii < mChildNodes.size(); ii++)
            childXPath += (String.format("| //img[@src=\"%s\"]", mChildNodes.get(ii)));
        return childXPath;
    }

    @Override
    protected String getFormItemDetailXPath() {
        return FORMITEMLINK;
    }

    @Override
    protected String getFormItemModifyTimeXPath() {
        return FORMITEMMODIFYTIME;
    }

    @Override
    protected String getFormNextPagesXPath() {
        return FORMITEMNEXTPAGE;
    }

    @Override
    protected String getFormItemTitleXPath() {
        return FORMITEMTITEL;
    }

    @Override
    protected String getPageDetailPostDateXPath() {
        return DETAILPOSTDATE;
    }

    @Override
    protected String getPageDetailSubjectXPath() {
        return DETAILSUBJECT;
    }

    @Override
    protected String getPageDetailContentXPath() {
        return DETAILCONTENTSANDCOMMENTS;
    }

    @Override
    public Site getSite() {
        Site site = Site.me().setDomain(ECUST_BBS_DOMAIN)
                .setSleepTime(Constant.SLEEPTIME)
                .setCharset(Constant.SPIDER_CHARSET);
        return site;
    }

    @Override
    public String getPublisher() {
        return SiteEnum.ECUST_BBS.getNickName();
    }
}
