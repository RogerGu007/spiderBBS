package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.magic.constants.Constant;
import com.school.magic.constants.ExtractMode;
import com.school.magic.constants.NJUSiteConstant;
import com.school.magic.constants.SiteEnum;
import com.school.spiderEnums.LocationEnum;
import com.school.utils.DateUtils;
import com.school.utils.TextareaUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.school.magic.constants.WHUSiteConstant.*;
import static com.school.utils.DateUtils.*;


public class WHUSiteHandler extends SQSiteHandler{


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public int getSiteLocationCode() {
        return LocationEnum.WUHAN.getZipCode();
    }

    @Override
    public String getLinkUrl() {
        return null;
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
        return null;
    }

    @Override
    protected String getFormItemDetailXPath() {
        return null;
    }

    @Override
    protected String getFormItemModifyTimeXPath() {
        return null;
    }

    @Override
    protected String getFormNextPagesXPath() {
        return FORM_ITEM_NEXT_PAGE;
    }

    @Override
    protected String getPageDetailPostDateXPath() {
        return DETAIL_POSTDATE_REGEX;
    }

    @Override
    protected String getFormItemTitleXPath() {
        return null;
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

    protected String getPageDetailContentStartRegex() {
        return DETAIL_CONTENT_START_REGEX;
    }

    protected String getPageDetailContentEndRegex() {
        return DETAIL_CONTENT_END_REGEX;
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain(WHU_BBS_DOMAIN).setSleepTime(Constant.SLEEPTIME);
    }

    @Override
    public void setExtractMode() {
        this.extractMode = ExtractMode.EXTRACT_TEXT;
    }

    @Override
    public String getPublisher() {
        return SiteEnum.WHU_BBS.name();
    }

    @Override
    public List<String> getRequests() {
        //文中pages
        String body = getmPage().getHtml().xpath(getFormNodeXPath()).toString();
        List<String> requestLinks = new ArrayList<>();
        Pattern p = Pattern.compile(FORM_ITEM_REGEX);
        if (StringUtils.isEmpty(body))
            return null;
        Matcher matcher = p.matcher(body);
        while (matcher.find()) {
            String temp = matcher.group(1);
            Matcher matcherPostDate = Pattern.compile(DATE_REGX).matcher(temp);
            String postDate = "";
            if (matcherPostDate.find())
                postDate = matcherPostDate.group();
            if (isDroppedItem(postDate)) //比预设的时间早，帖子丢弃
                continue;

            String[] tempArr = temp.substring(1, temp.length()-1).split(",");
            String linkUrlIndex = tempArr[0];
            String linkUrl = FORM_ITEM_LINK_TEMPLATE.replaceAll("PLACEHOLDER_ID", linkUrlIndex);
            requestLinks.add(linkUrl);
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

    protected Boolean isDroppedItem(String itemDate) {
        Date convertDate = DateUtils.getDateFromString(itemDate, DEFAULT_DATE_FORMAT);
        return isDroppedItem(convertDate);
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
            String temp = firstSeperate[1];
            String[] secondSeperate = temp.split(getPageDetailContentEndRegex());
            content = secondSeperate[0];
        }
        return NewsDetailDTO.generateNewsDetail(content, page.getUrl().toString());
    }
}