package com.school.magic.siteHandler;

import com.school.Gson.FudanCookieGson;
import com.school.magic.constants.Constant;
import com.school.magic.constants.SiteEnum;
import com.school.spiderEnums.LocationEnum;
import com.school.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
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

//    protected Selectable getChildPage(Selectable item) {
//        if (item == null || item.nodes().size() == 0)
//            return null;
//
//        return item.xpath(FORMITEMCHILD);
//    }

    protected List<String> getNextPages() {
        //翻页信息是脚本里面的，抓取不到
        List<String> pageList = new ArrayList<>();
        List<String> tempPageList = getmPage().getHtml().xpath(getFormNextPagesXPath()).links().all();
        String prePage = tempPageList.get(0);
        String[] indexArr = prePage.split("start");
        if (indexArr != null && indexArr.length == 2) {
            int preIndex = Integer.valueOf(indexArr[1]);
            for (int i = 0; i < 9; i++) {
                pageList.add(prePage.replace(indexArr[1], String.valueOf(preIndex - i * 20)));
            }
        }
        return pageList;
    }
}
