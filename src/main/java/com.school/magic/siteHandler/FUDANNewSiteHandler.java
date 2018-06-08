package com.school.magic.siteHandler;

import com.school.Gson.FudanCookieGson;
import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.magic.constants.Constant;
import com.school.magic.constants.SiteEnum;
import com.school.spiderEnums.LocationEnum;
import com.school.utils.DateUtils;
import com.school.utils.GsonUtils;
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

import static com.school.magic.constants.FUDANNewSiteConstant.*;

public class FUDANNewSiteHandler extends SQSiteHandler {
    private FudanCookieGson mFudanCookieGson = null;

    @Override
    public int getSiteLocationCode() {
        return LocationEnum.SHANGHAI.getZipCode();
    }

    @Override
    public String getLinkUrl() {
        return FORMNODE;
    }

    @Override
    public boolean isLoginPage() {
        if (getmPage() == null)
            return false;

        if (getmPage().getUrl().toString().equalsIgnoreCase(LOGIN_URL))
            return true;

        return false;
    }

    @Override
    public void extractCookie(Page page)
    {
        if (page == null)
            return;

        Selectable cookiePath = page.getHtml().xpath(COOKIEPATH);
        if (cookiePath == null)
            return;
        String cookieGson = cookiePath.toString();
        try {
            mFudanCookieGson = GsonUtils.fromGsonString(cookieGson, FudanCookieGson.class);
        }
        catch (Exception ex)
        {
            logger.error("", "failed to convert Gson:" + cookieGson + ex);
        }
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
    public String getPublisher() {
        return SiteEnum.FUDAN_BBS.getNickName();
    }

    @Override
    public Site getSite() {
        if (mFudanCookieGson == null)
            return Site.me().setDomain(FUDAN_BBS_DOMAIN)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
                .setSleepTime(Constant.SLEEPTIME);
        else
        {
            String headerCookie = String.format("utmpkey=%s; utmpuser=%s", mFudanCookieGson.getSession_key(), mFudanCookieGson.getUser_name());
            return Site.me().setDomain(FUDAN_BBS_DOMAIN)
                    .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
                    .setSleepTime(Constant.SLEEPTIME)
                    .addHeader("cookie", headerCookie);
        }
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

                    String board = getBoard();
                    if (StringUtils.isNotEmpty(board)) {
                        requestLinks.add(LINKTEMPLATE.replace("BOARD_PLACEHOLDER", board)
                                .replace("ID_PLACEHOLDER", detailNode.toString()));
                    }
                    logger.info(String.format("title: {%s}", item.xpath(getFormItemTitleXPath()).toString()));
                }
            }
        }

        if (getNewsURLList().contains(getmPage().getUrl().toString())) {
            List<String> nextPages = getNextPages();
            //只能翻6页，每天更新内容一般不会超过6页
            if (com.school.entity.Constant.SWITCH_ON.equals(PropertyUtil.getProperty("FIRST_SPIDER_SWITCH")))
                requestLinks.addAll(getSubList(nextPages, 0, 10));
            else
                requestLinks.addAll(getSubList(nextPages, 0, 3));
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
        if (nextPageNode == null || StringUtils.isEmpty(nextPageNode.toString()))
            return  null;

        String board = getBoard();
        List<String> pageList = new ArrayList<>();
        int startIndex = Integer.valueOf(nextPageNode.toString());
        for (int i = 1; i <= 10; i++) {
            String index = String.valueOf(startIndex - i * 20);
            pageList.add(NEXTPAGETEMPLATE.replace("BOARD_PLACEHOLDER", board)
                    .replace("ID_PLACEHOLDER", index));
        }
        return pageList;
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

    @Override
    public NewsDetailDTO extractNewsDetails(Page page) {
        if (page == null)
            return null;

        Selectable contentsAndComments = page.getHtml().xpath(getPageDetailContentXPath());

        if (contentsAndComments == null || contentsAndComments.nodes().size() == 0)
            return null;

        Selectable detailContent = contentsAndComments.nodes().get(0);
        if (detailContent == null)
            return null;

        List<Selectable> selectableList = page.getHtml().regex("http://.*?.jpg").nodes();
        for (Selectable selectable : selectableList) {
            String imageSource = selectable.toString();
            String oriHtml = String.format("<a i=\"i\" href=\"%s\"></a>", imageSource);
            String expectedHtml = String.format("<a i=\"i\" href=\"%s\"><img src=\"%s\"></a>", imageSource, imageSource);
            detailContent = detailContent.replace(oriHtml, expectedHtml);
        }

        String link = page.getUrl().toString();
        return NewsDetailDTO.generateNewsDetail(detailContent.toString(), link);
    }

    /**
     * 获取版块
     *
     * @return
     */
    private String getBoard() {
        String[] linkArr = getmPage().getUrl().toString().split(LINKSPLITER);
        if (linkArr.length == 2)
            return linkArr[1];
        else
            return "";
    }
}
