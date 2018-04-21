package com.school.job;

import com.school.magic.constants.SiteEnum;
import com.school.magic.spiderCreator.SpiderGenerator;
import com.school.utils.DateUtils;
import com.school.utils.PropertyUtil;
import com.school.entity.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.Date;
import java.util.concurrent.*;

import static com.school.utils.DateUtils.DEFAULT_DATE_FORMAT2;

@Component
public class SpiderCronJob implements ISpiderCronBaseJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final static Executor executor = new ThreadPoolExecutor(Integer.valueOf(PropertyUtil.getProperty("CORE_POOL_SIZE")),
            Integer.valueOf(PropertyUtil.getProperty("MAX_POOL_SIZE")), Integer.valueOf(PropertyUtil.getProperty("KEEP_ALIVA_TIME")),
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    /**
     * 首次运行的开关
     */
    private static String FIRST_SWITCH = PropertyUtil.getProperty("FIRST_SPIDER_SWITCH");
    /**
     * 首次运行抓取的初始时间
     */
    private static String FIRST_START_DATE = PropertyUtil.getProperty("FISRT_SPIDER_START_DATE");
    /**
     * 首次运行抓取的结束时间
     */
    private static String FIRST_END_DATE = PropertyUtil.getProperty("FIRST_SPIDER_END_DATE");
    /**
     * spider开关
     */
    private static String SPIDER_SWITCH = PropertyUtil.getProperty("SPIDER_SWITCH");

    /**
     * 首次抓取数据的job
     */
    @Scheduled(cron = "0 20 0/10 * * ?")
//    @Scheduled(cron = "0/15 * * * * ?")
    public void bbsSpiderFirst(){
        if (FIRST_SWITCH.equals(Constant.SWITCH_ON)) {
            logger.info(String.format("初次执行抓取的起止时间: %s ~ %s", FIRST_START_DATE, FIRST_END_DATE));
            //设置开关在指定时间的内抓取数据
            Date startDate = DateUtils.getDateFromString(FIRST_START_DATE, DateUtils.DEFAULT_DATE_FORMAT3);
            Date endDate = DateUtils.getDateFromString(FIRST_END_DATE, DateUtils.DEFAULT_DATE_FORMAT3);
//            Spider spider = SpiderGenerator.createSpider(SiteEnum.WHU_BBS, startDate, endDate);
//            spider.run();
            for (final SiteEnum siteEnum : SiteEnum.values()) {
                if (siteEnum.equals(SiteEnum.ECUST_BBS)) //华理的还有bug，先跳过抓取
                    continue;
                logger.info("获取数据从站点:" + siteEnum.name());
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
//                        logger.info(siteEnum.getNickName());
                        Spider spider = SpiderGenerator.createSpider(siteEnum, startDate, endDate);
                        spider.run();
                    }
                });
                logger.info("完成获取数据从站点:" + siteEnum.name());
            }
        }
    }

    @Scheduled(cron="0 30 * * * ? ")
    public void bbsSpider(){
        if (SPIDER_SWITCH.equals(Constant.SWITCH_ON)) {
            for (SiteEnum siteEnum : SiteEnum.values()) {
                logger.info(DateUtils.getStringFromDate(new Date(), DEFAULT_DATE_FORMAT2) + " 获取数据从站点:" + siteEnum.name());
                Spider spider = SpiderGenerator.createSpider(siteEnum, new Date());
                spider.run();
                logger.info(DateUtils.getStringFromDate(new Date(), DEFAULT_DATE_FORMAT2) + " 完成获取数据从站点:" + siteEnum.name());
            }
        }
    }
}
