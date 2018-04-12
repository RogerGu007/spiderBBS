package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.magic.constants.Constant;
import com.school.magic.constants.ExtractMode;
import com.school.magic.constants.NJUSiteConstant;
import com.school.utils.DateUtils;
import com.school.utils.TextareaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.school.magic.constants.NJUSiteConstant.*;
import static com.school.utils.DateUtils.DEFAULT_DATE_FORMAT;
import static com.school.utils.DateUtils.ENGLISH_DATE_FORMAT;
import static com.school.utils.DateUtils.NORMAL_ENGLISH_DATE_FORMAT;


public class NJUSiteHandler extends SQSiteHandler{


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public int getSiteLocationCode() {
        return com.school.spiderEnums.LocationEnum.NANJING.getZipCode();
    }

    @Override
    public String getLinkUrl() {
        return FORMITEMLINK;
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
    protected String getPageDetailPostDateXPath() {
        return DETAIL_POSTDATE_REGEX;
    }

    @Override
    protected String getFormItemTitleXPath() {
        return FORMITEMTITEL;
    }

    @Override
    protected String getPageDetailSubjectXPath() {
        return DETAIL_SUBJECT_REGEX;
    }

    @Override
    protected String getPageDetailContentXPath() {
        return DETAIL_CONTENT;
    }

    protected String getPageSubDetailContentXPath() {
        return SUB_DETAIL_CONTENT;
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain(NJUBBSDOMAIN).setSleepTime(Constant.SLEEPTIME);
    }

    @Override
    public void setExtractMode() {
        this.extractMode = ExtractMode.EXTRACT_TEXT;
    }

    //过滤掉hot类型的广告
    protected Selectable getChildPage(Selectable item) {
        if (item == null || item.nodes().size() == 0)
            return null;

        List<Selectable> nodeList = item.xpath(getFormItemXPath()).nodes();
        if (nodeList == null || nodeList.size() < 1)
            return null;

        if (nodeList.get(0).toString().equalsIgnoreCase(JOB_FORUM_MODERATOR))
            return null;

        return item.xpath(getFormItemXPath());
    }

    protected List<String> getNextPages() {
        List<String> linkList = getmPage().getHtml().xpath(getFormNextPagesXPath()).links().all();
        //详情页相同标签的下一页以board=JobExpress结尾，需要过滤掉
        if (linkList == null || linkList.size() < 1 || linkList.get(0).endsWith(INDEX_JOB_URL_TAG))
            return new ArrayList<>();
        return linkList;
    }

    protected List<String> getSubList(List<String> dataList, Integer from, Integer to) {
        List<String> subList = new ArrayList<>();
        if (dataList.size() == 0) {
            return subList;
        }

        String firstNextPageStartId = Pattern.compile("[^0-9]").matcher(dataList.get(0)).replaceAll("");
        String nextPageStartId = firstNextPageStartId;
        for (int ii = 1; ii < to - from + 1; ii++) {
            nextPageStartId = String.valueOf(Integer.valueOf(nextPageStartId)-20>0
                    ? Integer.valueOf(nextPageStartId)-20:0);
            dataList.add(dataList.get(0).replace(firstNextPageStartId, nextPageStartId));
        }

        return dataList;
    }

    public String getPostDate(Selectable item) {
        //格式：Apr 8 11:56
        String originDate = item.xpath(getFormItemModifyTimeXPath()).toString();
        originDate += " " + String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        return DateUtils.getStringFromDate(DateUtils.getDateFromString(originDate, ENGLISH_DATE_FORMAT), DEFAULT_DATE_FORMAT);
    }

    /**
     * 特殊处理，去掉postDateStr头尾的括号
     *
     * @param postDateStr
     * @return
     */
    protected Date formatPostDate(String postDateStr) {
        postDateStr = postDateStr.substring(1, postDateStr.length()-1);
        Date postDate = DateUtils.getDateFromString(postDateStr, NORMAL_ENGLISH_DATE_FORMAT);
        if (postDate == null) //兼容日期中有特殊符号的情况
            postDate = DateUtils.getDateFromString(postDateStr, SPECIAL_ENGLISH_DATE_FORMAT);
        return postDate;
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

        Selectable detailSelector = contentItem.xpath(getPageSubDetailContentXPath());
        if (detailSelector == null)
            return null;

        String postDateStr = detailSelector.regex(getPageDetailPostDateXPath()).toString();
        Date postDate = formatPostDate(postDateStr);

        String newsSubject = detailSelector.regex(NJUSiteConstant.DETAIL_SUBJECT_REGEX).toString();
        if (newsSubject.indexOf(getPageRowSeparator()) > 0)
            newsSubject = newsSubject.substring(0,newsSubject.indexOf(getPageRowSeparator()));
        newsSubject = newsSubject.replaceAll(NJUSiteConstant.DETAIL_SUBJECT_REGEX_Start, "");

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

        String htmlStr = contentItem.xpath(getPageSubDetailContentXPath()).regex(NJUSiteConstant.DETAIL_SUBJECT_REGEX).toString();
        String content = TextareaUtils.convertTextareToHtml(htmlStr);
        return NewsDetailDTO.generateNewsDetail(content, page.getUrl().toString());
    }

}
