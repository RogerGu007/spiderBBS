package com.school.magic.siteHandler;

import com.school.entity.News;
import com.school.entity.NewsDetail;
import com.school.magic.constants.Constant;
import com.school.spiderConstants.NewsEnum;
import com.school.spiderConstants.NewsJobSubEnum;
import com.school.utils.DateUtils;
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

public abstract class SQSiteHandler implements BaseSiteHandler {

    //列表数据
    protected abstract String getFormNodeXPath();
    protected abstract String getFormItemXPath();
    protected abstract String getFormItemDetailXPath();
    protected abstract String getFormItemModifyTimeXPath();
    protected abstract String getFormNextPagesXPath();

    //详细数据
    protected abstract String getPageDetailPostDateXPath();
    protected abstract String getPageDetailSubjectXPath();
    protected abstract String getPageDetailContentXPath();

    private Logger logger = LoggerFactory.getLogger(getClass());
    private BasicNameValuePair mUserNamePair = null;
    private BasicNameValuePair mPasswordPair = null;
    private String URL;
    private NewsEnum mNewsType;
    private String mNewsURL;
    private Page mPage;

    SQSiteHandler() {}

    SQSiteHandler(String userNameKey, String userName, String pwdKey, String password, String sqlURL, NewsEnum newsType) {
        setUserNamePair(userNameKey, userName);
        setPasswordPair(pwdKey, password);
        setLoginURL(sqlURL);
        setNewsType(newsType);
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

    public void setNewsType(NewsEnum newsType) {
        this.mNewsType = newsType;
    }

    public NewsEnum getmNewsType() {
        return mNewsType;
    }

    public Site getSite() {
        return null;
    }

    public Page getmPage() {
        return mPage;
    }

    public void setmPage(Page mPage) {
        this.mPage = mPage;
    }

    protected List<String> getSubList(List<String> dataList, Integer from, Integer to) {
        List<String> subList = new ArrayList<>();
        if (dataList.size() == 0) {
            return subList;
        }

        for (int ii = from; ii <= to; ii++) {
            if (dataList.get(ii) == null)
                break;
            subList.add(dataList.get(ii));
        }
        return subList;
    }

    protected Boolean isDroppedItem(String itemDate) {
        Date convertDate = DateUtils.getDateFromString(itemDate);
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

        if(itemDate.compareTo(getAcceptDate()) < 0) {
            return true;
        }
        return false;
    }

    protected Date getPostDate(Html pageHtml, String dateXPath) {
        if(pageHtml == null || TextUtils.isEmpty(dateXPath))
            return null;

        Selectable body = pageHtml.xpath(dateXPath).regex(DateUtils.DATE_REGX);
        if (body != null)
            return DateUtils.getDateFromString(body.toString());
        return new Date();
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
                if (detailNode != null) {
                    String modifiedDate = item.xpath(getFormItemModifyTimeXPath()).toString();
                    if (isDroppedItem(modifiedDate)) //比预设的时间早，帖子丢弃
                        continue;

                    requestLinks.addAll(detailNode.links().all());
                    logger.info("title: {}", detailNode.toString());
                }
            }
        }

        //下一页
        List<String> nextPages = getNextPages();
        //只能翻6页，每天更新内容一般不会超过6页
        requestLinks.addAll(getSubList(nextPages, 0, 5));
        return requestLinks;
    }

    private List<String> getNextPages() {
        return getmPage().getHtml().xpath(getFormNextPagesXPath()).links().all();
    }

    private Selectable getChildPage(Selectable item) {
        if (item == null || item.nodes().size() == 0)
            return null;

        return item.xpath(getFormItemXPath());
    }

    protected void setSubEnumType(News news) {
        if (mNewsType == NewsEnum.NEWS_JOB) {
            NewsJobSubEnum jobSubType = NewsJobSubEnum.SUB_FULLTIME;
            if (news.getmSubject().contains(Constant.INTERN))
                jobSubType = NewsJobSubEnum.SUB_INTERN;
            else if (news.getmSubject().contains(Constant.PARTTIME))
                jobSubType = NewsJobSubEnum.SUB_PARTTIME;
            news.setmNewsJobSubType(jobSubType.getSiteCode());
        }
    }

    /**
     * //返回item等信息
     * @return
     */
    @Override
    public News extractNews() {
        Date postDate = getPostDate(getmPage().getHtml(), getPageDetailPostDateXPath());
        if (postDate == null)
            return null;

        Selectable subjectItem = getmPage().getHtml().xpath(getPageDetailSubjectXPath());
        if (subjectItem == null || subjectItem.nodes().size() == 0)
            return null;

        News subjectNews = News.generateNews(subjectItem.toString(), getmNewsType(), postDate);
        setSubEnumType(subjectNews);
        return subjectNews;
    }

    @Override
    public NewsDetail extractNewsDetails() {
        Selectable contentsAndComments = getmPage().getHtml().xpath(getPageDetailContentXPath());
        if (contentsAndComments == null || contentsAndComments.nodes().size() == 0)
            return null;
        //获取内容，忽略comments，后续完善 todo
        Selectable detailContent = contentsAndComments.nodes().get(0);
        if (detailContent == null)
            return null;

        return NewsDetail.generateNewsDetail(detailContent.toString(), getmPage().getUrl().toString());
    }
}
