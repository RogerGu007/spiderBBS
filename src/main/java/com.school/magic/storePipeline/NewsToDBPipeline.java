package com.school.magic.storePipeline;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.Service.NewsService;
import com.school.dao.INewsDAO;
import com.school.entity.News;
import com.school.entity.NewsDetail;
import com.school.magic.constants.TJSiteConstant;
import com.school.utils.GsonUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

import static com.school.magic.constants.Constant.*;

@Component
public class NewsToDBPipeline implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NewsService newsService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        logger.info("get page:" + resultItems.getRequest().getUrl());

        String subjectGson = resultItems.get(RESULT_SUBJECT_FIELD);
        //目前不存在一个页面有多个详情的情况
        String detailGson = resultItems.get(RESULT_DETAIL_FIELD);

        if (TextUtils.isEmpty(subjectGson) && TextUtils.isEmpty(detailGson)) {
            logger.warn(String.format("Subejct and Detail is empty. \n Subject is {%s}; \n" +
                    " Detail is {%s}", subjectGson, detailGson));
            return;
        }

        logger.info(String.format("SubjectGson:{%s}", subjectGson));
        logger.info(String.format("DetailNewsGson: {%s}", detailGson));
        if (resultItems.get(IS_SUBJECT_LIST) == null) {
            //都从详情页获取数据
            News subjectNews = GsonUtils.fromGsonString(subjectGson, News.class);
            NewsDetail detailNews = GsonUtils.fromGsonString(detailGson, NewsDetail.class);
            try {
                newsService.storeDataToDB(subjectNews, detailNews);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            //news从列表页获取
            List<News> subjectNewsList =
                    new Gson().fromJson(subjectGson, new TypeToken<List<News>>() {
                    }.getType());
            try {
                newsService.storeDataToDB(subjectNewsList);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
