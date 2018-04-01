package com.school.magic.pageProcessor;

import com.school.entity.News;
import com.school.entity.NewsDetail;
import com.school.magic.siteHandler.SQSiteHandler;
import com.school.magic.storePipeline.ConsolePipeline;
import com.school.magic.storePipeline.NewsToDBPipeline;
import com.school.utils.GsonUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

import static com.school.magic.constants.Constant.RESULT_DETAIL_FIELD;
import static com.school.magic.constants.Constant.RESULT_SUBJECT_FIELD;

public class SQProcessor implements PageProcessor {
    private SQSiteHandler mSqSiteHandler;

    private SQProcessor(SQSiteHandler sqSiteHandler) {
        this.mSqSiteHandler = sqSiteHandler;
    }

    @Override
    public void process(Page page) {
        if (mSqSiteHandler == null)
            return;

        mSqSiteHandler.setmPage(page);
        //登录页过滤
        if (mSqSiteHandler.isLoginPage()) {
            page.setSkip(true);
            return;
        }

        List<String> requestURLs = mSqSiteHandler.getRequests();
        if (requestURLs != null && requestURLs.size() > 0) {
            //form 页面，只需要找到具体的item就可以了
            page.addTargetRequests(requestURLs);
            page.setSkip(true);
            return;
        }

        News news = mSqSiteHandler.extractNews();
        if (news == null) {
            page.setSkip(true);
            return;
        } else {
            page.putField(RESULT_SUBJECT_FIELD, GsonUtils.toGsonString(news));
        }

        NewsDetail details = mSqSiteHandler.extractNewsDetails();
        if (details == null) {
            page.setSkip(true);
            return;
        } else {
            page.putField(RESULT_DETAIL_FIELD, GsonUtils.toGsonString(details));
        }
    }

    @Override
    public Site getSite() {
        if (mSqSiteHandler == null)
            return null;
        return mSqSiteHandler.getSite();
    }

    public static Spider getSpider(SQSiteHandler sqSiteHandler) {
        if (sqSiteHandler == null)
            return null;

        SQProcessor sqProcessor = new SQProcessor(sqSiteHandler);
        Spider spider = Spider.create(sqProcessor);

        Request loginRequest = sqSiteHandler.getLoginRequest();
        if (loginRequest != null)
            spider.addRequest(loginRequest).addUrl(sqSiteHandler.getNewsURL());
        else
            spider.addUrl(sqSiteHandler.getNewsURL());

        spider.addPipeline(new NewsToDBPipeline());

        return spider;
    }
}
