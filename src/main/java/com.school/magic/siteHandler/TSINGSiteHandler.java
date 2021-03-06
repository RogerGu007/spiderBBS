package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.magic.constants.Constant;
import com.school.magic.constants.ExtractMode;
import com.school.magic.constants.SiteEnum;
import com.school.spiderEnums.LocationEnum;
import com.school.utils.DateUtils;
import com.school.utils.HtmlUtils;
import com.school.utils.TextareaUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.lang.reflect.Array;
import java.util.*;

import static com.school.magic.constants.TSINGSiteConstant.*;
import static com.school.utils.DateUtils.DATE_REGX_FOR_DAY;
import static com.school.utils.DateUtils.DATE_REGX_FOR_TIME;
import static com.school.utils.DateUtils.NORMAL_ENGLISH_DATE_FORMAT;

public class TSINGSiteHandler extends SQSiteHandler {

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
        return DETAIL_POSTDATE_REGEX;
    }

    @Override
    protected String getPageDetailSubjectXPath() {
        return DETAIL_SUBJECT_REGEX_START;
    }

    protected String getPageDetailSubjectEndTagXPath() {
        return DETAIL_SUBJECT_REGEX_END;
    }

    @Override
    protected String getPageDetailContentXPath() {
        return DETAIL_CONTENT;
    }

    protected String getPageSubDetailContentXPath() {
        return SUB_DETAIL_CONTENT;
    }

    protected String getPageDetailContentStartRegex() {
        return DETAIL_CONTENT_START_REGEX;
    }

    protected List<String> getPageDetailContentEndRegex() {
        return DETAIL_CONTENT_END_SPLIT_LIST;
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain(TSINGBBSDOMAIN)
                .setSleepTime(Constant.SLEEPTIME);
    }

    @Override
    public void setExtractMode() {
        this.extractMode = ExtractMode.EXTRACT_TEXT;
    }

    @Override
    public String getPublisher() {
        return SiteEnum.TSING_BBS.getNickName();
    }

    public String getPostDate(Selectable item) {
        //两种格式：1、19:38:08，补足为当日 2018-04-06 19:38:08；2、2018-03-30，补足2018-03-30 00:00:00
        String originDate = item.xpath(getFormItemModifyTimeXPath()).regex(DATE_REGX_FOR_DAY).toString();
        if (originDate == null) { //表示当日
            return DateUtils.getStringFromDate(new Date(), "yyyy-MM-dd") + " " +
                    item.xpath(getFormItemModifyTimeXPath()).regex(DATE_REGX_FOR_TIME).toString();
        }
        return originDate + " 00:00:00";
    }

    protected List<String> getNextPages() {
        List<String> pageList = getmPage().getHtml().xpath(getFormNextPagesXPath()).links().all();
        //去掉第一个，标识为当前页的linkUrl
        pageList.remove(0);
        return pageList;
    }

    protected String getPageRowSeparator() {
        return "\\s+";
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

        //	第一列：发信人: blephant (甲骨文研发招聘), 信区: Career_Upgrade
        //  第二列：标  题: [甲骨文北京]数据库运维工程师-Oracle Cloud DevOps_DBA
        //  第三列：发信站: 水木社区 (Mon Jan 29 17:52:52 2018), 站内
        //  下面都是详情内容
        NewsDTO newsDTO = null;
        Selectable selectable = contentItem.xpath(getPageSubDetailContentXPath()).regex(getPageDetailPostDateXPath());
        if (selectable == null || StringUtils.isEmpty(selectable.toString()))
            return null;
        String postDateStr = selectable.toString().replaceAll("&nbsp;", " ");
        Date postDate = formatPostDate(postDateStr);

        List<String> contentList = Arrays.asList(HtmlUtils.filterHtmlTag(contentItem.xpath(getPageSubDetailContentXPath())
                .toString()).split(getPageRowSeparator()));
        String newsSubject = "";
        boolean isSubjectExtractStart = false;
        for (int ii = 0; ii < contentList.size(); ii++) {
            //主题抽取需要过滤掉 &nbsp;
            String tempContent = contentList.get(ii).replaceAll("&nbsp;", " ");
            if (tempContent.replaceAll(" ", "").startsWith(getPageDetailSubjectXPath())) {  //开始抽取的标签
                isSubjectExtractStart = true;
                continue;
            }

            if (tempContent.replaceAll(" ", "").startsWith(getPageDetailSubjectEndTagXPath()))  //结束抽取的标签
                break;

            if (isSubjectExtractStart) {
                newsSubject += tempContent;
            }
        }

        newsDTO = NewsDTO.generateNews(newsSubject, getmNewsType(), postDate);
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

        String contentOri = contentItem.xpath(getPageSubDetailContentXPath()).toString();
        String content = "";
        String[] firstSeperate =
                contentOri.split(getPageDetailContentStartRegex());
        if (firstSeperate.length < 2)
            content = contentOri;
        else {
            content = firstSeperate[1];
            for (String endSplit : getPageDetailContentEndRegex()) {
                content = content.replaceFirst(endSplit, "");
            }
        }
        content = TextareaUtils.convertTextareToHtml(content, page.getUrl().toString());
        return NewsDetailDTO.generateNewsDetail(content, page.getUrl().toString());
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
}
