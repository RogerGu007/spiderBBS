package com.school.magic.siteHandler;

import com.school.entity.News;
import com.school.magic.constants.Constant;
import com.school.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.school.magic.constants.NJUSiteConstant.*;
import static com.school.utils.DateUtils.DEFAULT_DATE_FORMAT;
import static com.school.utils.DateUtils.ENGLISH_DATE_FORMAT;
import static org.apache.commons.lang3.StringUtils.isNumeric;


public class NJUSiteHandler extends SQSiteHandler{

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Site site = Site.me().setDomain(NJUBBSDOMAIN).setSleepTime(Constant.SLEEPTIME);

    @Override
    public boolean isLoginPage() {
        if (getmPage() == null)
            return false;

        if (getmPage().getUrl().toString().equalsIgnoreCase(LOGIN_URL))
            return true;

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
        return null;
    }

    @Override
    protected String getPageDetailSubjectXPath() {
        return null;
    }

    @Override
    protected String getPageDetailContentXPath() {
        return null;
    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     *
     * @return
     */
    public List<String> getRequests() {
        //文中pages
        Selectable body = getmPage().getHtml().xpath(getFormNodeXPath());
        List<String> requestLinks = new ArrayList<>();

        for (int ii = 0; ii < body.nodes().size(); ii++) {
            Selectable item = body.nodes().get(ii);
            boolean hasChildPage = hasChildPage(item);
            if (hasChildPage) {
                Selectable detailNode = item.xpath(getFormItemDetailXPath());
                if (detailNode != null) {
                    //"Mar 30 18:58" + "2018"
                    String modifiedDate = item.xpath(getFormItemModifyTimeXPath()).toString() +
                            " " + Calendar.getInstance().get(Calendar.YEAR);
                    if (isDroppedItem(modifiedDate)) //比预设的时间早，帖子丢弃
                        continue;

                    String link = detailNode.toString();
                    if (StringUtils.isNotBlank(link))
                        requestLinks.add(genSiteUrl(link));

                    logger.info(String.format("title: {%s}", item.xpath(getFormItemTitleXPath()).toString()));
                }
            }
        }

        //下一页
        List<String> nextPages = getNextPages();
        //只能翻6页，每天更新内容一般不会超过6页
        requestLinks.addAll(getSubList(nextPages, 0, 5));
        return requestLinks;
    }

    private boolean hasChildPage(Selectable item) {
        if (item == null || item.nodes().size() == 0)
            return false;

        String childNum = item.xpath(getFormItemXPath()).toString();
        if (isNumeric(childNum))
            return true;

        return false;
    }

    private String genSiteUrl(String url) {
        return "http://" + NJUBBSDOMAIN + "/" + url;
    }

    private List<String> getNextPages() {
        List<String> linkList = getmPage().getHtml().xpath(getFormNextPagesXPath()).all();
        if (linkList == null || linkList.size() <= 1)
            return new ArrayList<>();

        final String nextPageLink = genSiteUrl(linkList.get(1));
        return new ArrayList<String>() {{ add(nextPageLink); }};
    }

    @Override
    protected Boolean isDroppedItem(String itemDate) {
        Date convertDate = DateUtils.getDateFromString(itemDate, ENGLISH_DATE_FORMAT);
        return isDroppedItem(convertDate);
    }
}
