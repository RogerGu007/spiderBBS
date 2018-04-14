package com.school.magic.storePipeline;

import com.school.Service.NewsService;
import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.entity.PublisherDTO;
import com.school.utils.GsonUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

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
        NewsDTO subjectNews = GsonUtils.fromGsonString(subjectGson, NewsDTO.class);
        PublisherDTO publisherDTO = newsService.getPublisherId(resultItems.get(RESULT_PUBLISHER_FIELD));
        if (publisherDTO != null) {
            subjectNews.setPublisherId(publisherDTO.getId());
        }

        NewsDetailDTO detailNews = GsonUtils.fromGsonString(detailGson, NewsDetailDTO.class);
        try {
            newsService.storeDataToDB(subjectNews, detailNews);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
