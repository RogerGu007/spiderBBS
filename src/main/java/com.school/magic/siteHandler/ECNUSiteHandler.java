package com.school.magic.siteHandler;

import com.school.magic.constants.Constant;
import com.school.magic.constants.SiteEnum;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

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
        return FORMITEMLINK;
    }

    @Override
    protected String getFormItemDetailXPath() {
        return FORMITEMLINK;
    }

    @Override
    protected String getFormItemModifyTimeXPath() {
        return FORMITEMMODIFYTIME;
    }

    protected String getFormItemBakModifyTimeXPath() {
        return FORMITEMMODIFYTIME_BAK;
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
        return SiteEnum.ECNU_BBS.getNickName();
    }

    public String getPostDate(Selectable item) {
        Selectable selectable = item.xpath(getFormItemModifyTimeXPath());
        if (StringUtils.isEmpty(selectable.toString()))
            selectable = item.xpath(getFormItemBakModifyTimeXPath());
        return selectable.toString() + " 00:00:00";
    }
}
