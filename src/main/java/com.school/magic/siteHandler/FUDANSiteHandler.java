package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.magic.constants.Constant;
import com.school.magic.constants.SiteEnum;
import com.school.spiderEnums.LocationEnum;
import com.school.utils.DateUtils;
import com.school.spiderEnums.NewsTypeEnum;

import com.school.utils.PropertyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.school.magic.constants.FUDANSiteConstant.*;

public class FUDANSiteHandler extends SQSiteHandler {

    private List<String> childNodes = new ArrayList<>();

    @Override
    public int getSiteLocationCode() {
        return LocationEnum.SHANGHAI.getZipCode();
    }

    @Override
    public String getLinkUrl() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_FORMITEMLINK : JOB_FORMNODE;
    }

    @Override
    public boolean isLoginPage() {
        //可以不用登陆，游客模式
        return false;
    }

    @Override
    protected String getFormNodeXPath() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_FORMNODE : JOB_FORMNODE;
    }

    @Override
    protected String getFormItemXPath() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_FORMITEMCHILD : JOB_FORMITEMCHILD;
    }

    @Override
    protected String getFormItemDetailXPath() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_FORMITEMLINK : JOB_FORMITEMLINK;
    }

    @Override
    protected String getFormItemModifyTimeXPath() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_FORMITEMMODIFYTIME : JOB_FORMITEMMODIFYTIME;
    }

    @Override
    protected String getFormNextPagesXPath() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_FORMITEMNEXTPAGE : JOB_FORMITEMNEXTPAGE;
    }

    @Override
    protected String getFormItemTitleXPath() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_FORMITEMTITEL : JOB_FORMITEMTITEL;
    }

    @Override
    protected String getPageDetailPostDateXPath() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_DETAIL_POSTDATE : JOB_DETAIL_POSTDATE;
    }

    @Override
    protected String getPageDetailSubjectXPath() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_DETAIL_SUBJECT : JOB_DETAIL_SUBJECT;
    }

    @Override
    protected String getPageDetailContentXPath() {
        return getmNewsType().equals(NewsTypeEnum.NEWS_FRIENDS) ? FRIEND_DETAIL_CONTENT : JOB_DETAIL_CONTENT;
    }

    @Override
    public String getPublisher() {
        return SiteEnum.FUDAN_BBS.getNickName();
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain(FUDAN_BBS_JOB_DOMAIN)
                .setSleepTime(Constant.SLEEPTIME);
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

                    requestLinks.add(FRIEND_LINKTEMPLATE.replaceAll("PLACEHOLDER", detailNode.toString()));
                    logger.info(String.format("title: {%s}", item.xpath(getFormItemTitleXPath()).toString()));
                }
            }
        }

        if (getmPage().getUrl().toString().equalsIgnoreCase(getNewsURL())) {
            List<String> nextPages = getNextPages();
            //只能翻6页，每天更新内容一般不会超过6页
            if (com.school.entity.Constant.SWITCH_ON.equals(PropertyUtil.getProperty("FIRST_SPIDER_SWITCH")))
                requestLinks.addAll(getSubList(nextPages, 0, 10));
            else
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

        if (item.xpath(FRIEND_FORMITEMFILTER).toString().equalsIgnoreCase("1"))
            return null;

        return item.xpath(getFormItemXPath());
    }

    protected List<String> getNextPages() {
        Selectable nextPageNode = getmPage().getHtml().xpath(getFormNextPagesXPath());
        if (nextPageNode == null || StringUtils.isEmpty(nextPageNode.toString()))
            return  null;
        return new ArrayList<String>() {{ add(FRIEND_NEXTPAGETEMPLATE.replaceAll("PLACEHOLDER", nextPageNode.toString()));}};
    }

    protected List<String> getSubList(List<String> dataList, Integer from, Integer to) {
        List<String> subList = new ArrayList<>();
        if (dataList == null || dataList.size() == 0) {
            return subList;
        }

        int startId = Integer.valueOf(dataList.get(0).split(FRIEND_NEXTPAGESEPERATE)[1]);
        for (int ii = from; ii < to; ii++) {
            if (startId - 20*(ii-from+1) > 0)
                subList.add(FRIEND_NEXTPAGETEMPLATE.replaceAll("PLACEHOLDER", String.valueOf(startId-20*(ii-from+1))));
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
            if(isDroppedItem(postDate))
                return null;
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
