package com.school.magic.storePipeline;

import com.school.entity.News;
import com.school.entity.NewsDetail;
import com.school.magic.constants.TJSiteConstant;
import com.school.utils.GsonUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import static com.school.magic.constants.Constant.RESULT_DETAIL_FIELD;
import static com.school.magic.constants.Constant.RESULT_SUBJECT_FIELD;

public class NewsToDBPipeline implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void process(ResultItems resultItems, Task task) {
        logger.info("get page:" + resultItems.getRequest().getUrl());

        String subjectGson = resultItems.get(RESULT_SUBJECT_FIELD);
        String detailGson = resultItems.get(RESULT_DETAIL_FIELD);
        if (TextUtils.isEmpty(subjectGson) || TextUtils.isEmpty(detailGson)) {
            logger.warn(String.format("Subejct or Detail is empty. \n Subject is {%s}; \n" +
                    " Detail is {%s}", subjectGson, detailGson));
            return;
        }

        News subjectNews = GsonUtils.fromGsonString(subjectGson, News.class);
        logger.info("SubjectGson:{%s}", subjectGson);
        NewsDetail detailNews = GsonUtils.fromGsonString(detailGson, NewsDetail.class);
        logger.info("DetailNewsGson: {%s}", detailGson);
    }
}
