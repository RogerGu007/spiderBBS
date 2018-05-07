package com.school.magic;

import com.school.magic.constants.SiteEnum;
import com.school.magic.spiderCreator.SpiderGenerator;
import com.school.spiderEnums.NewsTypeEnum;
import com.school.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.net.ssl.SSLContext;
import java.util.Date;
import java.util.List;

/**
 * test webmagic
 */
public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(1).run();
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Date startDate = DateUtils.getDateFromString("2018-03-01", DateUtils.DEFAULT_DATE_FORMAT3);
        Date endDate = DateUtils.getDateFromString("2018-04-23", DateUtils.DEFAULT_DATE_FORMAT3);
        //todo
//        Spider spider = SpiderGenerator.createSpider(SiteEnum.ECUST_BBS);
        //tj done
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.TJ_BBS, NewsTypeEnum.NEWS_FRIENDS, startDate, endDate);
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.TJ_BBS, NewsTypeEnum.NEWS_JOB, startDate, endDate);
        // tsing done
       // List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.TSING_BBS, NewsTypeEnum.NEWS_FRIENDS, startDate, endDate);
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.TSING_BBS, NewsTypeEnum.NEWS_JOB, new Date());
        //peking   鹊桥需要用户权限；无法注册，需要北大的邮箱或学位证书认证  half done
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.PEKING_BBS, NewsTypeEnum.NEWS_JOB, startDate, endDate);
        //nju  done
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.NJU_BBS, NewsTypeEnum.NEWS_JOB, new Date());
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.NJU_BBS, NewsTypeEnum.NEWS_FRIENDS, startDate, endDate);
        //fudan
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.FUDAN_BBS, NewsTypeEnum.NEWS_FRIENDS, startDate, endDate);
        //todo
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.FUDAN_BBS, NewsTypeEnum.NEWS_JOB, startDate, endDate);
        //sjtu  done
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.SJTU_BBS, NewsTypeEnum.NEWS_JOB, startDate, endDate);
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.SJTU_BBS, NewsTypeEnum.NEWS_FRIENDS, startDate, endDate);
        //ecnu  done
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.ECNU_BBS, NewsTypeEnum.NEWS_JOB, startDate, endDate);
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.ECNU_BBS, NewsTypeEnum.NEWS_FRIENDS, startDate, endDate);
        //zju  www.zju1.com没有找到交友的入口，注册也是需要按月收费的
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.ZJU_BBS, NewsTypeEnum.NEWS_JOB, startDate, endDate);
        //whu  done
        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.WHU_BBS, NewsTypeEnum.NEWS_JOB, startDate, endDate);
//        List<Spider> spiderList = SpiderGenerator.createSpider(SiteEnum.WHU_BBS, NewsTypeEnum.NEWS_FRIENDS, startDate, endDate);
        for (Spider spider : spiderList)
            spider.run();
    }
}