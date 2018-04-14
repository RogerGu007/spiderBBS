package com.school.magic;

import com.school.magic.constants.SiteEnum;
import com.school.magic.spiderCreator.SpiderGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.net.ssl.SSLContext;

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
//        Spider spider = SpiderGenerator.createSpider(SiteEnum.TJ_BBS);
//        Spider spider = SpiderGenerator.createSpider(SiteEnum.NJU_BBS);
//        Spider spider = SpiderGenerator.createSpider(SiteEnum.PEKING_BBS);
        //todo
//        Spider spider = SpiderGenerator.createSpider(SiteEnum.FUDAN_BBS);
//        Spider spider = SpiderGenerator.createSpider(SiteEnum.TSING_BBS);
//        Spider spider = SpiderGenerator.createSpider(SiteEnum.SJTU_BBS);
//        Spider spider = SpiderGenerator.createSpider(SiteEnum.ECNU_BBS);
//        Spider spider = SpiderGenerator.createSpider(SiteEnum.ZJU_BBS);
        Spider spider = SpiderGenerator.createSpider(SiteEnum.WHU_BBS);
        spider.run();
    }
}