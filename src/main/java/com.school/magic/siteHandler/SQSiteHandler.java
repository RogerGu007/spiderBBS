package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.magic.constants.Constant;
import com.school.magic.constants.ExtractMode;
import com.school.spiderEnums.NewsSubTypeEnum;
import com.school.spiderEnums.NewsTypeEnum;
import com.school.utils.DateUtils;
import com.school.utils.HtmlUtils;
import com.school.utils.TextareaUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.*;
import static com.school.utils.DateUtils.DEFAULT_DATE_FORMAT;
import static com.school.utils.DateUtils.DEFAULT_DATE_FORMAT3;
import static com.school.utils.DateUtils.NORMAL_ENGLISH_DATE_FORMAT;

public abstract class SQSiteHandler implements BaseSiteHandler {

    //列表数据
    protected abstract String getFormNodeXPath();
    protected abstract String getFormItemXPath();
    protected abstract String getFormItemDetailXPath();
    protected abstract String getFormItemModifyTimeXPath();
    protected abstract String getFormNextPagesXPath();
    protected abstract String getFormItemTitleXPath();

    //详细数据
    protected abstract String getPageDetailPostDateXPath();
    protected abstract String getPageDetailSubjectXPath();
    protected abstract String getPageDetailContentXPath();

    public abstract String getPublisher();

    private Logger logger = LoggerFactory.getLogger(getClass());
    private BasicNameValuePair mUserNamePair = null;
    private BasicNameValuePair mPasswordPair = null;
    private String URL;
    private NewsTypeEnum mNewsType;
    private String mNewsURL;
    private Page mPage;
    protected ExtractMode extractMode = ExtractMode.EXTRACT_HTML_ITEM;
    //设置了expectedDate，如果news的发布时间不等于该时间即丢弃
    private Date expectedDate;
    private Date beginDate;
    private Date endDate;

    SQSiteHandler() {
        setExtractMode();
    }

    SQSiteHandler(String userNameKey,
                  String userName,
                  String pwdKey,
                  String password,
                  String sqlURL,
                  NewsTypeEnum newsType) {
        setUserNamePair(userNameKey, userName);
        setPasswordPair(pwdKey, password);
        setLoginURL(sqlURL);
        setNewsType(newsType);
        setExtractMode();
    }

    protected static Date getAcceptDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 1, 1);
        return cal.getTime();
    }

    public void setUserNamePair(String keyName, String userName) {
        mUserNamePair = new BasicNameValuePair(keyName, userName);
    }

    public void setPasswordPair(String pwdName, String password) {
        mPasswordPair = new BasicNameValuePair(pwdName, password);
    }

    public BasicNameValuePair getmUserNamePair() {
        return mUserNamePair;
    }

    public BasicNameValuePair getmPasswordPair() {
        return mPasswordPair;
    }

    public void setLoginURL(String url) {
        this.URL = url;
    }

    public String getLoginURL() {
        return this.URL;
    }

    public void setExtractMode() {
        //默认不设置，使用默认值
    }

    public ExtractMode getExtractMode() {
        return extractMode;
    }

    public boolean hasValidLoginInfo() {
        if (mUserNamePair == null || mPasswordPair == null)
            return false;

        if (TextUtils.isEmpty(mUserNamePair.getName()) || TextUtils.isEmpty(mUserNamePair.getValue()) ||
                TextUtils.isEmpty(mPasswordPair.getName()) || TextUtils.isEmpty(mPasswordPair.getValue()) ||
                TextUtils.isEmpty(getLoginURL()))
            return false;
        else
            return true;
    }

    public Request getLoginRequest() {
        if (!hasValidLoginInfo())
            return null;

        Request request = new Request(getLoginURL());
        Map<String, Object> params = new HashMap<>();
        params.put(mUserNamePair.getName(), mUserNamePair.getValue());
        params.put(mPasswordPair.getName(), mPasswordPair.getValue());
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(params, "UTF-8"));
        return request;
    }

    public void setNewsURL(String newsURL) {
        this.mNewsURL = newsURL;
    }

    public String getNewsURL() {
        return mNewsURL;
    }

    public void setNewsType(NewsTypeEnum newsType) {
        this.mNewsType = newsType;
    }

    public NewsTypeEnum getmNewsType() {
        return mNewsType;
    }

    public abstract Site getSite();

    public Page getmPage() {
        return mPage;
    }

    public void setmPage(Page mPage) {
        this.mPage = mPage;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    protected List<String> getSubList(List<String> dataList, Integer from, Integer to) {
        List<String> subList = new ArrayList<>();
        if (dataList.size() == 0) {
            return subList;
        }

        for (int ii = from; ii < to && ii < dataList.size(); ii++) {
            if (dataList.get(ii) == null)
                break;
            subList.add(dataList.get(ii));
        }
        return subList;
    }

    protected Boolean isDroppedItem(String itemDate) {
        Date convertDate = DateUtils.getDateFromString(itemDate, DEFAULT_DATE_FORMAT);
        return isDroppedItem(convertDate);
    }

    /**
     *  按日期过滤数据
     *
     * @param itemDate
     * @return
     */
    protected Boolean isDroppedItem(Date itemDate) {
        if(itemDate == null)
            return false;

        if (expectedDate != null) {
            if (expectedDate.compareTo(itemDate) == 0)
                return false;
            return true;
        }

        if (beginDate != null && endDate != null) {
            if (itemDate.compareTo(beginDate) >= 0 && itemDate.compareTo(endDate) <= 0)
                return false;
            return true;
        }

        if(itemDate.compareTo(getAcceptDate()) >= 0) {
            return false;
        }
        return true;
    }

    protected Date getPostDate(Html pageHtml, String dateXPath) {
        if(pageHtml == null || TextUtils.isEmpty(dateXPath))
            return null;

        Selectable body = pageHtml.xpath(dateXPath).regex(DateUtils.DATE_REGX);
        if (body != null)
            return DateUtils.getDateFromString(body.toString(), DEFAULT_DATE_FORMAT);
        return new Date();
    }

    /**
     * 详情页是text的站点需要重写该方法
     *
     * @return
     */
    protected String getPageDetailSubjectRegex() {
        return null;
    }

    /**
     * 详情页是text的站点需要重写该方法
     *
     * @return
     */
    protected int getPageDetailContentStartRow() {
        return 1;
    }

    /**
     * 详情页是text的站点需要重写该方法
     *
     * @return
     */
    protected int getPageDetailContentEndRow() {
        return 0;
    }

    /**
     *
     * @return
     */
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
                    //各个站点的时间格式差异较大，建议getPostDate在各自的SiteHandler里面重写
                    String modifiedDate = getPostDate(item);
                    if (isDroppedItem(modifiedDate)) //比预设的时间早，帖子丢弃
                        continue;

                    requestLinks.add(detailNode.links().toString());
                    logger.info(String.format("title: {%s}", item.xpath(getFormItemTitleXPath()).toString()));
                }
            }
        }

        // 下一页   startUrl才需要拿nextPages，否则会一直获取之后的数据
        // 默认方式：列表页可以拿到2,3,4,5...页的url，否则需要在SiteHandler的实现类重写getNextPages、getSubList方法来自定义
        if (getmPage().getUrl().toString().equalsIgnoreCase(getNewsURL())) {
            List<String> nextPages = getNextPages();
            //只能翻6页，每天更新内容一般不会超过6页
            requestLinks.addAll(getSubList(nextPages, 0, 5));
        }
        return requestLinks;
    }

    protected List<String> getNextPages() {
        return getmPage().getHtml().xpath(getFormNextPagesXPath()).links().all();
    }

    protected Selectable getChildPage(Selectable item) {
        if (item == null || item.nodes().size() == 0)
            return null;

        Selectable selectable = item.xpath(getFormItemXPath());
        if (StringUtils.isEmpty(selectable.toString()))
            return null;
        return selectable;
    }

    protected void setSubEnumType(NewsDTO news) {
        if (mNewsType == NewsTypeEnum.NEWS_JOB) {
            NewsSubTypeEnum jobSubType = NewsSubTypeEnum.SUB_FULLTIME;
            if (news.getmSubject().contains(Constant.INTERN) ||
                    news.getmSubject().contains(Constant.PARTTIME))
                jobSubType = NewsSubTypeEnum.SUB_PARTTIME;
            news.setNewsSubType(jobSubType.getNewsSubTypeCode());
        }
    }

    public String genSiteUrl(String url) {
        return url;
    }

    public String getPostDate(Selectable item) {
        return item.xpath(getFormItemModifyTimeXPath()).toString();
    }

    /**
     * 返回item的信息
     * @return
     */
    @Override
    public NewsDTO extractNews(Page page) {
        if (page == null)
            return null;

        Selectable date = page.getHtml().xpath(getPageDetailPostDateXPath()).regex(DateUtils.DATE_REGX);

        Date postDate = null;
        if (date != null)
            postDate = DateUtils.getDateFromString(date.toString(), DEFAULT_DATE_FORMAT);
        else
            return null;

        Selectable subjectItem = page.getHtml().xpath(getPageDetailSubjectXPath());
        if (subjectItem == null || subjectItem.nodes().size() == 0)
            return null;

        NewsDTO subjectNews = NewsDTO.generateNews(subjectItem.toString(), getmNewsType(), postDate);
        setSubEnumType(subjectNews);
        subjectNews.setLocationCode(getSiteLocationCode());
        subjectNews.setLinkUrl(genSiteUrl(page.getUrl().toString()));

        return subjectNews;
    }

    /**
     * 详情页的格式区别比较大，建议SiteHandler自己解析
     *
     * @param page
     * @return
     */
    @Override
    public NewsDetailDTO extractNewsDetails(Page page) {
        if (page == null)
            return null;

        Selectable contentsAndComments = page.getHtml().xpath(getPageDetailContentXPath());

        if (contentsAndComments == null || contentsAndComments.nodes().size() == 0)
            return null;
        //获取内容，忽略comments，后续完善 todo
        Selectable detailContent = contentsAndComments.nodes().get(0);
        if (detailContent == null)
            return null;

        String link = page.getUrl().toString();
//        return NewsDetailDTO.generateNewsDetail(HtmlUtils.filterHtmlTag(detailContent.toString())
//                .replaceAll("&nbsp;&nbsp;", "\n").replaceAll("&nbsp;", " "), link);
        return NewsDetailDTO.generateNewsDetail(detailContent.toString(), link);
    }

    @Override
    public NewsDTO extractNewsFromText(Page page) {
        //从详情页抽取news
        List<Selectable> selectableList = page.getHtml().xpath(getPageDetailContentXPath()).nodes();
        Selectable contentItem;
        if (selectableList != null && selectableList.size() > 0)
            contentItem = selectableList.get(0);
        else
            return null;

        String postDateStr = contentItem.xpath(getPageSubDetailContentXPath())
                .regex(getPageDetailPostDateXPath()).toString();
        Date postDate = formatPostDate(postDateStr);

        List<String> contentList = Arrays.asList(HtmlUtils.filterHtmlTag(contentItem.xpath(getPageSubDetailContentXPath())
                .toString()).split(getPageRowSeparator()));
        String newsSubject = "";
        for (int ii = 0; ii < contentList.size(); ii++) {
            //主题抽取需要过滤掉 &nbsp;
            String tempContent = contentList.get(ii);
            if (tempContent.startsWith(getPageDetailSubjectXPath())) {  //开始抽取的标签
                newsSubject += tempContent.substring(getPageDetailSubjectXPath().length(), tempContent.length());
                break;
            }
        }

        NewsDTO newsDTO = NewsDTO.generateNews(newsSubject, getmNewsType(), postDate);
        setSubEnumType(newsDTO);
        newsDTO.setLocationCode(getSiteLocationCode());
        newsDTO.setLinkUrl(genSiteUrl(page.getUrl().toString()));
        return newsDTO;
    }

    @Override
    public NewsDetailDTO extractNewsDetailsFromText(Page page) {
        //从详情页抽取newsDetail
        List<Selectable> selectableList = page.getHtml().xpath(getPageDetailContentXPath()).nodes();
        Selectable contentItem;
        if (selectableList != null && selectableList.size() > 0)
            contentItem = selectableList.get(0);
        else
            return null;

        String orihtmlStr = contentItem.xpath(getPageSubDetailContentXPath()).regex(getPageDetailSubjectRegex()).toString();
        String htmlStr = "";
        String[] contentArr = orihtmlStr.split("\n");
        for (int ii=0; ii<contentArr.length; ii++) {
            //从 DETAIL_CONTENT_START_ROWNUM 行开始截取
            if (ii+1 < getPageDetailContentStartRow())
                continue;
            //到倒数 DETAIL_CONTENT_END_ROWNUM 行截取结束
            if (contentArr.length - ii <= getPageDetailContentEndRow())
                break;

            htmlStr += contentArr[ii] + "\n";
        }

        String content = TextareaUtils.convertTextareToHtml(htmlStr);
        return NewsDetailDTO.generateNewsDetail(content, page.getUrl().toString());
    }

    protected String getPageSubDetailContentXPath() {
        return null;
    }

    protected String getPageRowSeparator() {
        return "\n";
    }

    protected Date formatPostDate(String postDateStr) {
        postDateStr = postDateStr.substring(1, postDateStr.length()-1);
        Date postDate = DateUtils.getDateFromString(postDateStr, NORMAL_ENGLISH_DATE_FORMAT);
        return postDate;
    }
}
