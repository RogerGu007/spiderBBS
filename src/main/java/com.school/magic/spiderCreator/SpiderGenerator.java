package com.school.magic.spiderCreator;

import com.school.magic.constants.*;
import com.school.magic.pageProcessor.SQProcessor;
import com.school.magic.siteHandler.*;
import com.school.spiderEnums.NewsTypeEnum;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.Calendar;
import java.util.Date;

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
                sqSiteHandler.setNewsURL(TJSiteConstant.FRIEND_URL);
                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                break;
            case NJU_BBS:
                sqSiteHandler = new NJUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURL(NJUSiteConstant.JOB_URL);
                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                break;
            case PEKING_BBS:
                sqSiteHandler = new PEKINGSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                for (String jobUrl : PEKINGSiteConstant.JOB_URL_LIST) {
                    sqSiteHandler.setNewsURL(jobUrl);
                    spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                }
                break;
            case TSING_BBS:
                sqSiteHandler = new TSINGSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                for (String jobUrl : TSINGSiteConstant.JOB_URL_LIST) {
                    sqSiteHandler.setNewsURL(jobUrl);
                    spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                }
                break;
            case FUDAN_BBS:
                sqSiteHandler = new FUDANSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_FRIENDS);
                sqSiteHandler.setNewsURL(FUDANSiteConstant.FRIEND_URL);
                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                break;
            case SJTU_BBS:
                sqSiteHandler = new SJTUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURL(SJTUSiteConstant.JOB_URL);
                //设置代理抓包调试
//                us.codecraft.webmagic.proxy.Proxy proxy = new us.codecraft.webmagic.proxy.Proxy("localhost", 8889);
//                HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//                httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxy));
//                spider = SQProcessor.getSpider(sqSiteHandler).setDownloader(httpClientDownloader).thread(1);
                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
//                SQProcessor.getSpider(sqSiteHandler).test(SJTUSiteConstant.JOB_URL);
                break;
            case ECNU_BBS:
                sqSiteHandler = new ECNUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                for (String jobUrl : ECNUSiteConstant.JOB_URL_LIST) {
                    sqSiteHandler.setNewsURL(jobUrl);
                    spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                }
                break;
            case ZJU_BBS:
                sqSiteHandler = new ZJUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURL(ZJUSiteConstant.JOB_URL);
                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                break;
            case WHU_BBS:
                sqSiteHandler = new WHUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURL(WHUSiteConstant.JOB_URL);
                //test expectedDate
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(new Date());
//                calendar.add(Calendar.DAY_OF_MONTH, -3);
//                sqSiteHandler.setExpectedDate(calendar.getTime());
                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                break;
            case ECUST_BBS:
                sqSiteHandler = new ECUSTSiteHandler();
                sqSiteHandler.setLoginURL(ECUSTSiteConstant.LOGIN_URL);
                sqSiteHandler.setUserNamePair("username", "rogergu");
                sqSiteHandler.setPasswordPair("password", "20180330");
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_FRIENDS);
                sqSiteHandler.setNewsURL(ECUSTSiteConstant.JOB_URL);
                us.codecraft.webmagic.proxy.Proxy proxy = new us.codecraft.webmagic.proxy.Proxy("localhost", 8889);
                HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
                httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxy));
                spider = SQProcessor.getSpider(sqSiteHandler).setDownloader(httpClientDownloader).thread(1);
//                spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
                break;
            default:
                break;
        }

        return spider;
    }
}
