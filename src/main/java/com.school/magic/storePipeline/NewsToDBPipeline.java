package com.school.magic.storePipeline;

import com.school.Constants.RetCode;
import com.school.Gson.PostMsgGson;
import com.school.Gson.RetIDResultGson;
import com.school.Gson.RetResultGson;
import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.utils.GsonUtils;
import com.school.utils.PropertyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import com.school.remote.NewsRomoteCaller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.school.magic.constants.Constant.*;

@Component
public class NewsToDBPipeline implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NewsRomoteCaller newsRemoteCaller;

    private static List<String> filterPrefixKeywordList =
            StringUtils.isNotEmpty(PropertyUtil.getProperty("FILTER_PREFIX_KEYWORD")) ?
                    Arrays.asList(PropertyUtil.getProperty("FILTER_PREFIX_KEYWORD").split(",")) : new ArrayList<>();
    private static List<String> filterSuffixKeywordList =
            StringUtils.isNotEmpty(PropertyUtil.getProperty("FILTER_SUFFIX_KEYWORD")) ?
                    Arrays.asList(PropertyUtil.getProperty("FILTER_SUFFIX_KEYWORD").split(",")) : new ArrayList<>();
    private static List<String> filterContainKeywordList =
            StringUtils.isNotEmpty(PropertyUtil.getProperty("FILTER_CONTAIN_KEYWORD")) ?
                    Arrays.asList(PropertyUtil.getProperty("FILTER_CONTAIN_KEYWORD").split(",")) : new ArrayList<>();

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
        if (isDropped(subjectNews))
            return;
        try {
            RetIDResultGson resultIdGson = newsRemoteCaller.getUserID(resultItems.get(RESULT_PUBLISHER_FIELD));
            if (resultIdGson.getID() == null)
            {
                //不存在user为空，但是需要发布内容的
                logger.error("user为空,nickname=" + resultItems.get(RESULT_PUBLISHER_FIELD));
                return;
            }
            subjectNews.setPublisherId(resultIdGson.getID().toString());
            NewsDetailDTO detailNews = GsonUtils.fromGsonString(detailGson, NewsDetailDTO.class);

            PostMsgGson postMsgGson = new PostMsgGson();
            postMsgGson.setContent(subjectNews.getmSubject());
            postMsgGson.setNewsType(subjectNews.getNewsType());
            postMsgGson.setNewsSubType(subjectNews.getNewsSubType());
            postMsgGson.setLocationCode(subjectNews.getLocationCode());
            postMsgGson.setPostDate(subjectNews.getPostDate());
			postMsgGson.setDetailContent(detailNews.getDetailContent());
			postMsgGson.setSourceArticleUrl(detailNews.getSourceArticleUrl());
			postMsgGson.setSource("spider");

            RetResultGson resultGson = newsRemoteCaller.postNews(subjectNews.getPublisherId(), postMsgGson);
            if (resultGson.getRetCode() == RetCode.RET_CODE_OK)
                logger.info("postNews Successful: " + detailNews.getSourceArticleUrl());
            else
                logger.error("postNews failed: " + detailNews.getSourceArticleUrl());

        } catch (Exception e) {
            logger.error(String.format("news落地失败，url=%s, %s", subjectNews.getLinkUrl(), e.getMessage()));
        }
    }

    /**
     * 落表前再做一次过滤
     *
     * @param newsDTO
     * @return
     */
    private boolean isDropped(NewsDTO newsDTO) {
        String subject = newsDTO.getmSubject().trim();
        if (filterContainKeywordList.contains(subject))
            return true;

        for (String prefix : filterPrefixKeywordList) {
            if (subject.startsWith(prefix))
                return true;
        }

        for (String suffix : filterSuffixKeywordList) {
            if (subject.endsWith(suffix))
                return true;
        }

        return false;
    }
}
