package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.magic.constants.Constant;
import com.school.spiderEnums.LocationEnum;
import com.school.utils.DateUtils;
import com.school.utils.HtmlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.school.magic.constants.FUDANSiteConstant.*;
import static com.school.utils.DateUtils.DEFAULT_DATE_FORMAT;

public class FUDANSiteHandler extends SQSiteHandler {

    private List<String> childNodes = new ArrayList<>();

    @Override
    public int getSiteLocationCode() {
        return LocationEnum.SHANGHAI.getZipCode();
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
        return Site.me().setDomain(FUDAN_BBS_JOB_DOMAIN).setSleepTime(Constant.SLEEPTIME);
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<String> getRequests() {
        //文中pages
        Selectable body = getmPage().getHtml().xpath(getFormNodeXPath());
        List<String> requestLinks = new ArrayList<>();

        for (int ii = 0; ii < body.nodes().size(); ii++) {
            Selectable item = body.nodes().get(ii);
            Selectable childItems = getChildPage(item);
            if (childItems != null && childItems.nodes().size() > 0) {
                Selectable detailNode = item.xpath(getFormItemDetailXPath());
                if (detailNode != null && detailNode.toString() != null) {
                    String modifiedDate = getPostDate(item);
                    if (isDroppedItem(modifiedDate)) //比预设的时间早，帖子丢弃
                        continue;

                    requestLinks.add(LINKTEMPLATE.replaceAll("PLACEHOLDER", detailNode.toString()));
                    logger.info(String.format("title: {%s}", item.xpath(getFormItemTitleXPath()).toString()));
                }
            }
        }

        if (getmPage().getUrl().toString().equalsIgnoreCase(getNewsURL())) {
            List<String> nextPages = getNextPages();
            //只能翻6页，每天更新内容一般不会超过6页
            requestLinks.addAll(getSubList(nextPages, 0, 5));
        }
        return requestLinks;
    }

    public String getPostDate(Selectable item) {
        //2018-03-01T21:42:32
        //在列表页用于时间判断，后三种都是最近的帖子，无须过滤
        String originDate = item.xpath(getFormItemModifyTimeXPath()).toString();
        return originDate.replace("T", " ");
    }

    protected Selectable getChildPage(Selectable item) {
        if (item == null || item.nodes().size() == 0)
            return null;

        if (item.xpath(FORMITEMFILTER).toString().equalsIgnoreCase("1"))
            return null;

        return item.xpath(getFormItemXPath());
    }

    protected List<String> getNextPages() {
        Selectable nextPageNode = getmPage().getHtml().xpath(getFormNextPagesXPath());
        if (nextPageNode == null)
            return  null;
        return new ArrayList<String>() {{ add(NEXTPAGETEMPLATE.replaceAll("PLACEHOLDER", nextPageNode.toString()));}};
    }

    protected List<String> getSubList(List<String> dataList, Integer from, Integer to) {
        List<String> subList = new ArrayList<>();
        if (dataList.size() == 0) {
            return subList;
        }

        int startId = Integer.valueOf(dataList.get(0).split(NEXTPAGESEPERATE)[1]);
        for (int ii = from; ii < to; ii++) {
            if (startId - 20*(ii-from+1) > 0)
                subList.add(NEXTPAGETEMPLATE.replaceAll("PLACEHOLDER", String.valueOf(startId-20*(ii-from+1))));
            else
                break;
        }
        return subList;
    }

    public NewsDTO extractNews(Page page) {
        if (page == null)
            return null;

        Selectable date = page.getHtml().xpath(getPageDetailPostDateXPath());

        Date postDate = null;
        if (date == null || StringUtils.isEmpty(date.toString())) {
            return null;
        } else {
            String dateStr = date.toString().substring(0, date.toString().length()-4).replaceAll("[\\u4e00-\\u9fa5]", " ");
            postDate = DateUtils.getDateFromString(dateStr, "yyyy MM dd hh:mm:ss");
        }

        Selectable subjectItem = page.getHtml().xpath(getPageDetailSubjectXPath());
        if (subjectItem == null || subjectItem.nodes().size() == 0)
            return null;

        NewsDTO subjectNews = NewsDTO.generateNews(subjectItem.toString(), getmNewsType(), postDate);
        setSubEnumType(subjectNews);
        subjectNews.setLocationCode(getSiteLocationCode());
        subjectNews.setLinkUrl(genSiteUrl(page.getUrl().toString()));

        return subjectNews;
    }
}
