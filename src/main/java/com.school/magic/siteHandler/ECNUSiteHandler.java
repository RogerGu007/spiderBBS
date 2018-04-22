package com.school.magic.siteHandler;

import com.school.magic.constants.Constant;
import com.school.magic.constants.SiteEnum;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;
import com.school.utils.MD5Utils;

import java.util.ArrayList;
import java.util.List;

import static com.school.magic.constants.ECNUSiteConstant.*;

public class ECNUSiteHandler extends SQSiteHandler{

    private List<String> mChildNodes = new ArrayList<>();

    @Override
    public int getSiteLocationCode() {
        return com.school.spiderEnums.LocationEnum.SHANGHAI.getZipCode();
    }

    @Override
    public String getLinkUrl() {
        return FORMITEMLINKURL;
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
        return FORMITEMLINK;
    }

    @Override
    protected String getFormItemDetailXPath() {
        return FORMITEMLINK;
    }

    @Override
    protected String getFormItemModifyTimeXPath() {
        return FORMITEMMODIFYTIME;
    }

    protected String getFormItemBakModifyTimeXPath() {
        return FORMITEMMODIFYTIME_BAK;
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
    public Site getSite() {
        String loginTime = String.valueOf(System.currentTimeMillis());
        Site site = Site.me().setDomain(ECNU_BBS_DOMAIN)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
                .addCookie("PHPSESSID", MD5Utils.MD5(loginTime))
                .addCookie("nj0v_5a4b_sid", "eqB86j")
                .addCookie("nj0v_5a4b_saltkey", "tbcJ228j")
//                .addCookie("nj0v_5a4b_lastvisit", "1524402005")
                .addCookie("nj0v_5a4b_lastvisit", loginTime)
//                .addCookie("nj0v_5a4b_sendmail", "1")
                .addCookie("nj0v_5a4b_st_t", "0%7C1524405686%7Cb4bce9396d7ace7b0cfd706e15cf89b6")
                .addCookie("_fmdata", "bqTkP6lXoAwotXYY6tQqyWof1vzt0yStyh%2B53QruCYdhHX%2FnbfiMkYyrx8MiLcnKi%2FMnCVABbxvaKq%2F7fENUc3S5Zn1SkNFbZAaHjpRBrTs%3D")
                .addCookie("nj0v_5a4b_seccode", "906.2ae5522bbdf4cad0c9")
//                .addCookie("nj0v_5a4b_lastact", "1524405713%09plugin.php%09")
                .addCookie("nj0v_5a4b_ulastactivity", "ebcdhMndoPT%2FZDm8eSHBaqso0eE033AucxpGJyHLEIBijyYsMhKx")
                .addCookie("nj0v_5a4b_auth", "d4c4yz7DPoZzXM9dgQ75n5bW%2BzYifvnqoUKqsxhl34fsCxdO2OAtaf7iaVzA9%2Bv8VJtVBFlIcwNFlsFsE9wPj%2FjlzVM")
                .setSleepTime(Constant.SLEEPTIME);
        return site;
    }

    @Override
    public String getPublisher() {
        return SiteEnum.ECNU_BBS.getNickName();
    }

    public String getPostDate(Selectable item) {
        Selectable selectable = item.xpath(getFormItemModifyTimeXPath());
        if (StringUtils.isEmpty(selectable.toString()))
            selectable = item.xpath(getFormItemBakModifyTimeXPath());
        return selectable.toString() + " 00:00:00";
    }
}
