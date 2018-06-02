package com.school.magic.siteHandler;

import com.school.magic.constants.Constant;
import com.school.magic.constants.SiteEnum;
import com.school.spiderEnums.LocationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;
import java.util.ArrayList;
import java.util.List;

import static com.school.magic.constants.FUDANSiteConstant.*;

public class FUDANSiteJobHandler extends SQSiteHandler {

    @Override
    public int getSiteLocationCode() {
        return LocationEnum.SHANGHAI.getZipCode();
    }

    @Override
    public String getLinkUrl() {
        return JOB_FORMNODE;
    }

    @Override
    public boolean isLoginPage() {
        //可以不用登陆，游客模式
        return false;
    }

    @Override
    protected String getFormNodeXPath() {
        return JOB_FORMNODE;
    }

    @Override
    protected String getFormItemXPath() {
        return JOB_FORMITEMCHILD;
    }

    @Override
    protected String getFormItemDetailXPath() {
        return JOB_FORMITEMLINK;
    }

    @Override
    protected String getFormItemModifyTimeXPath() {
        return JOB_FORMITEMMODIFYTIME;
    }

    @Override
    protected String getFormNextPagesXPath() {
        return JOB_FORMITEMNEXTPAGE;
    }

    @Override
    protected String getFormItemTitleXPath() {
        return JOB_FORMITEMTITEL;
    }

    @Override
    protected String getPageDetailPostDateXPath() {
        return JOB_DETAIL_POSTDATE;
    }

    @Override
    protected String getPageDetailSubjectXPath() {
        return JOB_DETAIL_SUBJECT;
    }

    @Override
    protected String getPageDetailContentXPath() {
        return JOB_DETAIL_CONTENT;
    }

    @Override
    public String getPublisher() {
        return SiteEnum.FUDAN_BBS.getNickName();
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain(FUDAN_BBS_JOB_DOMAIN)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
                .setSleepTime(Constant.SLEEPTIME);
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getPostDate(Selectable item) {
        //2018-06-02
        String oriDateStr = item.regex(getFormItemModifyTimeXPath()).toString();
        return String.format("%s 00:00:00", oriDateStr);
    }

    protected Selectable getChildPage(Selectable item) {
        if (item == null || item.nodes().size() == 0)
            return null;

        return item.xpath(JOB_FORMITEMFILTER);
    }

    protected List<String> getNextPages() {
        //翻页信息是脚本里面的，抓取不到
        List<String> pageList = new ArrayList<>();
        String prefixUrl = getmPage().getUrl().toString();
        for (int i = 2; i < 11; i++) {
            pageList.add(String.format("%s&PageIndex=%s", prefixUrl, i));
        }
        return pageList;
    }
}
