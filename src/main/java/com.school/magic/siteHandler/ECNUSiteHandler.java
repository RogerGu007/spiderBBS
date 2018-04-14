package com.school.magic.siteHandler;

import com.school.magic.constants.Constant;
import com.school.magic.constants.SiteEnum;
import us.codecraft.webmagic.Site;

import java.util.ArrayList;
import java.util.List;

import static com.school.magic.constants.ECNUSiteConstant.*;

public class ECNUSiteHandler extends SQSiteHandler{

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
        return DETAIL_POSTDATE;
    }

    @Override
    protected String getPageDetailSubjectXPath() {
        return DETAIL_SUBJECT;
    }

    @Override
    protected String getPageDetailContentXPath() {
        return DETAIL_CONTENT;
    }

    @Override
    public Site getSite() {
        Site site = Site.me().setDomain(ECNU_BBS_DOMAIN).setSleepTime(Constant.SLEEPTIME);
        return site;
    }

    @Override
    public String getPublisher() {
        return SiteEnum.ECNU_BBS.name();
    }
}
