package com.school.resource;

import com.school.magic.constants.SiteEnum;
import com.school.spiderEnums.NewsTypeEnum;
import com.school.magic.spiderCreator.*;
import com.school.entity.ResponseBaseDTO;
import com.school.utils.DateUtils;
import com.school.entity.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("/bbs")
public class BBSNewsResource {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * /spider/service/bbs/news?site=WHU_BBS&date=2018-04-20
     *
     * @param siteEnum
     * @param expectedDate
     * @return
     */
    @GET
    @Path("/news")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNewsByDate(@QueryParam("site") SiteEnum siteEnum,
                                @QueryParam("newsType") NewsTypeEnum newsTypeEnum,
                                @QueryParam("date") String expectedDate) {
        Date date = DateUtils.getDateFromString(expectedDate, DateUtils.DEFAULT_DATE_FORMAT3);
        //按天抓取数据
        List<Spider> spiderList = SpiderGenerator.createSpider(siteEnum, newsTypeEnum, date);
        for (Spider spider : spiderList)
            spider.run();
        return ResponseBaseDTO.render(Constant.RET_CODE_SUCCESS, Constant.RET_MSG_SUCCESS);
    }
}
