package com.school.magic.siteHandler;

import com.school.entity.NewsDetailDTO;
import com.school.magic.constants.Constant;
import com.school.spiderEnums.LocationEnum;
import com.school.utils.DateUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.school.magic.constants.PEKINGSiteConstant.*;
import static com.school.utils.DateUtils.DEFAULT_DATE_FORMAT;

public class PEKINGSiteHandler extends SQSiteHandler {

    private List<String> childNodes = new ArrayList<>();

    @Override
    public int getSiteLocationCode() {
        return LocationEnum.BEIJING.getZipCode();
    }

    @Override
    public String getLinkUrl() {
        return FORMITEMLINK;
    }

    @Override
    public boolean isLoginPage() {
        //可以不用登陆，游客模式
        return false;
    }

    @Override
    protected String getFormNodeXPath() {
        return FORMNODE;
    }

    @Override
    protected String getFormItemXPath() {
        return FORMITEMCHILD;
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
        return DETAIL_POSTDATE_TAG;
    }

    @Override
    protected String getPageDetailSubjectXPath() {
        return DETAIL_SUBJECT_TAG;
    }

    @Override
    protected String getPageDetailContentXPath() {
        return DETAIL_CONTENT;
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain(PEKINGBBSDOMAIN).setSleepTime(Constant.SLEEPTIME);
    }

    public String getPostDate(Selectable item) {
        //四种格式：1、04-03 14:49   2、16:59   3.昨天 13:08  4.40分钟前  都要补全为 2018-04-06 21:00:00
        //在列表页用于时间判断，后三种都是最近的帖子，无须过滤
        String originDate = item.xpath(getFormItemModifyTimeXPath()).regex("\\d+-\\d+\\s\\d+:\\d+").toString();
        if (originDate == null) {
            return DateUtils.getStringFromDate(new Date(), DEFAULT_DATE_FORMAT);
        }
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + "-" + originDate;
    }

    protected List<String> getNextPages() {
        List<String> pageList = getmPage().getHtml().xpath(getFormNextPagesXPath()).links().all();
        //去掉第一个，标识为当前页的linkUrl
        pageList.remove(getmPage().getUrl().toString());
        return pageList;
    }

    @Override
    public NewsDetailDTO extractNewsDetails(Page page, Selectable item) {
        //Peking默认从详情页解析出详情
        if (page == null)
            return null;

        String linkUrl = page.getUrl().toString();

        Selectable contentsNodes = page.getHtml().xpath(getPageDetailContentXPath());
        if (contentsNodes == null || contentsNodes.nodes().size() == 0)
            return null;

        String content = "";
        for (Selectable contentNode : contentsNodes.nodes()) {
            content += contentNode.toString();
            if (!contentNode.toString().equalsIgnoreCase(""))
                content += "\n";
        }

        return NewsDetailDTO.generateNewsDetail(content, linkUrl);
    }
}
