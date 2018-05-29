package com.school.magic.siteHandler;

import com.school.magic.constants.Constant;
import com.school.magic.constants.SiteEnum;
import com.school.spiderEnums.LocationEnum;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.school.magic.constants.ZJUSiteConstant.*;

public class ZJUSiteHandler extends SQSiteHandler{

    private List<String> mChildNodes = new ArrayList<>();

    @Override
    public int getSiteLocationCode() {
        return LocationEnum.ZHEJIANG.getZipCode();
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
        Site site = Site.me().setDomain(ZJU_BBS_DOMAIN)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
                .setSleepTime(Constant.SLEEPTIME)
                .setCharset(Constant.SPIDER_CHARSET);
        return site;
    }

    @Override
    public String getPublisher() {
        return SiteEnum.ZJU_BBS.getNickName();
    }

    @Override
    public String getPostDate(Selectable item) {
        Selectable selectable = item.xpath(getFormItemModifyTimeXPath());
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + "-" + selectable.toString().trim();
    }

    @Override
    protected List<String> getNextPages() {
        Selectable pageItems = getmPage().getHtml().xpath(getFormNextPagesXPath());
        List<String> nextPageLinkUrl = new ArrayList<>();
        if (pageItems != null) {
            List<Selectable> pageNodes = pageItems.nodes();
            pageNodes.remove(pageNodes.size()-1);
            for (Selectable pageNode : pageNodes) {
                String url = pageNode.links().all().get(0);
                if (!url.endsWith(FORMCURRENTPAGE))
                    //自定义翻页的url列表
                    nextPageLinkUrl.add(url);
            }
        }
        return nextPageLinkUrl;
    }
}
