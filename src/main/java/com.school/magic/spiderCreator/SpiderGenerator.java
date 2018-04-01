package com.school.magic.spiderCreator;

import com.school.magic.constants.SiteEnum;
import com.school.magic.pageProcessor.SQProcessor;
import com.school.magic.siteHandler.SQSiteHandler;
import com.school.magic.siteHandler.TJSiteHandler;
import com.school.spiderConstants.NewsEnum;
import us.codecraft.webmagic.Spider;

import static com.school.magic.constants.TJSiteConstant.FRIEND_URL;
import static com.school.magic.constants.TJSiteConstant.LOGIN_URL;

public class SpiderGenerator {

    public static Spider createSpider(SiteEnum siteEnum) {
        Spider spider = null;
        switch (siteEnum) {
            case TJ_BBS:
                SQSiteHandler sqSiteHandler = new TJSiteHandler();
                sqSiteHandler.setLoginURL(LOGIN_URL);
                sqSiteHandler.setUserNamePair("username", "tjshif");
                sqSiteHandler.setPasswordPair("password", "19801004");
                sqSiteHandler.setNewsType(NewsEnum.NEWS_FRIENDS);
                sqSiteHandler.setNewsURL(FRIEND_URL);
                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                break;
            default:
                break;
        }

        return spider;
    }
}
