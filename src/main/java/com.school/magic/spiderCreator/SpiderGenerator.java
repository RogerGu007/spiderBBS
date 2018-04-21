package com.school.magic.spiderCreator;

import com.school.magic.constants.*;
import com.school.magic.pageProcessor.SQProcessor;
import com.school.magic.siteHandler.*;
import com.school.spiderEnums.NewsTypeEnum;
import org.apache.commons.collections.map.HashedMap;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.*;

import static com.school.magic.constants.NJUSiteConstant.JOB_URL;
import static com.school.magic.constants.PEKINGSiteConstant.JOB_URL_LIST;
import static com.school.magic.constants.TJSiteConstant.FRIEND_URL;
import static com.school.magic.constants.TJSiteConstant.LOGIN_URL;

public class SpiderGenerator {

    public static List<Spider> createSpider(SiteEnum siteEnum, NewsTypeEnum newsTypeEnum) {
        return createSpider(siteEnum, newsTypeEnum,null, null, null);
    }

    public static List<Spider> createSpider(SiteEnum siteEnum, NewsTypeEnum newsTypeEnum, Date date) {
        return createSpider(siteEnum, newsTypeEnum, date, null, null);
    }

    public static List<Spider> createSpider(SiteEnum siteEnum, NewsTypeEnum newsTypeEnum,
                                            Date beginDate, Date endDate) {
        return createSpider(siteEnum, newsTypeEnum, null, beginDate, endDate);
    }

    public static List<Spider> createSpider(SiteEnum siteEnum, NewsTypeEnum newsTypeEnum,
                                      Date date, Date beginDate, Date endDate) {
        Spider spider = null;
        SQSiteHandler sqSiteHandler = null;
        List<Spider> spiderList = new ArrayList<>();
        switch (siteEnum) {
            case TJ_BBS:
                sqSiteHandler = new TJSiteHandler();
                sqSiteHandler.setLoginURL(LOGIN_URL);
                sqSiteHandler.setUserNamePair("username", "tjshif");
                sqSiteHandler.setPasswordPair("password", "19801004");
                if (newsTypeEnum.equals(NewsTypeEnum.NEWS_FRIENDS))
                    sqSiteHandler.setNewsURLList(Arrays.asList(TJSiteConstant.FRIEND_URL));
                else if (newsTypeEnum.equals(NewsTypeEnum.NEWS_JOB))
                    sqSiteHandler.setNewsURLList(Arrays.asList(TJSiteConstant.JOB_URL));

                break;
            case NJU_BBS:
                sqSiteHandler = new NJUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURLList(Arrays.asList(NJUSiteConstant.JOB_URL));

                break;
            case PEKING_BBS:
                sqSiteHandler = new PEKINGSiteHandler();
                if (newsTypeEnum.equals(NewsTypeEnum.NEWS_JOB))
                    sqSiteHandler.setNewsURLList(PEKINGSiteConstant.JOB_URL_LIST);

                break;
            case TSING_BBS:
                sqSiteHandler = new TSINGSiteHandler();
                if (newsTypeEnum.equals(NewsTypeEnum.NEWS_FRIENDS))
                    sqSiteHandler.setNewsURLList(Arrays.asList(TSINGSiteConstant.FRIEND_URL));
                else if (newsTypeEnum.equals(NewsTypeEnum.NEWS_JOB))
                    sqSiteHandler.setNewsURLList(TSINGSiteConstant.JOB_URL_LIST);

                break;
            case FUDAN_BBS:
                sqSiteHandler = new FUDANSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_FRIENDS);
                sqSiteHandler.setNewsURLList(Arrays.asList(FUDANSiteConstant.FRIEND_URL));
                break;
            case SJTU_BBS:
                sqSiteHandler = new SJTUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURLList(Arrays.asList(SJTUSiteConstant.JOB_URL));
                //设置代理抓包调试
//                us.codecraft.webmagic.proxy.Proxy proxy = new us.codecraft.webmagic.proxy.Proxy("localhost", 8889);
//                HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//                httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxy));
//                spider = SQProcessor.getSpider(sqSiteHandler).setDownloader(httpClientDownloader).thread(1);
//                SQProcessor.getSpider(sqSiteHandler).test(SJTUSiteConstant.JOB_URL);
                break;
            case ECNU_BBS:
                sqSiteHandler = new ECNUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURLList(ECNUSiteConstant.JOB_URL_LIST);
                break;
            case ZJU_BBS:
                sqSiteHandler = new ZJUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURLList(Arrays.asList(ZJUSiteConstant.JOB_URL));
                break;
            case WHU_BBS:
                sqSiteHandler = new WHUSiteHandler();
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURLList(Arrays.asList(WHUSiteConstant.JOB_URL));
                //test expectedDate
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(new Date());
//                calendar.add(Calendar.DAY_OF_MONTH, -3);
//                sqSiteHandler.setExpectedDate(calendar.getTime());
                break;
            case ECUST_BBS:
                sqSiteHandler = new ECUSTSiteHandler();
                sqSiteHandler.setLoginURL(ECUSTSiteConstant.LOGIN_URL);
                sqSiteHandler.setUserNamePair("username", "rogergu");
                sqSiteHandler.setPasswordPair("password", "20180330");
                sqSiteHandler.setNewsType(NewsTypeEnum.NEWS_JOB);
                sqSiteHandler.setNewsURL(ECUSTSiteConstant.JOB_URL);
                us.codecraft.webmagic.proxy.Proxy proxy = new us.codecraft.webmagic.proxy.Proxy("localhost", 8889);
                HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
                httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxy));
                spider = SQProcessor.getSpider(sqSiteHandler).setDownloader(httpClientDownloader).thread(1);
                break;
            default:
                break;
        }

        sqSiteHandler.setNewsType(newsTypeEnum);
        setSiteHandlerDate(sqSiteHandler, date, beginDate, endDate);
        //特殊处理，如job信息又分为了校招、社招两个入口
        for (String newsUrls : sqSiteHandler.getNewsURLList()) {
            sqSiteHandler.setNewsURL(newsUrls);
            spider = SQProcessor.getSpider(sqSiteHandler).thread(1);
            spiderList.add(spider);
        }

        return spiderList;
    }

    private static void setSiteHandlerDate(SQSiteHandler sqSiteHandler, Date date, Date beginDate, Date endDate) {
        if (date != null) {
            sqSiteHandler.setExpectedDate(date);
        }

        if (beginDate != null) {
            sqSiteHandler.setBeginDate(beginDate);
        }

        if (endDate != null) {
            sqSiteHandler.setEndDate(endDate);
        }
    }
}
