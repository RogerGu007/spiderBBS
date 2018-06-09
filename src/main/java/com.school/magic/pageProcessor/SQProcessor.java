package com.school.magic.pageProcessor;

import com.school.Constants.RetCode;
import com.school.Gson.NewsDetailResultGson;
import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.magic.constants.ExtractMode;
import com.school.magic.siteHandler.SQSiteHandler;
import com.school.magic.storePipeline.NewsToDBPipeline;
import com.school.remote.NewsRomoteCaller;
import com.school.utils.ApplicationContextUtils;
import com.school.utils.GsonUtils;
import com.school.utils.PropertyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.school.magic.constants.Constant.*;

public class SQProcessor implements PageProcessor {
    private SQSiteHandler mSqSiteHandler;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private NewsRomoteCaller newsRemoteCaller = ApplicationContextUtils.getBean(NewsRomoteCaller.class);

    private static Long REPEAT_SPIDER_TIME = StringUtils.isEmpty(PropertyUtil.getProperty("REPEAT_SPIDER_TIME"))
            ? 21600 : Long.valueOf(PropertyUtil.getProperty("REPEAT_SPIDER_TIME"));

    private SQProcessor(SQSiteHandler sqSiteHandler) {
        this.mSqSiteHandler = sqSiteHandler;
    }

    @Override
    public void process(Page page) {
        if (mSqSiteHandler == null)
            return;

        mSqSiteHandler.setmPage(page);
        //Step1：登录页过滤
        if (mSqSiteHandler.isLoginPage()) {
            mSqSiteHandler.extractCookie(page);
            page.setSkip(true);
            return;
        }

        final String siteUrl = page.getUrl().toString();
        //重复的页面不再抓取
        if (isSkipUrl(siteUrl, page)) {
            page.setSkip(true);
            return;
        }

        //Step2: 获取一页的所有请求数据
        List<String> requestURLs = mSqSiteHandler.getRequests();
        if (requestURLs != null && requestURLs.size() > 0) {
            List<String> filterURLs = new ArrayList<>();

            for (String url : requestURLs)
            {
                if (isSkipUrl(url, page))
                    continue;
                filterURLs.add(url);
            }
            //form 页面，只需要找到具体的item就可以了
            page.addTargetRequests(filterURLs);
            page.setSkip(true);
            return;
        }

        //提取news
        if (!extractSubjectFile(page))
            return;
        //提取news详情
        extractDetailField(page);
    }

    private Boolean isSkipUrl(String siteUrl, Page page)
    {
        if (TextUtils.isEmpty(siteUrl))
            return true;
        NewsDetailResultGson resultGson = newsRemoteCaller.getNewsDetailByUrl(siteUrl);
        if (resultGson.getRetCode() == RetCode.RET_CODE_SYSTEMERROR)
        {
            logger.error("failed to get news info: " + siteUrl);
            return true;
        }

        if (resultGson.getNewsDetailDTO() != null) {
            NewsDetailDTO detailDTO = resultGson.getNewsDetailDTO();
            //6小时以内的帖子不过滤
            if (detailDTO.getPostDate() != null
                    && (detailDTO.getPostDate().getTime() + REPEAT_SPIDER_TIME * 1000 >= new Date().getTime())) {
                page.putField(RESULT_NEWSID_FIELD, detailDTO.getNewsID());
                page.putField(EXISTED_DETAIL_FIELD, detailDTO.getDetailContent()); //detailContent透传下去，不变就不做更新了
                return false;
            }

            return true;
        }
        return false;
    }

    public static Spider getSpider(SQSiteHandler sqSiteHandler) {
        if (sqSiteHandler == null)
            return null;

        SQProcessor sqProcessor = new SQProcessor(sqSiteHandler);
        Spider spider = Spider.create(sqProcessor);

        Request loginRequest = sqSiteHandler.getLoginRequest();
        if (loginRequest != null)
        {
            if (sqSiteHandler.getNewsURL() != null)
                spider.addRequest(loginRequest).addUrl(sqSiteHandler.getNewsURL());
            else
                spider.addRequest(loginRequest);
        }
        else
            spider.addUrl(sqSiteHandler.getNewsURL());

        NewsToDBPipeline newsToDBPipeline = ApplicationContextUtils.getBean(NewsToDBPipeline.class);
        spider.addPipeline(newsToDBPipeline);

        return spider;
    }

    @Override
    public Site getSite() {
        if (mSqSiteHandler == null)
            return null;
        return mSqSiteHandler.getSite();
    }

    /**
     * 抽取标题的数据
     *
     * @param page
     */
    private boolean extractSubjectFile(Page page) {
        NewsDTO news = null;
        if (mSqSiteHandler.getExtractMode().equals(ExtractMode.EXTRACT_HTML_ITEM))
            news = mSqSiteHandler.extractNews(page);
        else if (mSqSiteHandler.getExtractMode().equals(ExtractMode.EXTRACT_TEXT))
            news = mSqSiteHandler.extractNewsFromText(page);

        if (news == null) {
            page.setSkip(true);
            return false;
        } else {
            page.putField(RESULT_PUBLISHER_FIELD, mSqSiteHandler.getPublisher());
            page.putField(RESULT_SUBJECT_FIELD, GsonUtils.toGsonString(news));
        }
        return true;
    }

    /**
     * 抽取详情数据
     * @param page
     */
    private boolean extractDetailField(Page page) {
        NewsDetailDTO details = null;
        if (mSqSiteHandler.getExtractMode().equals(ExtractMode.EXTRACT_HTML_ITEM))
            details = mSqSiteHandler.extractNewsDetails(page);
        else if (mSqSiteHandler.getExtractMode().equals(ExtractMode.EXTRACT_TEXT))
            details = mSqSiteHandler.extractNewsDetailsFromText(page);

        if (details == null || TextUtils.isEmpty(details.getSourceArticleUrl())) {
            page.setSkip(true);
            return false;
        } else {
            page.putField(RESULT_DETAIL_FIELD, GsonUtils.toGsonString(details));
        }
        return true;
    }
}
