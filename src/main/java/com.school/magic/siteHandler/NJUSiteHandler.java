package com.school.magic.siteHandler;

import com.school.entity.NewsDTO;
import com.school.magic.constants.Constant;
import com.school.magic.constants.ExtractSequenceType;
import com.school.utils.DateUtils;
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

import static com.school.magic.constants.NJUSiteConstant.*;
import static com.school.utils.DateUtils.ENGLISH_DATE_FORMAT;
import static org.apache.commons.lang3.StringUtils.isNumeric;


public class NJUSiteHandler extends SQSiteHandler{

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Site site = Site.me().setDomain(NJUBBSDOMAIN).setSleepTime(Constant.SLEEPTIME);

    @Override
    public int getSiteLocationCode() {
        return com.school.spiderEnums.LocationEnum.NANJING.getZipCode();
    }

    @Override
    public String getLinkUrl() {
        return FORMITEMLINKURL;
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
    public void setExtractSequence() {
        //默认的排序
        setExtractNewsSequence(ExtractSequenceType.INNER_INDEX);
        setExtractNewsDetailSequence(ExtractSequenceType.AFTER_NEWS);
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
        return DETAILPOSTDATE;
    }

    @Override
    protected String getPageDetailSubjectXPath() {
        return DETAILSUBJECT;
    }

    @Override
    protected String getPageDetailContentXPath() {
        return DETAILCONTENTSANDCOMMENTS;
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
                Selectable detailNodeLink = item.xpath(getFormItemDetailXPath());
                if (detailNodeLink != null) {
                    //"Mar 30 18:58" + "2018"
                    String modifiedDate = item.xpath(getFormItemModifyTimeXPath()).toString() +
                            " " + Calendar.getInstance().get(Calendar.YEAR);
                    if (isDroppedItem(modifiedDate)) //比预设的时间早，帖子丢弃
                        continue;

                    String link = detailNodeLink.toString();
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

    //过滤掉hot类型的广告
    private boolean hasChildPage(Selectable item) {
        if (item == null || item.nodes().size() == 0)
            return false;

        String childNum = item.xpath(getFormItemXPath()).toString();
        if (isNumeric(childNum))
            return true;

        return false;
    }

    public String genSiteUrl(String url) {
        return "http://" + NJUBBSDOMAIN + "/" + url;
    }

    private List<String> getNextPages() {
        List<String> linkList = getmPage().getHtml().xpath(getFormNextPagesXPath()).all();
        //详情页相同标签的下一页以board=JobExpress结尾，需要过滤掉
        if (linkList == null || linkList.size() < 1 || linkList.get(0).endsWith(INDEX_JOB_URL_TAG))
            return new ArrayList<>();

        final String nextPageLink = genSiteUrl(linkList.get(0));
        return new ArrayList<String>() {{ add(nextPageLink); }};
    }

    @Override
    protected Boolean isDroppedItem(String itemDate) {
        Date convertDate = DateUtils.getDateFromString(itemDate, ENGLISH_DATE_FORMAT);
        return isDroppedItem(convertDate);
    }

    /**
     * 返回item的信息
     * @return
     */
    public NewsDTO extractNews(Page page, Selectable item) {
        //如果没有详情或item需要过滤，无须再往下走了
        if(!hasChildPage(item))
            return null;

        Date postDate = null;
        Selectable date = item.xpath(getPageDetailPostDateXPath()).regex(DateUtils.DATE_REGX2);
        if (date != null && date.toString() != null)
            postDate = DateUtils.getDateFromString(date.toString() +
                    " " + Calendar.getInstance().get(Calendar.YEAR), ENGLISH_DATE_FORMAT);
        else
            return null;

        Selectable subjectItem = item.xpath(getPageDetailSubjectXPath());
        if (subjectItem == null || subjectItem.nodes().size() == 0)
            return null;

        NewsDTO subjectNews = NewsDTO.generateNews(subjectItem.toString(), getmNewsType(), postDate);
        setSubEnumType(subjectNews);
        subjectNews.setLocationCode(getSiteLocationCode());
        subjectNews.setLinkUrl(page != null ? genSiteUrl(page.getHtml().xpath(getLinkUrl()).toString())
                : genSiteUrl(item.xpath(getLinkUrl()).toString()));
        return subjectNews;
    }
}
