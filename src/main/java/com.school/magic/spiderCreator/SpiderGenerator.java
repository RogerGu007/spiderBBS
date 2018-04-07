package com.school.magic.spiderCreator;

import com.school.magic.constants.SiteEnum;
import com.school.magic.pageProcessor.SQProcessor;
import com.school.magic.siteHandler.NJUSiteHandler;
import com.school.magic.siteHandler.PEKINGSiteHandler;
import com.school.magic.siteHandler.SQSiteHandler;
import com.school.magic.siteHandler.TJSiteHandler;
import com.school.spiderEnums.NewsTypeEnum;
import us.codecraft.webmagic.Spider;

import static com.school.magic.constants.NJUSiteConstant.JOB_URL;
import static com.school.magic.constants.PEKINGSiteConstant.JOB_URL_LIST;
import static com.school.magic.constants.TJSiteConstant.FRIEND_URL;
import static com.school.magic.constants.TJSiteConstant.LOGIN_URL;

public class SpiderGenerator {

    public static Spider createSpider(SiteEnum siteEnum) {
        Spider spider = null;
        SQSiteHandler sqSiteHandler = null;
        switch (siteEnum) {
            case TJ_BBS:
                sqSiteHandler = new TJSiteHandler();
                sqSiteHandler.setLoginURL(LOGIN_URL);
                sqSiteHandler.setUserNamePair("username", "tjshif");
                sqSiteHandler.setPasswordPair("password", "19801004");
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_FRIENDS);
                sqSiteHandler.setNewsURL(FRIEND_URL);
                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                break;
            case NJU_BBS:
                sqSiteHandler = new NJUSiteHandler();
//                sqSiteHandler.setLoginURL(LOGIN_URL);
//                sqSiteHandler.setUserNamePair("username", "tjshif");
//                sqSiteHandler.setPasswordPair("password", "19801004");
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURL(JOB_URL);
                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                break;
            case PEKING_BBS:
                sqSiteHandler = new PEKINGSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                for (String jobUrl : JOB_URL_LIST) {
                    sqSiteHandler.setNewsURL(jobUrl);
                    spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                }
                break;
            default:
                break;
        }

        return spider;
    }
}
