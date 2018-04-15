package com.school.Service;

import com.school.magic.constants.SiteEnum;
import com.school.magic.spiderCreator.SpiderGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
public class SpiderCronServiceImpl implements ISpiderCronService {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Scheduled(cron="0/5 * *  * * ? ")   //测试：每5秒执行一次
    @Scheduled(cron = "0 45 0/1 * * ?")
    @Override
    public void bbsSpider(){
//        System.out.println("RogerGu进入测试");
        for (SiteEnum siteEnum : SiteEnum.values()) {
            logger.info("获取数据从站点:" + siteEnum.name());
            Spider spider = SpiderGenerator.createSpider(siteEnum);
            spider.run();
        }
    }
}
